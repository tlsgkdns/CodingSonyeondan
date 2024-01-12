package com.example.codingsonyeondan.domain.album.controller

import com.example.codingsonyeondan.domain.album.dto.AlbumCreateDTO
import com.example.codingsonyeondan.domain.album.dto.AlbumDTO
import com.example.codingsonyeondan.domain.album.dto.AlbumModifyDTO
import com.example.codingsonyeondan.domain.album.service.AlbumService
import com.example.codingsonyeondan.domain.songs.dto.SongCreateDTO
import com.example.codingsonyeondan.domain.songs.dto.SongCreateListDTO
import com.example.codingsonyeondan.domain.songs.dto.SongDTO
import com.example.codingsonyeondan.infra.exception.InvalidDTOError
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/album")
class AlbumController(
    private val albumService: AlbumService
) {
    @GetMapping("/{albumId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAlbum(@PathVariable albumId: Long): ResponseEntity<AlbumDTO>
    {
        return ResponseEntity.ok(albumService.getAlbum(albumId))
    }
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createAlbum(@RequestPart(required = false) image: MultipartFile?,
                    @RequestPart @Valid albumCreateDTO: AlbumCreateDTO,
                    @RequestPart songs: SongCreateListDTO,
                    bindingResult: BindingResult)
    : ResponseEntity<AlbumDTO>
    {
        if(bindingResult.hasErrors()) throw InvalidDTOError("AlbumCreateDTO", bindingResult.fieldError?.defaultMessage ?: "")
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(albumService.createAlbum(albumCreateDTO, image, songs))
    }
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{albumId}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun modifyAlbum(@PathVariable albumId: Long,
                    @RequestPart(required = false) image: MultipartFile?,
                    @RequestPart @Valid albumModifyDTO: AlbumModifyDTO): ResponseEntity<AlbumDTO>
    {
        return ResponseEntity.ok(albumService.modifyAlbum(albumId, image, albumModifyDTO))
    }
    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/{albumId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteAlbum(@PathVariable albumId: Long): ResponseEntity<Unit>
    {
        albumService.deleteAlbum(albumId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}