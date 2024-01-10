package com.example.codingsonyeondan.domain.album.service

import com.example.codingsonyeondan.domain.album.dto.AlbumCreateDTO
import com.example.codingsonyeondan.domain.album.dto.AlbumDTO
import com.example.codingsonyeondan.domain.album.dto.AlbumModifyDTO
import com.example.codingsonyeondan.domain.album.model.Album
import com.example.codingsonyeondan.domain.album.repository.AlbumRepository
import com.example.codingsonyeondan.domain.image.dto.UploadFileDTO
import com.example.codingsonyeondan.domain.image.repository.ImageRepository
import com.example.codingsonyeondan.domain.image.service.ImageService
import com.example.codingsonyeondan.infra.exception.ModelNotFoundException
import com.example.codingsonyeondan.infra.exception.UniqueAttributeAlreadyExistException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class AlbumServiceImpl(
    private val albumRepository: AlbumRepository,
    private val imageService: ImageService,
    private val imageRepository: ImageRepository
): AlbumService {

    private fun getValidateAlbum(albumId: Long): Album
    {
        return albumRepository.findByIdOrNull(albumId) ?: throw ModelNotFoundException("Album", albumId.toString())
    }
    private fun checkTitleIsAlreadyExist(title: String)
    {
        if(albumRepository.existsByTitle(title))
            throw UniqueAttributeAlreadyExistException("album", "title", title)
    }
    override fun getAlbum(albumId: Long): AlbumDTO {
        return AlbumDTO.from(getValidateAlbum(albumId));
    }

    @Transactional
    override fun createAlbum(albumCreateDTO: AlbumCreateDTO, image: MultipartFile?): AlbumDTO {
        checkTitleIsAlreadyExist(albumCreateDTO.title)
        val album = Album(
            title = albumCreateDTO.title,
            artist = albumCreateDTO.artist,
            releasedDate = albumCreateDTO.releasedDate
        )
        if(image != null)
        {
            val img = imageService.uploadImage(UploadFileDTO(image))
            album.albumImage = img.to()
        }
        return AlbumDTO.from(albumRepository.save(album))
    }
    @Transactional
    override fun modifyAlbum(albumId: Long, albumModifyDTO: AlbumModifyDTO): AlbumDTO {
        checkTitleIsAlreadyExist(albumModifyDTO.title)
        getValidateAlbum(albumId)
        return AlbumDTO.from(albumRepository.save(Album(albumId, albumModifyDTO.title,
            albumModifyDTO.artist, albumModifyDTO.releasedDate)))
    }
    @Transactional
    override fun deleteAlbum(albumId: Long) {
        println(getValidateAlbum(albumId).title)
        return albumRepository.delete(getValidateAlbum(albumId))
    }
}