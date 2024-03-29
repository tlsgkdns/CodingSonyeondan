package com.example.codingsonyeondan.domain.songs.repository

import com.example.codingsonyeondan.domain.album.model.Album
import com.example.codingsonyeondan.domain.songs.Song
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SongRepository : JpaRepository<Song, Long> {
    fun findByAlbumId(albumId: Long): List<Song>
    fun findByAlbumIdAndId(albumId: Long, songId: Long): Song?

    fun deleteByAlbumId(album: Long)
}