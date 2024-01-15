package com.example.codingsonyeondan.domain.image.dto

import com.example.codingsonyeondan.domain.album.model.Album
import com.example.codingsonyeondan.domain.image.model.Image

data class ImageDTO(
    val uuid: String,
    val filename: String = "default"
)
{
    companion object
    {
        fun from(image: Image): ImageDTO
        {
            return ImageDTO(image.uuid, image.filename)
        }
    }
    fun to(): Image
    {
        return Image(uuid, filename)
    }
}
