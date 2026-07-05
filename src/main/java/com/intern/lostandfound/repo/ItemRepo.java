package com.intern.lostandfound.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intern.lostandfound.entity.Item;

public interface ItemRepo extends JpaRepository<Item, Long> {

}