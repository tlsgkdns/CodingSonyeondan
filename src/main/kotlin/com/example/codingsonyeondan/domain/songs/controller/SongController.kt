package com.example.codingsonyeondan.domain.songs.controller

import com.example.codingsonyeondan.domain.songs.Song
import com.example.codingsonyeondan.domain.songs.dto.SongCreateDTO
import com.example.codingsonyeondan.domain.songs.dto.SongUpdateDTO
import com.example.codingsonyeondan.domain.songs.service.SongService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize

@RestController
@Tag(name = "Song", description = "곡 API")
@RequestMapping("/album")
class SongController(private val songService: SongService) {
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "곡 정보 업데이트", description = "주어진 앨범 ID와 곡 ID에 해당하는 곡의 정보를 업데이트합니다.")
    @PutMapping("/{albumId}/song/{songId}",produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateSong(
        @Parameter(description = "앨범 ID") @PathVariable albumId: Long,
        @Parameter(description = "곡 ID") @PathVariable songId: Long,
        @Parameter(description = "업데이트할 곡 정보") @RequestBody songUpdateDTO: SongUpdateDTO
    ): ResponseEntity<out Any> {
        val song = songService.getSong(albumId, songId)
        return if (song != null) {
            songService.updateSong(albumId, songId, songUpdateDTO)
            ResponseEntity.ok(songUpdateDTO)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("message" to "존재하지 않는 곡입니다."))
        }
    }
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "곡 삭제", description = "주어진 앨범 ID와 곡 ID에 해당하는 곡을 삭제합니다.")
    @DeleteMapping("/{albumId}/song/{songId}", produces = [MediaType.TEXT_PLAIN_VALUE])
    fun deleteSong(
        @Parameter(description = "앨범 ID") @PathVariable albumId: Long,
        @Parameter(description = "곡 ID") @PathVariable songId: Long
    ): ResponseEntity<out Any> {
        val song = songService.getSong(albumId, songId)
        return if (song != null) {
            songService.deleteSong(albumId, songId)
            ResponseEntity.ok(mapOf("message" to "곡을 삭제하였습니다."))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 곡입니다.")
        }
    }

    @GetMapping("/{albumId}/song/{songId}",produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "특정 곡 조회", description = "주어진 앨범 ID와 곡 ID에 해당하는 곡을 조회합니다.")
    fun getSong(
        @Parameter(description = "앨범 ID") @PathVariable albumId: Long,
        @Parameter(description = "곡 ID") @PathVariable songId: Long
    ): ResponseEntity<out Any> {
        val song = songService.getSong(albumId, songId)
        return if (song != null) {
            ResponseEntity.ok(song)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("message" to "존재하지 않는 곡입니다."))
        }
    }

    @GetMapping("/{albumId}/songs",produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "앨범의 모든 곡 조회", description = "주어진 앨범 ID에 해당하는 모든 곡을 조회합니다.")
    fun getSongs(
        @Parameter(description = "앨범 ID") @PathVariable albumId: Long
    ): ResponseEntity<out Any> {
        val songs = songService.getSongs(albumId)
        return if (songs.isNotEmpty()) {
            ResponseEntity.ok(songs)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("message" to "존재하지 않는 앨범입니다."))
        }
    }
}