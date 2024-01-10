package com.example.codingsonyeondan

import com.example.codingsonyeondan.domain.album.service.AlbumService
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CodingSonyeondanApplicationTests(
 private val albumService: AlbumService
) {

    @Test
    fun contextLoads() {
    }



}
