package com.example.commerce.member.controller;

import com.example.commerce.common.token.JwtTokenProvider;
import com.example.commerce.member.domain.Member;
import com.example.commerce.member.dtos.*;
import com.example.commerce.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public MemberController(MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody MemberCreateDto dto) {
        Member member = memberService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(member.getId());

    }

    @PostMapping("/login")
    public MemberTokenDto login(@RequestBody MemberloginDto dto) {
        Member member = memberService.login(dto);
        String accestoken = jwtTokenProvider.createToken(member);
        MemberTokenDto dtoToken = MemberTokenDto.builder()
                .accesToken(accestoken)
                .build();
        return dtoToken;
    }

    @PostMapping("/update/password")
    public void updatepassword(@RequestBody MemberUpdatePasswordDto dto) {
        memberService.updatepassword(dto);

    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public List<MemberListDto> findAll() {
        List<MemberListDto> dtoList = memberService.findAll();
        return dtoList;
    }

    @GetMapping("/myinfo")
    public ResponseEntity<?> myinfo(@AuthenticationPrincipal String principal) {
        MemberDetailDto dto = memberService.myinfo();
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/{id}")
    public MemberDetailDto findById(@PathVariable Long id) {

        MemberDetailDto dto = memberService.findById(id);
        return dto;


    }

}
