package com.example.commerce.member.controller;

import com.example.commerce.common.token.JwtTokenProvider;
import com.example.commerce.member.domain.Member;
import com.example.commerce.member.dtos.MemberCreateDto;
import com.example.commerce.member.dtos.MemberListDto;
import com.example.commerce.member.dtos.MemberUpdatePasswordDto;
import com.example.commerce.member.dtos.MemberloginDto;
import com.example.commerce.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Component
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
    public ResponseEntity<?>create(@RequestBody MemberCreateDto dto){
        memberService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("OK");

    }
    @PostMapping("/login")
    public String login(@RequestBody MemberloginDto dto){
        Member member = memberService.login(dto);
        String token = jwtTokenProvider.createToken(member);
        return token;
    }
    @PostMapping("/update/password")
    public void updatepassword(@RequestBody MemberUpdatePasswordDto dto){
    memberService.updatepassword(dto);

    }
    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public List<MemberListDto> findAll(){

    }

}
