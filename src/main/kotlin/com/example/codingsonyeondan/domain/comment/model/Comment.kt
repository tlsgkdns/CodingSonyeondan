package com.example.codingsonyeondan.domain.comment.model

import com.example.codingsonyeondan.domain.album.model.Album
import com.example.codingsonyeondan.domain.comment.dto.CommentResponse
import com.example.codingsonyeondan.domain.member.model.Member
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDateTime

@Entity
@Table(name = "comment")
data class Comment (
        @ManyToOne
        @JoinColumn(name = "album")
        @OnDelete(action = OnDeleteAction.CASCADE)
        val album: Album?,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        @Column(name = "content")
        var content: String,
        @ManyToOne
        @JoinColumn(name = "writer")
        var writer: Member
) {
        fun toResponse():CommentResponse {
                return CommentResponse(album?.id, id, content )
        }
}