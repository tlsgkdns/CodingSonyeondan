package com.example.codingsonyeondan.songs.repository

import com.example.codingsonyeondan.domain.album.model.Album
import com.example.codingsonyeondan.songs.Song
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SongRepository : JpaRepository<Song, Long> {
    fun findAllByAlbum(album: Album): List<Song>
}