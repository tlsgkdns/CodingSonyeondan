package com.example.codingsonyeondan.domain.image.service

import com.example.codingsonyeondan.domain.album.model.Album
import com.example.codingsonyeondan.domain.image.dto.ImageDTO
import com.example.codingsonyeondan.domain.image.dto.UploadFileDTO

interface ImageService {
    fun uploadImage(uploadFileDTO: UploadFileDTO): ImageDTO
    fun deleteImage(uuid: String)
    fun getImage(uuid: String): ImageDTO
}