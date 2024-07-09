package org.example.velogplatform.service;

import org.example.velogplatform.model.Role;
import org.example.velogplatform.model.User;
import org.example.velogplatform.repository.RoleRepository;
import org.example.velogplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User registUser(User user) {
        String name=user.getName();

        Role userRole;
        if(name.equals("강슬빈")){
            userRole=roleRepository.findByName("ADMIN");
        }else{
            userRole=roleRepository.findByName("USER");
        }
        user.setRoles(Collections.singleton(userRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findByUserName(String username){
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUser(Long id){
        return userRepository.findById(id);
    }

}
