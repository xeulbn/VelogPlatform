package org.example.velogplatform.controller;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.velogplatform.dto.UserLoginResponseDto;
import org.example.velogplatform.jwt.util.JwtTokenizer;
import org.example.velogplatform.model.RefreshToken;
import org.example.velogplatform.model.Role;
import org.example.velogplatform.model.User;
import org.example.velogplatform.security.dto.UserLoginDto;
import org.example.velogplatform.service.RefreshTokenService;
import org.example.velogplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;
    private final RefreshTokenService refreshTokenService;

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

        System.out.println(user.getUsername());

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
        return "redirect:/api"; // 로그아웃 후 API 페이지로 리다이렉트
    }

    @GetMapping("/loginform")
    public String login(){
        return "user/loginform";
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserLoginDto userLoginDto,
                                BindingResult bindingResult, HttpServletResponse response) {
        if(bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        User user = userService.findByUserName(userLoginDto.getUsername());
        if(!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            return new ResponseEntity("비밀번호가 올바르지 않습니다.",HttpStatus.UNAUTHORIZED);

        }

        List<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());

        //토큰 발급
        String accessToken = jwtTokenizer.createAccessToken(
                user.getId(),user.getEmail(),user.getName(),user.getUsername(),roles
        );
        String refreshToken = jwtTokenizer.createRefreshToken(
                user.getId(), user.getEmail(), user.getName(), user.getUsername(), roles);

        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setValue(refreshToken);
        refreshTokenEntity.setUserId(user.getId());

        refreshTokenService.addRefreshToken(refreshTokenEntity);


        //응답으로 보낼 값들을 준비해요.
        UserLoginResponseDto loginResponseDto = UserLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .name(user.getName())
                .build();

        Cookie accessTokenCookie = new Cookie("accessToken",accessToken);
        accessTokenCookie.setHttpOnly(true);  //보안 (쿠키값을 자바스크립트같은곳에서는 접근할수 없어요.)
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(Math.toIntExact(JwtTokenizer.ACCESS_TOKEN_EXPIRE_COUNT/1000)); //30분 쿠키의 유지시간 단위는 초 ,  JWT의 시간단위는 밀리세컨드

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(Math.toIntExact(JwtTokenizer.REFRESH_TOKEN_EXPIRE_COUNT/1000)); //7일

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        return new ResponseEntity(loginResponseDto, HttpStatus.OK);

    }

    @GetMapping("/api/authtest")
    public String authTest(){
        return "authTest";
    }

    @PostMapping("/refreshToken")
    public ResponseEntity refreshToken(HttpServletRequest request, HttpServletResponse response){
        //할일!!
        //1. 쿠키로부터 리프레시토큰을 얻어온다.
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if("refreshToken".equals(cookie.getName())){
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }
        //2-1. 없다.  (오류로  응담.
        if(refreshToken == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        //2-2. 있을때.
        //3. 토큰으로부터 정보를얻어와요.
        Claims claims = jwtTokenizer.parseRefreshToken(refreshToken);
        Long userId = Long.valueOf ((Integer)claims.get("userId"));

        User user = userService.getUser(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾지 못했습니다."));

        //4. accessToken 생성
        List roles = (List)claims.get("roles");


        String accessToken = jwtTokenizer.createAccessToken(userId, user.getEmail(),
                user.getName(), user.getUsername(), roles);

        //5. 쿠키 생성 response로 보내고.
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(Math.toIntExact( JwtTokenizer.ACCESS_TOKEN_EXPIRE_COUNT / 1000));

        response.addCookie(accessTokenCookie);

        // 6. 적절한 응답결과(ResponseEntity)를 생성해서 응답.
        UserLoginResponseDto responseDto = UserLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .name(user.getName())
                .userId(user.getId())
                .build();


        return new ResponseEntity(responseDto, HttpStatus.OK);
    }





}
