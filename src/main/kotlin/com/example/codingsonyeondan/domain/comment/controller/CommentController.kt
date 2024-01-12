package com.example.codingsonyeondan.domain.comment.controller

import com.example.codingsonyeondan.domain.comment.dto.CommentResponse
import com.example.codingsonyeondan.domain.comment.dto.CreateCommentDTO
import com.example.codingsonyeondan.domain.comment.dto.UpdateCommentDTO
import com.example.codingsonyeondan.domain.comment.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/album/{albumId}/comment")
class CommentController (
    private val commentService: CommentService
) {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getCommentList(@PathVariable albumId:Long) : ResponseEntity<List<CommentResponse>>{
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.getCommentList(albumId))
    }

    @GetMapping("/{commentId}",produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getComment(@PathVariable albumId:Long,
                   @PathVariable commentId :Long,
                   ): ResponseEntity<CommentResponse>{
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.getComment(albumId,commentId))
    }
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createComment (@PathVariable albumId: Long,
                      @RequestBody createCommentDTO : CreateCommentDTO): ResponseEntity<CommentResponse> {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.createComment(albumId,createCommentDTO))
    }
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{commentId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateComment (@PathVariable albumId:Long,
                       @PathVariable commentId: Long,
                       @RequestBody updateCommentDTO : UpdateCommentDTO) : ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(albumId, commentId, updateCommentDTO))
    }
    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/{commentId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteComment(@PathVariable commentId:Long, @PathVariable albumId : Long): ResponseEntity<Unit>
    {
        commentService.deleteComments(commentId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}