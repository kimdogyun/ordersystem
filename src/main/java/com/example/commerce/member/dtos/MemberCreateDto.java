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
public class MemberCreateDto {
    private String email;
    private String name;
    private String password;

    public Member toENtity(String encodedPassword){
        return Member.builder()
                .email(this.email)
                .name(this.name)
                .password(encodedPassword)
                .build();

    }

}
