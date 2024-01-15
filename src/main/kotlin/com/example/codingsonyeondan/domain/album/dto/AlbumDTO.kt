package com.example.codingsonyeondan.domain.album.dto

import com.example.codingsonyeondan.domain.album.model.Album
import org.hibernate.validator.constraints.Length
import java.time.LocalDateTime
import java.util.*

data class AlbumDTO(
    val title: String,
    val artist: String,
    val releasedDate: LocalDateTime,
    val albumImage: String? = null
)
{
    companion object{
        fun from(album: Album): AlbumDTO
        {
            return AlbumDTO(album.title, album.artist, album.releasedDate, album.albumImage?.uuid)
        }
    }
}