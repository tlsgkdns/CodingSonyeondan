package com.example.codingsonyeondan.domain.album.dto

import org.hibernate.validator.constraints.Length
import java.time.LocalDateTime

data class AlbumModifyDTO(
    @field:Length(min = 1, max = 200, message = "title length must between 1 and 200")
    val title: String,
    @field:Length(min = 1, max = 50, message = "artist length must between 1 and 50")
    val artist: String,
    val releasedDate: LocalDateTime
)