package com.example.codingsonyeondan.domain.songs.service

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
    private val songRepository: SongRepository): SongService {

    private fun getValidateSong(songId: Long): Song {
        return songRepository.findByIdOrNull(songId) ?: throw ModelNotFoundException("Song", songId)
    }
    private fun checkTitleIsAlreadyExist(title: String) {
        if(songRepository.existsByTitle(title))
            throw UniqueAttributeAlreadyExistException("song", "title", title)
    }
    override fun getSong(albumId: Long, songId: Long): SongDTO? {
        return SongDTO.from(getValidateSong(songId))
    }

    override fun getSongs(albumId: Long): List<SongDTO> {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun createSong(albumId: Long, songCreateDTO: SongCreateDTO): SongDTO {
        checkTitleIsAlreadyExist(songCreateDTO.title)
        return SongDTO.from(songRepository.save(
            Song(title = songCreateDTO.title,
                composer = songCreateDTO.composer,
                lyrics = songCreateDTO.lyrics,
                link = songCreateDTO.link)
        ))
    }
    override fun updateSong(albumId: Long, songId: Long, songModifyDTO: SongUpdateDTO): SongDTO {
        val song = songRepository.findById(songId).orElseThrow { NoSuchElementException("곡을 찾을 수 없습니다.") }
        song.title = songModifyDTO.title
        song.composer = songModifyDTO.composer
        song.lyrics = songModifyDTO.lyrics
        song.link = songModifyDTO.link
        songRepository.save(song)
        return TODO("반환 값을 제공하세요")
    }
    @Transactional
    override fun deleteSong(albumId: Long, songId: Long) {
        println(getValidateSong(songId).title)
        return songRepository.delete(getValidateSong(songId))
    }
}