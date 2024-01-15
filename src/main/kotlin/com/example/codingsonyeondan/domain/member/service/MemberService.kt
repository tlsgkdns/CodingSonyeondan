package com.example.codingsonyeondan.domain.member.service

import com.example.codingsonyeondan.domain.member.dto.*

interface MemberService {
    fun registerMember(memberRegisterDTO: MemberRegisterDTO): MemberDTO
    fun signIn(memberSignInDTO: MemberSignInDTO): SignInResponse
    fun getMember(loginId: String): MemberDTO
    fun getMember(id: Long): MemberDTO
    fun modifyMember(loginId: String, memberUpdateDTO: MemberUpdateDTO): MemberDTO
    fun deleteMember(loginId: String)
}