package com.example.codingsonyeondan.domain.member.controller

import com.example.codingsonyeondan.domain.member.dto.MemberDTO
import com.example.codingsonyeondan.domain.member.dto.MemberRegisterDTO
import com.example.codingsonyeondan.domain.member.dto.MemberSignInDTO
import com.example.codingsonyeondan.domain.member.dto.MemberUpdateDTO
import com.example.codingsonyeondan.domain.member.service.MemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "Member", description = "회원 API")
@RequestMapping("/member")
class MemberController(private val memberService: MemberService) {

    @Operation(summary = "회원 정보 조회", description = "로그인 ID를 통해 회원 정보를 조회합니다.")
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{loginId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getMember(@Parameter(description = "로그인 ID") @PathVariable loginId: String): ResponseEntity<MemberDTO>
    {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getMember(loginId))
    }

    @Operation(summary = "회원 정보 수정", description = "로그인 ID와 수정할 회원 정보를 통해 회원 정보를 수정합니다.")
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{loginId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateMember(@Parameter(description = "로그인 ID") @PathVariable loginId: String,
                     @Parameter(description = "회원 정보 업데이트 데이터") @RequestBody memberUpdateDTO: MemberUpdateDTO)
            : ResponseEntity<MemberDTO>
    {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.modifyMember(loginId, memberUpdateDTO))
    }

    @Operation(summary = "회원 정보 삭제", description = "로그인 ID를 통해 회원 정보를 삭제합니다.")
    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/{loginId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteMember(@Parameter(description = "로그인 ID") @PathVariable loginId: String): ResponseEntity<Unit>
    {
        println("Hello!")
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(memberService.deleteMember(loginId))
    }

    @Operation(summary = "회원 가입", description = "회원 가입 정보를 통해 새로운 회원을 등록합니다.")
    @PostMapping("/sign-up", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun registerMember(@Parameter(description = "회원 가입 정보") @RequestBody memberRegisterDTO: MemberRegisterDTO): ResponseEntity<MemberDTO>
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.registerMember(memberRegisterDTO))
    }

    @Operation(summary = "회원 로그인", description = "회원 로그인 정보를 통해 로그인을 진행합니다.")
    @PostMapping("/sign-in", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun loginMember(@Parameter(description = "회원 로그인 정보") @RequestBody memberSignInDTO: MemberSignInDTO) =
        ResponseEntity.status(HttpStatus.OK).body(memberService.signIn(memberSignInDTO))
}