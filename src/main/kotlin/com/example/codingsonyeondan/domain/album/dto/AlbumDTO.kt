package com.example.codingsonyeondan.domain.album.dto

import com.example.codingsonyeondan.domain.album.model.Album
import com.example.codingsonyeondan.domain.songs.dto.SongDTO
import com.example.codingsonyeondan.domain.songs.service.SongService
import org.hibernate.validator.constraints.Length
import java.time.LocalDateTime
import java.util.*

data class AlbumDTO(
    val title: String,
    val artist: String,
    val releasedDate: LocalDateTime,
    val albumImage: String? = null,
    val songList: List<SongDTO>
)
{
    companion object{
        fun from(album: Album, songService: SongService): AlbumDTO
        {
            return AlbumDTO(album.title, album.artist, album.releasedDate,
                album.albumImage?.uuid, songService.getSongs(album.id!!))
        }
    }
}