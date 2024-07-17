//package org.example.velogplatform.controller;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.when;
//
//
//import org.example.velogplatform.jwt.util.JwtTokenizer;
//import org.example.velogplatform.model.RefreshToken;
//import org.example.velogplatform.model.Role;
//import org.example.velogplatform.model.User;
//import org.example.velogplatform.service.RefreshTokenService;
//import org.example.velogplatform.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.Collections;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @Mock
//    private JwtTokenizer jwtTokenizer;
//
//    @Mock
//    private RefreshTokenService refreshTokenService;
//
//    @InjectMocks
//    private UserController authController;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
//    }
//
//    @Test
//    public void testLoginSuccess() throws Exception {
//        UserLoginDto userLoginDto = new UserLoginDto();
//        userLoginDto.setUsername("xeulbn");
//        userLoginDto.setPassword("0215");
//
//        User user = new User();
//        user.setId(1L);
//        user.setEmail("xeulbn@liontest.net");
//        user.setPassword("$2a$10$Ba5u2hi0ZH/G81CaAqAzaOAXjfXGM0KiMnCxi9GZ5p3ZOx2jeOS8O");
//        user.setName("강슬빈");
//        user.setUsername("xeulbn");
//        Role role = new Role();
//        role.setName("ROLE_ADMIN");
//        user.setRoles(Collections.singleton(role));
//
//        System.out.println(userLoginDto.getPassword());
//
//        when(userService.findByUserName(userLoginDto.getUsername())).thenReturn(user);
//        when(passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())).thenReturn(true);
//        when(jwtTokenizer.createAccessToken(eq(user.getId()), eq(user.getEmail()), eq(user.getName()), eq(user.getUsername()), any())).thenReturn("accessToken");
//        when(jwtTokenizer.createRefreshToken(eq(user.getId()), eq(user.getEmail()), eq(user.getName()), eq(user.getUsername()), any())).thenReturn("refreshToken");
//
//        RefreshToken refreshTokenEntity = new RefreshToken();
//        refreshTokenEntity.setValue("refreshToken");
//        refreshTokenEntity.setUserId(user.getId());
//        when(refreshTokenService.addRefreshToken(any())).thenReturn(refreshTokenEntity);
//
//        mockMvc.perform(post("/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"email\":\"xeulbn@liontest.net\", \"password\":\"0215\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.accessToken").value("accessToken"))
//                .andExpect(jsonPath("$.refreshToken").value("refreshToken"))
//                .andExpect(jsonPath("$.userId").value(user.getId()))
//                .andExpect(jsonPath("$.name").value(user.getName()));
//    }
//
//    @Test
//    public void testLoginFailureInvalidPassword() throws Exception {
//        UserLoginDto userLoginDto = new UserLoginDto();
//        userLoginDto.setUsername("xeulbn");
//        userLoginDto.setPassword("0215");
//
//        User user = new User();
//        user.setId(1L);
//        user.setEmail("xeulbn@liontest.net");
//        user.setPassword("$2a$10$Ba5u2hi0ZH/G81CaAqAzaOAXjfXGM0KiMnCxi9GZ5p3ZOx2jeOS8O");
//        user.setName("강슬빈");
//        user.setUsername("xeulbn");
//        Role role = new Role();
//        role.setName("ROLE_ADMIN");
//        user.setRoles(Collections.singleton(role));
//
//        when(userService.findByUserName(userLoginDto.getUsername())).thenReturn(user);
//        when(passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())).thenReturn(false);
//
//        mockMvc.perform(post("/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"email\":\"xeulbn@liontest.net\", \"password\":\"1234\"}"))
//                .andExpect(status().isUnauthorized())
//                .andExpect(jsonPath("$").value("비밀번호가 올바르지 않습니다."));
//    }
//
//    @Test
//    public void testLoginFailureInvalidEmail() throws Exception {
//        UserLoginDto userLoginDto = new UserLoginDto();
//        userLoginDto.setUsername("xeulbn");
//        userLoginDto.setPassword("0215");
//
//        when(userService.findByUserName(userLoginDto.getUsername())).thenReturn(null);
//
//        mockMvc.perform(post("/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"email\":\"xeulbin@liontest.net\", \"password\":\"0215\"}"))
//                .andExpect(status().isUnauthorized());
//    }
//
//    @Test
//    public void testLoginFailureValidationErrors() throws Exception {
//        mockMvc.perform(post("/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"email\":\"\", \"password\":\"\"}"))
//                .andExpect(status().isBadRequest());
//    }
//}
