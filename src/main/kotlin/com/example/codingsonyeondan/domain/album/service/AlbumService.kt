package com.example.codingsonyeondan.domain.album.service

import com.example.codingsonyeondan.domain.album.dto.AlbumCreateDTO
import com.example.codingsonyeondan.domain.album.dto.AlbumDTO
import com.example.codingsonyeondan.domain.album.dto.AlbumModifyDTO
import com.example.codingsonyeondan.domain.album.model.Album

interface AlbumService {

    fun getAlbum(albumId: Long): AlbumDTO
    fun createAlbum(albumCreateDTO: AlbumCreateDTO) : AlbumDTO
    fun modifyAlbum(albumId: Long, albumModifyDTO: AlbumModifyDTO) : AlbumDTO
    fun deleteAlbum(albumId: Long)
}