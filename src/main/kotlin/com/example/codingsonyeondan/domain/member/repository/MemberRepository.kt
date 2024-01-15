package com.example.codingsonyeondan.domain.member.repository

import com.example.codingsonyeondan.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long>{

    fun findByLoginId(username: String): Member?
}