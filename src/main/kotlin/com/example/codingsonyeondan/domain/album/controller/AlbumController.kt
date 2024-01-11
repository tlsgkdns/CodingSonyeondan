package com.example.codingsonyeondan.domain.album.controller

import com.example.codingsonyeondan.domain.album.dto.AlbumCreateDTO
import com.example.codingsonyeondan.domain.album.dto.AlbumDTO
import com.example.codingsonyeondan.domain.album.dto.AlbumModifyDTO
import com.example.codingsonyeondan.domain.album.service.AlbumService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
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
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createAlbum(@RequestPart(required = false) image: MultipartFile?,
                    @RequestPart albumCreateDTO: AlbumCreateDTO)
    : ResponseEntity<AlbumDTO>
    {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(albumService.createAlbum(albumCreateDTO, image))
    }
    @PutMapping("/{albumId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun modifyAlbum(@PathVariable albumId: Long, @RequestBody albumModifyDTO: AlbumModifyDTO): ResponseEntity<AlbumDTO>
    {
        return ResponseEntity.ok(albumService.modifyAlbum(albumId, albumModifyDTO))
    }
    @DeleteMapping("/{albumId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteAlbum(@PathVariable albumId: Long): ResponseEntity<Unit>
    {
        albumService.deleteAlbum(albumId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}