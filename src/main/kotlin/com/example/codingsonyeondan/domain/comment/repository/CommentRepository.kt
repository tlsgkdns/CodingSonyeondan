package com.example.codingsonyeondan.domain.comment.repository

import com.example.codingsonyeondan.domain.comment.model.Comment
import com.example.codingsonyeondan.domain.songs.Song
import org.springframework.data.jpa.repository.JpaRepository


interface CommentRepository: JpaRepository<Comment, Long> {
    fun findByAlbumId(albumId: Long): List<Comment>
}