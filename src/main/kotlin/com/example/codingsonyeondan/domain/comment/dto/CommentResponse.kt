package com.example.codingsonyeondan.domain.comment.dto

import jakarta.persistence.Id

data class CommentResponse (
        val albumId : Long?,
        val commentId : Long?,
        val content : String
)