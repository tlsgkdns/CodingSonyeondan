package com.example.codingsonyeondan.domain.comment.service

import com.example.codingsonyeondan.domain.album.model.Album
import com.example.codingsonyeondan.domain.comment.dto.CommentResponse
import com.example.codingsonyeondan.domain.comment.dto.CreateCommentDTO
import com.example.codingsonyeondan.domain.comment.dto.UpdateCommentDTO
import com.example.codingsonyeondan.domain.comment.model.Comment
import com.example.codingsonyeondan.domain.comment.repository.CommentRepository
import com.example.codingsonyeondan.infra.exception.ModelNotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class CommentServiceImpl (
        private val commentRepository: CommentRepository
): CommentService {

    @Transactional
    override fun createComment(albumId: Long, request: CreateCommentDTO) : CommentResponse{
        val comment = commentRepository.save(Comment(
                albumId = albumId,
                content = request.content

        ))
        return comment.toResponse()

    }
    @Transactional
    override fun updateComment (commentId:Long, request: UpdateCommentDTO) : CommentResponse  {
        val comment1 = commentRepository.findByIdOrNull(commentId)?: throw ModelNotFoundException(
                "Comment", commentId.toString())

        val content = request.content
        comment1.content = content

        return commentRepository.save(comment1).toResponse()
    }

    override fun deleteComment (commentId:Long){
        val comment = commentRepository.findByIdOrNull(commentId)?: throw ModelNotFoundException (
                "Comment",commentId.toString())
        commentRepository.delete(comment)

    }

    override fun getCommentList(albumId: Long): List<CommentResponse> {

        return commentRepository.findByAlbumId(albumId).map{ it.toResponse() }

    }

    override fun getComment(albumId: Long, commentId: Long): CommentResponse {
        val comment = commentRepository.findByIdOrNull(commentId)
                ?: throw ModelNotFoundException("Comment", commentId.toString())
        return comment.toResponse()
    }
}