package com.example.codingsonyeondan.domain.member.service

import com.example.codingsonyeondan.domain.member.dto.*
import com.example.codingsonyeondan.domain.member.model.Member
import com.example.codingsonyeondan.domain.member.repository.MemberRepository
import com.example.codingsonyeondan.infra.exception.ModelNotFoundException
import com.example.codingsonyeondan.infra.security.SecurityUtil
import com.example.codingsonyeondan.infra.security.TokenProvider
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.IllegalStateException

@Service
class MemberServiceImpl (
    private val memberRepository: MemberRepository,
    private val encoder: PasswordEncoder,
    private val tokenProvider: TokenProvider
): MemberService {
    @Transactional
    override fun registerMember(memberRegisterDTO: MemberRegisterDTO): MemberDTO {
        if(memberRepository.findByLoginId(memberRegisterDTO.loginId) != null) throw IllegalStateException("이미 등록된 ID입니다.")
        return MemberDTO.from(memberRepository.save(Member.from(memberRegisterDTO, encoder)))
    }

    @Transactional
    override fun signIn(memberSignInDTO: MemberSignInDTO): SignInResponse {
        val member = memberRepository.findByLoginId(memberSignInDTO.username)
            ?.takeIf { encoder.matches(memberSignInDTO.password, it.password) }
            ?:throw IllegalStateException("아이디 또는 비밀번호가 일치하지 않습니다.")
        val token = tokenProvider.createToken("${member.loginId}:${member.type}")
        return SignInResponse(member.loginId, member.type, token)
    }

    @Transactional
    override fun getMember(loginId: String): MemberDTO {
        val member = memberRepository.findByLoginId(loginId) ?: throw ModelNotFoundException("member", loginId)
        return MemberDTO.from(member)
    }

    @Transactional
    override fun getMember(id: Long): MemberDTO {
        val member = memberRepository.findByIdOrNull(id) ?: throw ModelNotFoundException("member", id.toString())
        return MemberDTO.from(member)
    }

    @Transactional
    override fun modifyMember(loginId: String, memberUpdateDTO: MemberUpdateDTO): MemberDTO {
        val member = memberRepository.findByLoginId(loginId) ?: throw ModelNotFoundException("member", loginId)
        if(memberRepository.findByLoginId(memberUpdateDTO.newLoginId ?: "") != null)
            throw IllegalStateException("이미 존재하는 아이디입니다.")
        SecurityUtil.checkUserCanAccessThis(member, "Member")
        member.updateMember(
            MemberUpdateDTO(memberUpdateDTO.newLoginId,
            memberUpdateDTO.newPassword, memberUpdateDTO.newEmail, memberUpdateDTO.newNickname), encoder)
        memberRepository.save(member)
        return MemberDTO.from(member)
    }

    @Transactional
    override fun deleteMember(loginId: String) {
        val member = memberRepository.findByLoginId(loginId) ?: throw ModelNotFoundException("member", loginId)
        SecurityUtil.checkUserCanAccessThis(member, "Member")
        memberRepository.delete(member)
    }
}