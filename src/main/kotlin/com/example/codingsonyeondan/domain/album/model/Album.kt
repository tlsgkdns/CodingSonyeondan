package com.example.codingsonyeondan.domain.album.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.Date

@Entity
@Table(name = "album")
data class Album(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "title")
    val title: String,
    @Column(name = "artist")
    val artist: String,
    @Column(name = "released_date")
    val releasedDate: LocalDateTime
)