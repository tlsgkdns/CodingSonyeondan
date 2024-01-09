package com.example.codingsonyeondan.domain.songs.service

import com.example.codingsonyeondan.domain.songs.Song

interface SongService {
    fun createSong(albumId: Long, song: Song): Song
    fun updateSong(albumId: Long, songId: Long, song: Song): Song
    fun deleteSong(albumId: Long, songId: Long)
    fun getSong(albumId: Long, songId: Long): Song?
    fun getSongs(albumId: Long): List<Song>
}