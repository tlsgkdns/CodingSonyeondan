package com.example.codingsonyeondan.songs

import com.example.codingsonyeondan.domain.album.model.Album
import jakarta.persistence.*

@Entity
@Table(name = "song", schema = "public")
data class Song(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,

    @Column(name = "composer")
    var composer: String,

    @Column(name = "lyrics")
    var lyrics: String,

    @Column(name = "link")
    var link: String,

    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    var album: Album? = null
)