package com.example.codingsonyeondan.domain.comment.controller

import com.example.codingsonyeondan.domain.comment.dto.CommentResponse
import com.example.codingsonyeondan.domain.comment.dto.CreateCommentDTO
import com.example.codingsonyeondan.domain.comment.dto.UpdateCommentDTO
import com.example.codingsonyeondan.domain.comment.service.CommentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
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
@Tag(name = "Comment", description = "댓글 API")
@RequestMapping("/album/{albumId}/comment")
class CommentController (
    private val commentService: CommentService
) {

    @GetMapping("/{commentId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "특정 댓글 조회", description = "앨범 ID와 댓글 ID를 통해 특정 댓글을 조회합니다.")
    fun getComment(
        @Parameter(description = "조회할 앨범의 ID") @PathVariable albumId: Long,
        @Parameter(description = "조회할 댓글의 ID") @PathVariable commentId: Long,
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(commentService.getComment(albumId, commentId))
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "댓글 생성", description = "사용자 권한이 있는 경우, 앨범 ID와 댓글 내용을 통해 새로운 댓글을 생성합니다.")
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createComment(
        @Parameter(description = "댓글을 생성할 앨범의 ID") @PathVariable albumId: Long,
        @Parameter(description = "생성할 댓글의 내용") @RequestBody createCommentDTO: CreateCommentDTO
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(commentService.createComment(albumId, createCommentDTO))
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "댓글 수정", description = "사용자 권한이 있는 경우, 앨범 ID와 댓글 ID, 수정할 댓글 내용을 통해 특정 댓글을 수정합니다.")
    @PutMapping("/{commentId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateComment(
        @Parameter(description = "댓글을 수정할 앨범의 ID") @PathVariable albumId: Long,
        @Parameter(description = "수정할 댓글의 ID") @PathVariable commentId: Long,
        @Parameter(description = "수정할 댓글의 내용") @RequestBody updateCommentDTO: UpdateCommentDTO
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(albumId, commentId, updateCommentDTO))
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "댓글 삭제", description = "사용자 권한이 있는 경우, 앨범 ID와 댓글 ID를 통해 특정 댓글을 삭제합니다.")
    @DeleteMapping("/{commentId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteComment(
        @Parameter(description = "삭제할 댓글의 ID") @PathVariable commentId: Long,
        @Parameter(description = "댓글을 삭제할 앨범의 ID") @PathVariable albumId: Long
    ): ResponseEntity<Unit> {
        commentService.deleteComment(albumId, commentId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}