package com.example.codingsonyeondan.domain.songs.dto

import com.example.codingsonyeondan.domain.songs.Song

data class SongDTO(
    val id: Long,
    val title: String,
    val composer: String,
    val lyrics: String,
    val link: String
)
{
companion object {
    fun from(song: Song): SongDTO {
        return SongDTO(
            id = song.id,
            title = song.title,
            composer = song.composer,
            lyrics = song.lyrics,
            link = song.link
        )
    }
}
}