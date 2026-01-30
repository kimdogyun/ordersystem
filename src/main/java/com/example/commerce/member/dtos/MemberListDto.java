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
public class MemberListDto {
    private Long id;
    private String name;
    private String email;

    public static MemberListDto fromEntity(Member member) {
        return MemberListDto.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }

}
