package com.example.commerce.member.service;

import com.example.commerce.member.domain.Member;
import com.example.commerce.member.dtos.*;
import com.example.commerce.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member save(MemberCreateDto dto) {
        if (memberRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이메일 중복임");
        }

        Member member = dto.toEntity(passwordEncoder.encode(dto.getPassword()));
        return memberRepository.save(member);
    }

    public Member login(MemberloginDto dto) {
        Optional<Member> optionalMember = memberRepository.findByEmail(dto.getEmail());
        boolean check = true;
        if (!optionalMember.isPresent()) {
            check = false;
        }if (!passwordEncoder.matches(dto.getPassword(),optionalMember.get().getPassword())){
            check = false;
        }if (!check){
            throw new IllegalArgumentException("이메일 또는 비밀번호 중복임.");
        }
        return optionalMember.get();
    }
    public void updatepassword(MemberUpdatePasswordDto dto){
    Optional<Member>optionalMember=memberRepository.findByEmail(dto.getEmail());
    Member member = optionalMember.orElseThrow(()->new EntityNotFoundException("X"));
    member.updatePassword(dto.getPassword());

    }
    public List<MemberListDto> findAll(){
        return memberRepository.findAll().stream().map(m->MemberListDto.fromEntity(m)).collect((Collectors.toList()));

    }
    public MemberDetailDto myinfo(){
    String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    Optional<Member>optionalMember = memberRepository.findByEmail(email);
    Member member = optionalMember.orElseThrow(()->new NoSuchElementException("X"));
    MemberDetailDto dto = MemberDetailDto.fromEntity(member);
    return dto;

    }
    public MemberDetailDto findById(Long id){
        Optional<Member>optionalMember = memberRepository.findById(id);
        Member member = optionalMember.orElseThrow(()-> new EntityNotFoundException("X"));
        MemberDetailDto dto = MemberDetailDto.fromEntity(member);
        return dto;
    }
}