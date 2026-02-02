package com.example.commerce.member.controller;

import com.example.commerce.common.token.JwtTokenProvider;
import com.example.commerce.member.domain.Member;
import com.example.commerce.member.dtos.*;
import com.example.commerce.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
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
        Long id = memberService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);

    }

    @PostMapping("/dologin")
    public ResponseEntity<?> dologin(@RequestBody MemberloginDto dto) {
        Member member = memberService.login(dto);
        String accessToken = jwtTokenProvider.createToken(member);
//        refresh 생성 및 저장
        String refreshToken = jwtTokenProvider.createRtToken(member);
        MemberTokenDto dtoToken = MemberTokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(dtoToken);
    }
    @PostMapping("/refresh-at")
    public ResponseEntity<?> refreshAt(@RequestBody RefreshTokenDto dto){
//        rt 검증(1.토큰 자체 검증 2.redis 조회 검증)
        Member member = jwtTokenProvider.validateRt(dto.getRefreshToken());
//        at신규 생성
        String accessToken = jwtTokenProvider.createToken(member);
//        refresh 생성 및 저장
        MemberTokenDto dtoToken = MemberTokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(null)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(dtoToken);
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
    public ResponseEntity<?> myinfo(@AuthenticationPrincipal String email) {
        MemberDetailDto dto = memberService.myinfo(email);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/detail/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public MemberDetailDto findById(@PathVariable Long id) {
        MemberDetailDto dto = memberService.findById(id);
        return dto;


    }

}
