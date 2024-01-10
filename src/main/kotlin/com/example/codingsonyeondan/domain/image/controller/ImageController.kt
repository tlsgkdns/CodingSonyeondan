package com.example.codingsonyeondan.domain.image.controller

import com.example.codingsonyeondan.domain.image.dto.ImageDTO
import com.example.codingsonyeondan.domain.image.dto.UploadFileDTO
import com.example.codingsonyeondan.domain.image.service.ImageService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/image")
class ImageController (
    private val imageService: ImageService
){
    @GetMapping("/{uuid}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getImage(@PathVariable uuid: String): ResponseEntity<ImageDTO>
    {
        return ResponseEntity.status(HttpStatus.OK)
            .body(imageService.getImage(uuid))
    }
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun uploadImage(@RequestPart multipartFile: MultipartFile): ResponseEntity<ImageDTO>
    {
        return ResponseEntity.status(HttpStatus.OK)
            .body(imageService.uploadImage(UploadFileDTO(multipartFile)))
    }
    @DeleteMapping("/{uuid}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteImage(@PathVariable uuid: String): ResponseEntity<Unit>
    {
        imageService.deleteImage(uuid)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}