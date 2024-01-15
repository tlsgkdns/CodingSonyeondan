package com.example.codingsonyeondan.domain.image.service

import com.example.codingsonyeondan.domain.album.model.Album
import com.example.codingsonyeondan.domain.image.dto.ImageDTO
import com.example.codingsonyeondan.domain.image.dto.UploadFileDTO
import com.example.codingsonyeondan.domain.image.model.Image
import com.example.codingsonyeondan.domain.image.repository.ImageRepository
import com.example.codingsonyeondan.infra.exception.ModelNotFoundException
import com.example.codingsonyeondan.infra.exception.NoImageException
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Paths
import java.util.UUID

@Service
class ImageServiceImpl(
    @Value("\${org.example.upload.path}")
    val uploadPath: String,
    private val imageRepository: ImageRepository
): ImageService {
    fun checkUploadedFileIsValidAndGetImage(uploadFileDTO: UploadFileDTO): MultipartFile
    {
        val image = uploadFileDTO.multipartFile ?: throw NoImageException()
        if(!image.contentType?.startsWith("image")!!) throw NoImageException()
        return image
    }
    fun getValidatedImage(uuid: String): Image
    {
        return imageRepository.findByIdOrNull(uuid) ?: throw ModelNotFoundException("Image", uuid)
    }
    @Transactional
    override fun uploadImage(uploadFileDTO: UploadFileDTO): ImageDTO {
        val image = checkUploadedFileIsValidAndGetImage(uploadFileDTO)
        var originalName = image.originalFilename!!
        originalName = originalName.replace("_", "-")
        val uuid = UUID.randomUUID().toString()
        val savePath = Paths.get(uploadPath, uuid+"_"+originalName)
        image.transferTo(savePath)
        return ImageDTO(uuid, originalName)
    }

    override fun getImage(uuid: String): ImageDTO {
        val image = getValidatedImage(uuid)
        return ImageDTO.from(image)
    }
    @Transactional
    override fun deleteImage(uuid: String)
    {
        val image = getValidatedImage(uuid)
        imageRepository.delete(image)
        File(Paths.get(uploadPath, image.getLink()).toUri()).delete()
    }
}