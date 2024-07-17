//package org.example.velogplatform.jwt.util;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwt;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import org.example.velogplatform.security.jwt.util.JwtTokenizer;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.nio.charset.StandardCharsets;
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
//class JwtTokenizerTest {
//
//    @Autowired
//    JwtTokenizer jwtTokenizer;
//
//    @Value("${spring.jwt.secret}")
//    String accessSecret;
//
//    private final Long ACCESS_TOKEN_EXPIRE_COUNT=60*60*1000L;
//
//    @Test
//    void createToken() throws Exception {
//        String email= "xeulbn@liontest.net";
//        String username= "xeulbn";
//        String name= "강슬빈";
//        List<String> roles = List.of("ROLE_ADMIN");
//        Long id= 1L;
//        Claims claims = Jwts.claims().setSubject(email);
//
//        claims.put("username", username);
//        claims.put("name",name);
//        claims.put("userId",id);
//        claims.put("roles", roles);
//
//        byte[] accessSecret = this.accessSecret.getBytes(StandardCharsets.UTF_8);
//
//        String JwtToken = Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(new Date().getTime()+this.ACCESS_TOKEN_EXPIRE_COUNT))
//                .signWith(Keys.hmacShaKeyFor(accessSecret))
//                .compact();
//
//        System.out.println(JwtToken);
//    }
//
//
//    @Test
//    public void parseToken() throws Exception{
//        byte[]accessSecret = this.accessSecret.getBytes(StandardCharsets.UTF_8);
//        String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4ZXVsYm5AbGlvbnRlc3QubmV0IiwidXNlcm5hbWUiOiJ4ZXVsYm4iLCJuYW1lIjoi6rCV7Iqs67mIIiwidXNlcklkIjoxLCJyb2xlcyI6WyJST0xFX0FETUlOIl0sImlhdCI6MTcyMDY4MTk1MiwiZXhwIjoxNzIwNjg1NTUyfQ.NzB2E4auYpzUYCbewGAVKSQQhOa7EBRUsxE7knKwPU8";
//
//
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey(Keys.hmacShaKeyFor(accessSecret))
//                .build()
//                .parseClaimsJws(jwtToken)
//                .getBody();
//
//        System.out.println(claims.getSubject());
//        System.out.println(claims.get("roles"));
//        System.out.println(claims.get("userId"));
//        System.out.println(claims.get("username"));
//        System.out.println(claims.getIssuedAt());
//        System.out.println(claims.getExpiration());
//    }
//
//}