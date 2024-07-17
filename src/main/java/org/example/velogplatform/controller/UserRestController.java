package org.example.velogplatform.controller;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.velogplatform.dto.RefreshTokenDto;
import org.example.velogplatform.dto.UserLoginDto;
import org.example.velogplatform.dto.UserLoginResponseDto;
import org.example.velogplatform.model.RefreshToken;
import org.example.velogplatform.model.Role;
import org.example.velogplatform.model.User;
import org.example.velogplatform.security.jwt.util.JwtTokenizer;
import org.example.velogplatform.service.RefreshTokenService;
import org.example.velogplatform.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserRestController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenizer jwtTokenizer;


    @PostMapping("/login")
    public void loginUser(@RequestParam("username") String username, @RequestParam("password") String password,
                            Model model, HttpServletResponse response) throws IOException {

        log.info("login시작");

//        if(result.hasErrors()){
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        }

        User user= userService.findByUserName(username);
//        if(!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())){
//            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
//        }

        if(user == null) {
            model.addAttribute("error","없는 아이디입니다");
        }

        List<String> roles= user.getRoles().stream()
                .map(Role::getName).collect(Collectors.toList());

        String accessToken = jwtTokenizer.createAccessToken(user.getId(),user.getEmail(),user.getUsername(),roles);
        String refreshToken = jwtTokenizer.createRefreshToken(user.getId(),user.getEmail(),user.getUsername(),roles);

        log.info("access token 뭐냐고? {}", accessToken);

        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setValue(refreshToken);
        refreshTokenEntity.setUserId(user.getId());
        refreshTokenService.addRefreshToken(refreshTokenEntity);

        log.info("refresh token 뭐냐고? {}", refreshToken);

        UserLoginResponseDto loginResponse = UserLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .name(user.getUsername())
                .build();

        response.sendRedirect("/");
    }

    @DeleteMapping("/logout")
    public ResponseEntity logout(@RequestBody RefreshTokenDto refreshTokenDto) {
        refreshTokenService.deleteRefreshToken(refreshTokenDto.getRefreshToken());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity requestRefresh(@RequestBody RefreshTokenDto refreshTokenDto) {
        RefreshToken refreshToken = refreshTokenService.findRefreshToken(refreshTokenDto.getRefreshToken()).orElseThrow(() -> new IllegalArgumentException("Refresh token not found"));
        Claims claims = jwtTokenizer.parseRefreshToken(refreshToken.getValue());

        Long userId = Long.valueOf((Integer)claims.get("userId"));
        String username = claims.get("username").toString();
        User user = userService.getUser(userId).orElseThrow(() -> new IllegalArgumentException("Member not found"));


        List roles = (List) claims.get("roles");
        String email = claims.getSubject();

        String accessToken = jwtTokenizer.createAccessToken(userId, username, email, roles);

        UserLoginResponseDto loginResponse = UserLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshTokenDto.getRefreshToken())
                .userId(user.getId())
                .name(user.getName())
                .build();
        return new ResponseEntity(loginResponse, HttpStatus.OK);
    }



}
