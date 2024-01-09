package com.example.codingsonyeondan.songs.service

import com.example.codingsonyeondan.domain.album.repository.AlbumRepository
import com.example.codingsonyeondan.songs.Song
import com.example.codingsonyeondan.songs.repository.SongRepository
import org.springframework.stereotype.Service

@Service
class SongServiceImpl(private val songRepository: SongRepository, private val albumRepository: AlbumRepository) : SongService {

    override fun createSong(albumId: Long, song: Song): Song {
        val album = albumRepository.findById(albumId)
        return album.map {
            song.album = it
            songRepository.save(song)
        }.orElseThrow { RuntimeException("Album not found") }
    }

    override fun updateSong(albumId: Long, songId: Long, song: Song): Song {
        val album = albumRepository.findById(albumId)
        return album.map { album ->
            val existingSong = songRepository.findById(songId)
            existingSong.map {
                it.composer = song.composer
                it.lyrics = song.lyrics
                it.link = song.link
                it.album = album
                songRepository.save(it)
            }.orElseThrow { RuntimeException("Song not found") }
        }.orElseThrow { RuntimeException("Album not found") }
    }

    override fun deleteSong(albumId: Long, songId: Long) {
        val album = albumRepository.findById(albumId)
        album.ifPresent {
            val song = songRepository.findById(songId)
            song.ifPresent {
                songRepository.delete(it)
            }
        }
    }

    override fun getSong(albumId: Long, songId: Long): Song? {
        val album = albumRepository.findById(albumId)
        if (album.isPresent) {
            val song = songRepository.findById(songId)
            return song.orElse(null)
        }
        return null
    }

    override fun getSongs(albumId: Long): List<Song> {
        val album = albumRepository.findById(albumId)
        return if (album.isPresent) {
            songRepository.findAllByAlbum(album.get())
        } else {
            emptyList()
        }
    }
}
