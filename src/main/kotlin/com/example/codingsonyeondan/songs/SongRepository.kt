package com.example.codingsonyeondan.songs

import org.springframework.data.jpa.repository.JpaRepository

interface SongRepository : JpaRepository<Song, Long> {

}