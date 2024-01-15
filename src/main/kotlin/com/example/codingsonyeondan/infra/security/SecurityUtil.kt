package com.example.codingsonyeondan.infra.security

import com.example.codingsonyeondan.infra.exception.NotHaveAuthorityException
import com.example.codingsonyeondan.domain.member.model.Member
import com.example.codingsonyeondan.domain.member.repository.MemberRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

class SecurityUtil {
    companion object{
        private fun getUsername(): String
        {
            return (SecurityContextHolder.getContext()?.authentication?.principal as? UserDetails)?.username ?: "anonymous"
        }
        fun getLoginMember(memberRepository: MemberRepository): Member
        {
            return memberRepository.findByLoginId(getUsername()) ?:
            Member(-1, "anonymous", "anonymous", "anonymous", "anonymous");
        }
        fun checkUserCanAccessThis(member: Member, name: String)
        {
            if(member.loginId != getUsername())
                throw NotHaveAuthorityException(name)
        }
    }
}