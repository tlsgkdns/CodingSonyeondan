package com.example.codingsonyeondan.domain.album.controller

import com.example.codingsonyeondan.domain.album.dto.AlbumCreateDTO
import com.example.codingsonyeondan.domain.album.dto.AlbumDTO
import com.example.codingsonyeondan.domain.album.dto.AlbumModifyDTO
import com.example.codingsonyeondan.domain.album.service.AlbumService
import com.example.codingsonyeondan.domain.songs.dto.SongCreateDTO
import com.example.codingsonyeondan.domain.songs.dto.SongCreateListDTO
import com.example.codingsonyeondan.domain.songs.dto.SongDTO
import com.example.codingsonyeondan.infra.exception.InvalidDTOError
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@Tag(name = "Album", description = "앨범 API")
@RequestMapping("/album")
class AlbumController(
    private val albumService: AlbumService
) {
    @GetMapping("/{albumId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "특정 앨범 정보 조회", description = "앨범 ID를 통해 앨범 정보를 조회합니다.")
    fun getAlbum(@Parameter(description = "앨범 ID") @PathVariable albumId: Long): ResponseEntity<AlbumDTO>
    {
        return ResponseEntity.ok(albumService.getAlbum(albumId))
    }
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "새 앨범 생성", description = "사용자 권한이 있는 경우 새 앨범을 생성합니다.")
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createAlbum(@Parameter(description = "이미지 파일") @RequestPart(required = false) image: MultipartFile?,
                    @Parameter(description = "앨범 생성 정보") @RequestPart @Valid albumCreateDTO: AlbumCreateDTO,
                    @Parameter(description = "곡 생성 정보 리스트") @RequestPart songs: SongCreateListDTO,
                    @Parameter(hidden = true) bindingResult: BindingResult)
            : ResponseEntity<AlbumDTO>
    {
        if(bindingResult.hasErrors()) throw InvalidDTOError("AlbumCreateDTO", bindingResult.fieldError?.defaultMessage ?: "")
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(albumService.createAlbum(albumCreateDTO, image, songs))
    }
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "앨범 정보 수정", description = "사용자 권한이 있는 경우 특정 앨범 정보를 수정합니다.")
    @PutMapping("/{albumId}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun modifyAlbum(@Parameter(description = "앨범 ID") @PathVariable albumId: Long,
                    @Parameter(description = "이미지 파일") @RequestPart(required = false) image: MultipartFile?,
                    @Parameter(description = "앨범 수정 정보") @RequestPart @Valid albumModifyDTO: AlbumModifyDTO): ResponseEntity<AlbumDTO>
    {
        return ResponseEntity.ok(albumService.modifyAlbum(albumId, image, albumModifyDTO))
    }
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "앨범 삭제", description = "사용자 권한이 있는 경우 특정 앨범을 삭제합니다.")
    @DeleteMapping("/{albumId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteAlbum(@Parameter(description = "앨범 ID") @PathVariable albumId: Long): ResponseEntity<Unit>
    {
        albumService.deleteAlbum(albumId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}