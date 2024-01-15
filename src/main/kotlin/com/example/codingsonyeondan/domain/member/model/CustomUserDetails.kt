package com.example.codingsonyeondan.domain.member.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails (
    private val loginId: String,
    private val password: String): UserDetails{
    companion object {
        fun from(member: Member): CustomUserDetails {
            return with(member) {
                CustomUserDetails(loginId = loginId,
                    password = password)
            }
        }
    }
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        TODO("Not yet implemented")
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return loginId
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
       return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}