package com.example.codingsonyeondan.domain.member.dto

import com.example.codingsonyeondan.domain.member.model.Member

data class MemberDTO(
    val id: Long,
    val loginId: String,
    val password: String,
    val nickname: String,
    val email: String
)
{
    companion object{
        fun from(member: Member): MemberDTO
        {
            return MemberDTO(member.id!!, member.loginId,
                member.password, member.nickname, member.email)
        }
    }
}