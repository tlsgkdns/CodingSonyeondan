package com.example.codingsonyeondan.domain.album.service

import com.example.codingsonyeondan.domain.album.dto.AlbumCreateDTO
import com.example.codingsonyeondan.domain.album.dto.AlbumDTO
import com.example.codingsonyeondan.domain.album.dto.AlbumModifyDTO
import com.example.codingsonyeondan.domain.album.model.Album
import com.example.codingsonyeondan.domain.album.repository.AlbumRepository
import com.example.codingsonyeondan.domain.comment.service.CommentService
import com.example.codingsonyeondan.domain.image.dto.UploadFileDTO
import com.example.codingsonyeondan.domain.image.repository.ImageRepository
import com.example.codingsonyeondan.domain.image.service.ImageService
import com.example.codingsonyeondan.domain.member.repository.MemberRepository
import com.example.codingsonyeondan.domain.songs.dto.SongCreateDTO
import com.example.codingsonyeondan.domain.songs.dto.SongCreateListDTO
import com.example.codingsonyeondan.domain.songs.dto.SongDTO
import com.example.codingsonyeondan.domain.songs.service.SongService
import com.example.codingsonyeondan.infra.exception.ModelNotFoundException
import com.example.codingsonyeondan.infra.exception.UniqueAttributeAlreadyExistException
import com.example.codingsonyeondan.infra.security.SecurityUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class AlbumServiceImpl(
    private val albumRepository: AlbumRepository,
    private val memberRepository: MemberRepository,
    private val imageService: ImageService,
    private val commentService: CommentService,
    private val songService: SongService
): AlbumService {

    private fun getValidateAlbum(albumId: Long): Album
    {
        return albumRepository.findByIdOrNull(albumId) ?: throw ModelNotFoundException("Album", albumId.toString())
    }

    override fun getAlbum(albumId: Long): AlbumDTO {
        return AlbumDTO.from(getValidateAlbum(albumId), songService);
    }
    @Transactional
    override fun createAlbum(albumCreateDTO: AlbumCreateDTO, imageFile: MultipartFile?, songList: SongCreateListDTO): AlbumDTO {
        val album = albumRepository.save(Album(
            title = albumCreateDTO.title,
            artist = albumCreateDTO.artist,
            releasedDate = albumCreateDTO.releasedDate,
            albumImage = imageFile?.let { imageService.uploadImage(UploadFileDTO(it)).to() },
            creator = SecurityUtil.getLoginMember(memberRepository)
        ))
        songList.songs.map { songService.createSong(album.id!!, it) }
        return AlbumDTO.from(album, songService)
    }
    @Transactional
    override fun modifyAlbum(albumId: Long, imageFile: MultipartFile?, albumModifyDTO: AlbumModifyDTO): AlbumDTO {
        val album = albumRepository.findByIdOrNull(albumId)!!
        SecurityUtil.checkUserCanAccessThis(album.creator, "album")
        val albumImage = album.albumImage
        return AlbumDTO.from(albumRepository.save(Album(albumId, albumModifyDTO.title,
            albumModifyDTO.artist, albumModifyDTO.releasedDate,
            imageFile?.let { albumImage?.apply { imageService.deleteImage(this.uuid) };
                                    imageService.uploadImage(UploadFileDTO(it)).to() }
                ?: albumImage
            ,album.creator
            )), songService)
    }
    @Transactional
    override fun deleteAlbum(albumId: Long) {
        val album = getValidateAlbum(albumId)
        SecurityUtil.checkUserCanAccessThis(album.creator, "album")
        album.albumImage?.let { imageService.deleteImage(it.uuid) }
        songService.deleteSongs(albumId); commentService.deleteComments(albumId)
        return albumRepository.delete(album)
    }
}