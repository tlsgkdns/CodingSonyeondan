package com.example.codingsonyeondan.domain.member.dto

data class MemberRegisterDTO (
    val loginId: String,
    val password: String,
    val email: String,
    val nickname: String
)