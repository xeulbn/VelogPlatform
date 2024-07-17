package org.example.velogplatform.config;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.velogplatform.security.CustomUserDetailsService;
import org.example.velogplatform.security.jwt.exception.CustomAuthenticationEntryPoint;
import org.example.velogplatform.security.jwt.filter.JwtAuthenticationFilter;
import org.example.velogplatform.security.jwt.util.JwtTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/userregform","/userreg","/login","/refreshToken","/loginform").permitAll()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenizer), UsernamePasswordAuthenticationFilter.class)
//                .formLogin(Customizer.withDefaults())
                .formLogin(form -> form.disable())
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .cors(cors -> cors.configurationSource(configurationSource()))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint));


        return http.build();
    }


    public CorsConfigurationSource configurationSource(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowedMethods(List.of("GET","POST","DELETE"));
        source.registerCorsConfiguration("/**",config);
        return source;
    }

    @Bean  //해당 메서드의 리턴되는 오브젝트를 Ioc로 등록
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
