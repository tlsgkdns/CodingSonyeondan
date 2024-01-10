package com.example.codingsonyeondan.domain.image.model

import com.example.codingsonyeondan.infra.annotation.AllOpen
import jakarta.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "image")
@AllOpen
class Image(
    @Id
    val uuid: String,
    val filename: String
)
{
    fun getLink(): String{return uuid + "_" + filename}
}
