package com.example.codingsonyeondan.domain.album.controller

import com.example.codingsonyeondan.domain.album.dto.AlbumCreateDTO
import com.example.codingsonyeondan.domain.album.dto.AlbumDTO
import com.example.codingsonyeondan.domain.album.dto.AlbumModifyDTO
import com.example.codingsonyeondan.domain.album.service.AlbumService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/album")
class AlbumController(
    private val albumService: AlbumService
) {
    @GetMapping("/{albumId}")
    fun getAlbum(@PathVariable albumId: Long): ResponseEntity<AlbumDTO>
    {
        return ResponseEntity.status(HttpStatus.OK)
            .body(albumService.getAlbum(albumId))
    }

    @PostMapping()
    fun createAlbum(@RequestBody albumCreateDTO: AlbumCreateDTO):
            ResponseEntity<AlbumDTO>
    {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(albumService.createAlbum(albumCreateDTO))
    }

    @PutMapping("/{albumId}")
    fun modifyAlbum(@PathVariable albumId: Long, @RequestBody albumModifyDTO: AlbumModifyDTO): ResponseEntity<AlbumDTO>
    {
        return ResponseEntity.status(HttpStatus.OK)
            .body(albumService.modifyAlbum(albumId, albumModifyDTO))
    }

    @DeleteMapping("/{albumId}")
    fun deleteAlbum(@PathVariable albumId: Long): ResponseEntity<Unit>
    {
        albumService.deleteAlbum(albumId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}