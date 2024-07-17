//package org.example.velogplatform.service;
//
//import org.example.velogplatform.jwt.util.JwtTokenizer;
//import org.example.velogplatform.model.Role;
//import org.example.velogplatform.model.User;
//import org.example.velogplatform.repository.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Collections;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class UserServiceTest {
//
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @Mock
//    private JwtTokenizer jwtTokenizer;
//
//    @Mock
//    private UserService userService;
//
//
//    @Test
//    public void testFindByEmail() {
//
//        String testEmail = "xeulbn@liontest.net";
//        User mockUser = new User();
//        mockUser.setId(1L);
//        mockUser.setEmail(testEmail);
//        mockUser.setPassword("$2a$10$Ba5u2hi0ZH/G81CaAqAzaOAXjfXGM0KiMnCxi9GZ5p3ZOx2jeOS8O");
//        mockUser.setName("강슬빈");
//        mockUser.setUsername("xeulbn");
//        Role role = new Role();
//        role.setName("ROLE_ADMIN");
//        mockUser.setRoles(Collections.singleton(role));
//
//
//
//        // Test userService.findByEmail method
//        User foundUser = userService.findByEmail(testEmail);
//        assert(foundUser != null);
//        assert(foundUser.getEmail().equals(testEmail));
//    }
//
//    @Test
//    void findByUserName() {
//    }
//}