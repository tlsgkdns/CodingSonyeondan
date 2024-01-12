package com.example.codingsonyeondan.domain.album.model

import com.example.codingsonyeondan.domain.comment.model.Comment
import com.example.codingsonyeondan.domain.image.model.Image
import com.example.codingsonyeondan.domain.member.model.Member
import com.example.codingsonyeondan.domain.songs.Song
import jakarta.persistence.*
import org.springframework.data.jpa.repository.EntityGraph
import java.time.LocalDateTime

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
    val releasedDate: LocalDateTime,

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "albumimage")
    var albumImage: Image? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator")
    val creator: Member
)