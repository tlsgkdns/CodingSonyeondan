package com.example.codingsonyeondan.infra.exception

data class InvalidDTOError(val dto: String, val msg: String):
    RuntimeException("$dto has Invalid Value: $msg")
