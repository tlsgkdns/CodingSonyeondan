package com.example.codingsonyeondan.domain.comment.service

import com.example.codingsonyeondan.domain.comment.dto.CommentResponse
import com.example.codingsonyeondan.domain.comment.dto.CreateCommentDTO
import com.example.codingsonyeondan.domain.comment.dto.UpdateCommentDTO

interface CommentService {

    fun createComment (albumId: Long, request: CreateCommentDTO) : CommentResponse

    fun updateComment (commentId:Long, request: UpdateCommentDTO) : CommentResponse

    fun deleteComment(commentId:Long)

    fun getCommentList(albumId:Long) : List<CommentResponse>

    fun getComment(albumId: Long, commentId: Long) : CommentResponse
}
