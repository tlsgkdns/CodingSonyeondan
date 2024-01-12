package com.example.codingsonyeondan.domain.member.controller

import com.example.codingsonyeondan.domain.member.dto.MemberDTO
import com.example.codingsonyeondan.domain.member.dto.MemberRegisterDTO
import com.example.codingsonyeondan.domain.member.dto.MemberSignInDTO
import com.example.codingsonyeondan.domain.member.dto.MemberUpdateDTO
import com.example.codingsonyeondan.domain.member.service.MemberService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/member")
class MemberController(private val memberService: MemberService) {

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{loginId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getMember(@PathVariable loginId: String): ResponseEntity<MemberDTO>
    {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getMember(loginId))
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{loginId}", produces = [MediaType.APPLICATION_JSON_VALUE])

    fun updateMember(@PathVariable loginId: String, @RequestBody memberUpdateDTO: MemberUpdateDTO)
    : ResponseEntity<MemberDTO>
    {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.modifyMember(loginId, memberUpdateDTO))
    }
    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/{loginId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteMember(@PathVariable loginId: String): ResponseEntity<Unit>
    {
        println("Hello!")
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(memberService.deleteMember(loginId))
    }

    @PostMapping("/sign-up", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun registerMember(@RequestBody memberRegisterDTO: MemberRegisterDTO): ResponseEntity<MemberDTO>
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.registerMember(memberRegisterDTO))
    }

    @PostMapping("/sign-in", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun loginMember(@RequestBody memberSignInDTO: MemberSignInDTO) =
        ResponseEntity.status(HttpStatus.OK).body(memberService.signIn(memberSignInDTO))
}