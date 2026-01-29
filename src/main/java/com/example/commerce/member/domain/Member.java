package com.example.commerce.member.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String name;
    private LocalDateTime createtime = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;

    public void updatePassword(String password) {
        this.password = password;
    }
}
