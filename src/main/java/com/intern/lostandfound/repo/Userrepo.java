package com.intern.lostandfound.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.intern.lostandfound.model.User;

public interface Userrepo extends JpaRepository<User, Integer> {

    User findByEmail(String email);
}
