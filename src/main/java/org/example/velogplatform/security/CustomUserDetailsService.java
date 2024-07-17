package org.example.velogplatform.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.velogplatform.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.example.velogplatform.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//시큐리티 설정에서 loginProcessingUrl("/login");
//login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는
// loadUserByUsername 함수가 실행

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("유저네임 확인중이다알아너라: {}", username);
        User user= userRepository.findByUsername(username);

        if(user==null){
            throw new UsernameNotFoundException(username);
        }

        log.info("유저 찾았다라라라ㅏ{}",user);

        UserBuilder userBuilder= org.springframework.security.core.userdetails.User.withUsername(user.getUsername());
        userBuilder.password(user.getPassword());
        userBuilder.roles(user.getRoles().stream().map(role ->
                role.getName()).toArray(String[]::new));

        log.info("유저 딩테일스 생성 완료 {}",username);

        return userBuilder.build();
    }



}
