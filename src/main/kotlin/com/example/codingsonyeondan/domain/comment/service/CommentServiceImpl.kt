package com.example.codingsonyeondan.domain.comment.service

import com.example.codingsonyeondan.domain.album.model.Album
import com.example.codingsonyeondan.domain.album.repository.AlbumRepository
import com.example.codingsonyeondan.domain.comment.dto.CommentResponse
import com.example.codingsonyeondan.domain.comment.dto.CreateCommentDTO
import com.example.codingsonyeondan.domain.comment.dto.UpdateCommentDTO
import com.example.codingsonyeondan.domain.comment.model.Comment
import com.example.codingsonyeondan.domain.comment.repository.CommentRepository
import com.example.codingsonyeondan.domain.member.repository.MemberRepository
import com.example.codingsonyeondan.infra.exception.ModelNotFoundException
import com.example.codingsonyeondan.infra.security.SecurityUtil
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class CommentServiceImpl (
        private val albumRepository: AlbumRepository,
        private val memberRepository: MemberRepository,
        private val commentRepository: CommentRepository
): CommentService {

    private fun checkAlbumAndCommentIsMatch(comment: Comment, albumId: Long)
    {
        if(comment.album?.id != albumId)
            throw NoSuchElementException("앨범에 해당 댓글이 없습니다.")
    }
    @Transactional
    override fun createComment(albumId: Long, request: CreateCommentDTO) : CommentResponse{
        val comment = commentRepository.save(Comment(
                album = albumRepository.findByIdOrNull(albumId)
                    ?: throw ModelNotFoundException("album", albumId.toString()),
                content = request.content,
                writer = SecurityUtil.getLoginMember(memberRepository)
        ))
        return comment.toResponse()

    }
    @Transactional
    override fun updateComment (albumId: Long, commentId:Long, request: UpdateCommentDTO) : CommentResponse  {
        val comment = commentRepository.findByIdOrNull(commentId)
            ?: throw ModelNotFoundException("Comment", commentId.toString())
        checkAlbumAndCommentIsMatch(comment, albumId)
        SecurityUtil.checkUserCanAccessThis(comment.writer, "Comment")
        comment.content = request.content
        return commentRepository.save(comment).toResponse()
    }

    @Transactional
    override fun deleteComment (albumId: Long, commentId:Long){
        val comment = commentRepository.findByIdOrNull(commentId)
            ?: throw ModelNotFoundException ("Comment",commentId.toString())
        checkAlbumAndCommentIsMatch(comment, albumId)
        SecurityUtil.checkUserCanAccessThis(comment.writer, "Comment")
        commentRepository.delete(comment)
    }
    @Transactional
    override fun deleteComments (albumId:Long){
        commentRepository.deleteByAlbumId(albumId)
    }
    override fun getCommentList(albumId: Long): List<CommentResponse> {
        return commentRepository.findByAlbumId(albumId).map{ it.toResponse() }
    }
    override fun getComment(albumId: Long, commentId: Long): CommentResponse {
        val comment = commentRepository.findByIdOrNull(commentId)
                ?: throw ModelNotFoundException("Comment", commentId.toString())
        checkAlbumAndCommentIsMatch(comment, albumId)
        return comment.toResponse()
    }
}