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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;


import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;




}
