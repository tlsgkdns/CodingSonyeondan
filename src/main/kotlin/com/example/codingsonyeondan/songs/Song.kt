package com.example.codingsonyeondan.songs

import jakarta.persistence.*

@Entity
@Table(name = "song", schema = "public")
data class Song(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,

    @Column(name = "composer")
    val composer: String,

    @Column(name = "lyrics")
    val lyrics: String,

    @Column(name = "link")
    val link: String,

    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    var album: Album? = null
)