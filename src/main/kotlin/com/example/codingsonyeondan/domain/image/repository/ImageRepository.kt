package com.example.codingsonyeondan.domain.image.repository

import com.example.codingsonyeondan.domain.image.model.Image
import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository: JpaRepository<Image, String>
{

}