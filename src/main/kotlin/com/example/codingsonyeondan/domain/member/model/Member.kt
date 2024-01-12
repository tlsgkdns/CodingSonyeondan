package com.example.codingsonyeondan.domain.member.model

import com.example.codingsonyeondan.domain.member.dto.MemberRegisterDTO
import com.example.codingsonyeondan.domain.member.dto.MemberUpdateDTO
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.Email
import org.springframework.security.crypto.password.PasswordEncoder

@Entity
@Table(name = "member")
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "loginid")
    var loginId: String,
    @Column(name = "password")
    var password: String,
    @Column(name = "email")
    var email: String,
    @Column(name = "nickname")
    var nickname: String,
    @Enumerated(EnumType.STRING)
    val type: MemberType = MemberType.USER
){
    fun updateMember(request: MemberUpdateDTO, encoder: PasswordEncoder)
    {
        this.loginId = request.newLoginId ?: this.loginId
        this.password = request.newPassword
            ?.takeIf { it.isNotBlank() }
            ?.let{encoder.encode(it)}
            ?:this.password
        this.email = request.newEmail ?: this.email
        this.nickname = request.newNickname ?: this.nickname
    }
    companion object{
        fun from(memberRegisterDTO: MemberRegisterDTO, encoder: PasswordEncoder) = Member(
            loginId = memberRegisterDTO.loginId,
            password = encoder.encode(memberRegisterDTO.password),
            email = memberRegisterDTO.email,
            nickname = memberRegisterDTO.nickname
        )
    }
}
