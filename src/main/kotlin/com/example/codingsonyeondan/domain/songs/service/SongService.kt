package com.example.codingsonyeondan.domain.songs.service

import com.example.codingsonyeondan.domain.songs.dto.SongCreateDTO
import com.example.codingsonyeondan.domain.songs.dto.SongDTO
import com.example.codingsonyeondan.domain.songs.dto.SongUpdateDTO



interface SongService {
    fun createSong(albumId: Long, songCreateDTO: SongCreateDTO): SongDTO
    fun updateSong(albumId: Long, songId: Long, songModifyDTO: SongUpdateDTO): SongDTO
    fun deleteSong(albumId: Long, songId: Long)
    fun getSong(albumId: Long, songId: Long): SongDTO?
    fun getSongs(albumId: Long): List<SongDTO>


}