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
    private val imageService: ImageService
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
    override fun createAlbum(albumCreateDTO: AlbumCreateDTO, imageFile: MultipartFile?): AlbumDTO {
        checkTitleIsAlreadyExist(albumCreateDTO.title)
        return AlbumDTO.from(albumRepository.save(Album(
            title = albumCreateDTO.title,
            artist = albumCreateDTO.artist,
            releasedDate = albumCreateDTO.releasedDate,
            albumImage = imageFile?.let { imageService.uploadImage(UploadFileDTO(it)).to() }
        )))
    }
    @Transactional
    override fun modifyAlbum(albumId: Long, imageFile: MultipartFile?, albumModifyDTO: AlbumModifyDTO): AlbumDTO {
        checkTitleIsAlreadyExist(albumModifyDTO.title)
        val albumImage = albumRepository.findByIdOrNull(albumId)?.albumImage
        return AlbumDTO.from(albumRepository.save(Album(albumId, albumModifyDTO.title,
            albumModifyDTO.artist, albumModifyDTO.releasedDate,
            imageFile?.let { albumImage?.apply { imageService.deleteImage(this.uuid) };
                                    imageService.uploadImage(UploadFileDTO(it)).to() }
                ?: albumImage
            )))
    }
    @Transactional
    override fun deleteAlbum(albumId: Long) {
        val album = getValidateAlbum(albumId)
        album.albumImage?.let { imageService.deleteImage(it.uuid) }
        return albumRepository.delete(album)
    }
}