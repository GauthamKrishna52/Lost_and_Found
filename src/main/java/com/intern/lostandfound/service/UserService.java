package com.intern.lostandfound.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.intern.lostandfound.model.User;
import org.springframework.stereotype.Service;
import com.intern.lostandfound.repo.Userrepo;
import org.springframework.security.core.Authentication;
import com.intern.lostandfound.dto.LoginRequest;

@Service
public class UserService {

    @Autowired
    private Userrepo userRepo;

    @Autowired
    AuthenticationManager authManager;

    public User registerUser(User user) {

       BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12); 
       user.setPassword(passwordEncoder.encode(user.getPassword()));
       return userRepo.save(user);
    }

    public boolean loginUser(LoginRequest user) {
        
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        if (authentication.isAuthenticated()) {
            return true;
        } else {
            return false;
        }
    }

}
