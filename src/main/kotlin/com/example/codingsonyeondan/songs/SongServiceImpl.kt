package com.example.codingsonyeondan.songs

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class SongServiceImpl(
    private val songRepository: SongRepository,
    private val albumRepository: AlbumRepository
) : SongService {

    override fun createSong(albumId: Long, song: Song): Song {
        val album = albumRepository.findById(albumId).orElseThrow { Exception("앨범이 존재하지 않습니다.") }
        song.album = album
        return songRepository.save(song)
    }

    override fun updateSong(albumId: Long, songId: Long, song: Song): Song {
        val album = albumRepository.findById(albumId).orElseThrow { Exception("앨범이 존재하지 않습니다.") }
        val existingSong = songRepository.findByIdAndAlbum(songId, album).orElseThrow { Exception("곡이 존재하지 않습니다.") }
        existingSong.title = song.title
        existingSong.composer = song.composer
        existingSong.lyrics = song.lyrics
        existingSong.link = song.link
        return songRepository.save(existingSong)
    }

    override fun deleteSong(albumId: Long, songId: Long) {
        val album = albumRepository.findById(albumId).orElseThrow { Exception("앨범이 존재하지 않습니다.") }
        val song = songRepository.findByIdAndAlbum(songId, album).orElseThrow { Exception("곡이 존재하지 않습니다.") }
        songRepository.delete(song)
    }

    override fun getSong(albumId: Long, songId: Long): Song? {
        val album = albumRepository.findById(albumId).orElseThrow { Exception("앨범이 존재하지 않습니다.") }
        return songRepository.findByIdAndAlbum(songId, album).orElse(null)
    }

    override fun getSongs(albumId: Long): List<Song> {
        val album = albumRepository.findById(albumId).orElseThrow { Exception("앨범이 존재하지 않습니다.") }
        return songRepository.findByAlbum(album)
    }
}