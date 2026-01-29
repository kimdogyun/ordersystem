package com.example.commerce.common.token;

import com.example.commerce.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

public class JwtTokenProvider {
    @Value("${jwt.secretKey}")
    private String st_secret_key;
    @Value("${jwt.expiration}")
    private int expiration;
    private Key secret_key;
    @PostConstruct
    public void init (){

        secret_key = new SecretKeySpec(Base64.getDecoder().decode(st_secret_key), SignatureAlgorithm.HS512.getJcaName());
    }
    public String createToken(Member member){
        // sub : abc@naver.com 형태
        Claims claims = Jwts.claims().setSubject(member.getEmail());
//        주된 키값을 제외한 나머지 정보는 put 사용하여 key:value 세팅
        claims.put("role", member.getRole().toString());
//        ex) claims.put("age" , author.getAge()); 형태 가능

        Date now = new Date();
//        토큰의 구성요소 : 헤더, 페이로드, 시그니처(서명부)
        String token = Jwts.builder()
//                아래 3가지 요소는 페이로드
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiration*60*1000L)) //30분 * 60초 * 1000밀리초 : 밀리초형태로 변환
//                secret키를 통해 서명값(signature) 생성
                .signWith(secret_key)
                .compact();
        return token;
    }
}