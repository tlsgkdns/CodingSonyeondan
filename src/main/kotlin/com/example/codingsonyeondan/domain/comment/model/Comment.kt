package com.example.codingsonyeondan.domain.comment.model

import com.example.codingsonyeondan.domain.comment.dto.CommentResponse
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "comment")
data class Comment (
        @Column(name = "albumId")
        val albumId: Long?,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val commentId: Long? = null,
        @Column(name = "content")
        var content: String
) {
        fun toResponse():CommentResponse {
                return CommentResponse(albumId, commentId, content )

                //comment를 commentResponse로 바꾸기
        }
}