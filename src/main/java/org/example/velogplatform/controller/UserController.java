package org.example.velogplatform.controller;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
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

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenizer jwtTokenizer;

    @GetMapping("/")
    public String main(){
        return "blog/main";
    }

    @GetMapping("/userregform")
    public String register(){
        return "user/register";
    }

    @PostMapping("/userreg")
    public String registerUser(@ModelAttribute("user") User user, BindingResult result) {
        if(result.hasErrors()){
            return "user/register";
        }
        User byUserName = userService.findByUserName(user.getUsername());

        if(byUserName != null){
            result.rejectValue("username", null,"이미 사용중인 아이디입니다.");
        }


        userService.registUser(user);
        return "redirect:/";
    }


    @GetMapping("/api/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("accessToken", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0); // 쿠키 삭제

        response.addCookie(cookie);
        return "redirect:/"; // 로그아웃 후 API 페이지로 리다이렉트
    }

    @GetMapping("/loginform")
    public String login(){
        log.info("로그인 한다구룡렁러ㅏ널아러ㅏ널ㄴ");
        return "user/loginform";
    }






}
