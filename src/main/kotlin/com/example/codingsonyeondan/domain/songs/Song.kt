package com.example.codingsonyeondan.domain.songs

import com.example.codingsonyeondan.domain.album.model.Album
import com.example.codingsonyeondan.domain.songs.dto.SongDTO
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "song", schema = "public")
data class Song(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,

    @Column(name = "title")
    var title: String,

    @Column(name = "composer")
    var composer: String,

    @Column(name = "lyrics")
    var lyrics: String,
    @Column(name = "link")
    var link: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album")
    @OnDelete(action = OnDeleteAction.CASCADE)
    var album: Album?
){
    companion object {
        fun toDto(song: Song): SongDTO {
            return SongDTO(
                id = song.id,
                title = song.title,
                composer = song.composer,
                lyrics = song.lyrics,
                link = song.link,
            )
        }
    }
}