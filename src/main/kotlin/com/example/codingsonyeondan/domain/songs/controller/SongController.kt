package com.example.codingsonyeondan.domain.songs.controller

import com.example.codingsonyeondan.domain.songs.Song
import com.example.codingsonyeondan.domain.songs.service.SongService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpStatus

@RestController
@RequestMapping("/album")
class SongController(private val songService: SongService) {

    @PostMapping("/{albumId}/song")
    fun createSong(@PathVariable albumId: Long, @RequestBody song: Song): ResponseEntity<String> {
        songService.createSong(albumId, song)
        return ResponseEntity.ok("곡을 생성하였습니다.")
    }

    @PutMapping("/{albumId}/song/{songId}")
    fun updateSong(@PathVariable albumId: Long, @PathVariable songId: Long, @RequestBody song: Song): ResponseEntity<String> {
        songService.updateSong(albumId, songId, song)
        return ResponseEntity.ok("곡을 수정하였습니다.")
    }

    @DeleteMapping("/{albumId}/song/{songId}")
    fun deleteSong(@PathVariable albumId: Long, @PathVariable songId: Long): ResponseEntity<String> {
        songService.deleteSong(albumId, songId)
        return ResponseEntity.ok("곡을 삭제하였습니다.")
    }

    @GetMapping("/{albumId}/song/{songId}")
    fun getSong(@PathVariable albumId: Long, @PathVariable songId: Long): ResponseEntity<out Any> {
        val song = songService.getSong(albumId, songId)
        return if (song != null) {
            ResponseEntity.ok(song)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 곡입니다.")
        }
    }

    @GetMapping("/{albumId}/songs")
    fun getSongs(@PathVariable albumId: Long): ResponseEntity<out Any> {
        val songs = songService.getSongs(albumId)
        return if (songs.isNotEmpty()) {
            ResponseEntity.ok(songs)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 앨범입니다.")
        }
    }
}