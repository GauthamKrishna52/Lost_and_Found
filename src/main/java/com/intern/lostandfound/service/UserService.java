package com.intern.lostandfound.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.intern.lostandfound.model.User;
import org.springframework.stereotype.Service;
import com.intern.lostandfound.repo.Userrepo;

@Service
public class UserService {

    @Autowired
    private Userrepo userRepo;

    public User registerUser(User user) {

       BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12); 
       user.setPassword(passwordEncoder.encode(user.getPassword()));
       return userRepo.save(user);
    }

}
