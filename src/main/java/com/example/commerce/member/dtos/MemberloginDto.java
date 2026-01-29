package com.example.commerce.member.dtos;

import com.example.commerce.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberloginDto {
    private String email;
    private String password;

    public MemberloginDto fromEntity(Member member){
        return MemberloginDto.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .build();
    }

}
