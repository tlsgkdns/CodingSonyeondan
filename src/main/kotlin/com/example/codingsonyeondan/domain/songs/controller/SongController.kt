package com.example.codingsonyeondan.domain.songs.controller

import com.example.codingsonyeondan.domain.songs.Song
import com.example.codingsonyeondan.domain.songs.dto.SongCreateDTO
import com.example.codingsonyeondan.domain.songs.dto.SongUpdateDTO
import com.example.codingsonyeondan.domain.songs.service.SongService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize

@RestController

@RequestMapping("/album")
class SongController(private val songService: SongService) {
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{albumId}/song/{songId}",produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateSong(@PathVariable albumId: Long, @PathVariable songId: Long, @RequestBody songUpdateDTO: SongUpdateDTO): ResponseEntity<out Any> {
        val song = songService.getSong(albumId, songId)
        return if (song != null) {
            songService.updateSong(albumId, songId, songUpdateDTO)
            ResponseEntity.ok(songUpdateDTO)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("message" to "존재하지 않는 곡입니다."))
        }
    }
    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/{albumId}/song/{songId}", produces = [MediaType.TEXT_PLAIN_VALUE])
    fun deleteSong(@PathVariable albumId: Long, @PathVariable songId: Long): ResponseEntity<out Any> {
        val song = songService.getSong(albumId, songId)
        return if (song != null) {
            songService.deleteSong(albumId, songId)
            ResponseEntity.ok(mapOf("message" to "곡을 삭제하였습니다."))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 곡입니다.")
        }
    }

    @GetMapping("/{albumId}/song/{songId}",produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getSong(@PathVariable albumId: Long, @PathVariable songId: Long): ResponseEntity<out Any> {
        val song = songService.getSong(albumId, songId)
        return if (song != null) {
            ResponseEntity.ok(song)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("message" to "존재하지 않는 곡입니다."))
        }
    }

    @GetMapping("/{albumId}/songs",produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getSongs(@PathVariable albumId: Long): ResponseEntity<out Any> {
        val songs = songService.getSongs(albumId)
        return if (songs.isNotEmpty()) {
            ResponseEntity.ok(songs)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("message" to "존재하지 않는 앨범입니다."))
        }
    }
}