package com.example.codingsonyeondan.domain.songs.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "곡 업데이트 정보")
data class SongUpdateDTO(
    @Schema(description = "곡 이름")
    val title: String,

    @Schema(description = "작곡가")
    val composer: String,

    @Schema(description = "가사")
    val lyrics: String,

    @Schema(description = "곡 링크")
    val link: String
)