package com.intern.lostandfound.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @Column(unique = true)
    private String email;
    
    private String password;
    private String phoneNumber;
    

}
