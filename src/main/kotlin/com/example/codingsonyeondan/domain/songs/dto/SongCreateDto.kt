package com.example.codingsonyeondan.domain.songs.dto

data class SongCreateDTO(
    val title: String,
    val composer: String,
    val lyrics: String,
    val link: String
)
