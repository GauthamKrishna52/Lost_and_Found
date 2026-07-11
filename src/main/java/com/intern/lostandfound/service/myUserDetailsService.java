package com.intern.lostandfound.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.intern.lostandfound.model.User;
import com.intern.lostandfound.repo.Userrepo;
import com.intern.lostandfound.model.UserPrincipal;


@Service
public class myUserDetailsService implements UserDetailsService {

    @Autowired
    private Userrepo Userrepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = Userrepo.findByEmail(username);
        

        if(user == null) {
        throw new UsernameNotFoundException("User not found with username: " + username);
    }

    return new UserPrincipal(user);
        
}


    

}
