package com.intern.lostandfound.service;

import org.springframework.stereotype.Service;

import com.intern.lostandfound.dto.ItemRequest;
import com.intern.lostandfound.model.Item;
import com.intern.lostandfound.repo.ItemRepo;

@Service
public class ItemService {

    private final ItemRepo itemRepo;

    public ItemService(ItemRepo itemRepo) {
        this.itemRepo = itemRepo;
    }

    public String addItem(ItemRequest request) {

        Item item = new Item();

        item.setItemName(request.getItemName());
        item.setDescription(request.getDescription());
        item.setCategory(request.getCategory());
        item.setLocation(request.getLocation());
        item.setLostDate(request.getLostDate());
        item.setStatus(request.getStatus());
        item.setImageUrl(request.getImageUrl());

        itemRepo.save(item);

        return "Item Added Successfully";
    }
}