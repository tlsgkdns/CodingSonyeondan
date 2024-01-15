package com.example.codingsonyeondan.domain.member.dto

import com.example.codingsonyeondan.domain.member.model.MemberType

data class SignInResponse(val name: String?, val type: MemberType, val token: String)