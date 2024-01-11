package com.example.codingsonyeondan.domain.songs.service

import com.example.codingsonyeondan.domain.album.repository.AlbumRepository
import com.example.codingsonyeondan.domain.songs.Song
import com.example.codingsonyeondan.domain.songs.dto.SongCreateDTO
import com.example.codingsonyeondan.domain.songs.dto.SongDTO
import com.example.codingsonyeondan.domain.songs.dto.SongUpdateDTO

import com.example.codingsonyeondan.domain.songs.repository.SongRepository
import com.example.codingsonyeondan.infra.exception.ModelNotFoundException
import com.example.codingsonyeondan.infra.exception.UniqueAttributeAlreadyExistException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class SongServiceImpl(
    private val songRepository: SongRepository,
    private val albumRepository: AlbumRepository
): SongService {

    private fun getValidateSong(songId: Long): Song {
        return songRepository.findByIdOrNull(songId) ?: throw ModelNotFoundException("Song", songId.toString())
    }
    private fun checkTitleIsAlreadyExist(title: String) {
        if(songRepository.existsByTitle(title))
            throw UniqueAttributeAlreadyExistException("song", "title", title)
    }
    override fun getSong(albumId: Long, songId: Long): SongDTO? {
        return songRepository.findByAlbumIdAndId(albumId, songId)?.let { SongDTO.from(it) }
    }
    override fun getSongs(albumId: Long): List<SongDTO> {
        return songRepository.findByAlbumId(albumId).map { SongDTO.from(it) }
    }
    @Transactional
    override fun createSong(albumId: Long, songCreateDTO: SongCreateDTO): SongDTO {
        val album = albumRepository.findById(albumId)
            .orElseThrow { throw IllegalArgumentException("존재하지 않는 앨범입니다.") }

        checkTitleIsAlreadyExist(songCreateDTO.title)
        return SongDTO.from(songRepository.save(
            Song(
                title = songCreateDTO.title,
                composer = songCreateDTO.composer,
                lyrics = songCreateDTO.lyrics,
                link = songCreateDTO.link,
                album = album
            )
        ))
    }
    override fun updateSong(albumId: Long, songId: Long, songModifyDTO: SongUpdateDTO): SongDTO {
        val song = songRepository.findById(songId).orElseThrow { NoSuchElementException("곡을 찾을 수 없습니다.") }
        song.title = songModifyDTO.title
        song.composer = songModifyDTO.composer
        song.lyrics = songModifyDTO.lyrics
        song.link = songModifyDTO.link
        songRepository.save(song)
        return Song.toDto(song)
    }
    @Transactional
    override fun deleteSong(albumId: Long, songId: Long) {
        println(getValidateSong(songId).title)
        return songRepository.delete(getValidateSong(songId))
    }


}
