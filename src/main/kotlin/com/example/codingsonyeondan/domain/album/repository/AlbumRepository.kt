package com.example.codingsonyeondan.domain.album.repository

import com.example.codingsonyeondan.domain.album.model.Album
import org.springframework.data.jpa.repository.JpaRepository

interface AlbumRepository: JpaRepository<Album, Long> {

}