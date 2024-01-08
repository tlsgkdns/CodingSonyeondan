package com.example.codingsonyeondan.infra.exception.handler

import com.example.codingsonyeondan.infra.exception.ModelNotFoundException
import com.example.codingsonyeondan.infra.exception.UniqueAttributeAlreadyExistException
import com.example.codingsonyeondan.infra.exception.dto.ErrorDTO
import org.springframework.boot.Banner.Mode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ModelNotFoundException::class)
    fun handleModelNotfoundException(e: ModelNotFoundException): ResponseEntity<ErrorDTO>
    {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorDTO(e.message))
    }

    @ExceptionHandler(UniqueAttributeAlreadyExistException::class)
    fun handleUniqueAttributeAlreadyExistException(e: UniqueAttributeAlreadyExistException): ResponseEntity<ErrorDTO>
    {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ErrorDTO(e.message))
    }
}