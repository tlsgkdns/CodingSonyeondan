package com.example.codingsonyeondan.domain.member.dto

data class MemberUpdateDTO (
    val newLoginId: String?,
    val newPassword: String?,
    val newEmail: String?,
    val newNickname: String?
)