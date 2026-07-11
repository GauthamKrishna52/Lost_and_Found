package com.intern.lostandfound.service;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.intern.lostandfound.dto.ItemRequest;
import com.intern.lostandfound.dto.ItemResponse;
import com.intern.lostandfound.model.Item;
import com.intern.lostandfound.model.User;
import com.intern.lostandfound.repo.ItemRepo;
import com.intern.lostandfound.repo.Userrepo;

@Service
public class ItemService {

    private final ItemRepo itemRepo;
    private final Userrepo userRepo;

    public ItemService(ItemRepo itemRepo, Userrepo userRepo) {
        this.itemRepo = itemRepo;
        this.userRepo = userRepo;
    }

    public String addItem(ItemRequest request) {
        return addItem(request, null);
    }

    public String addItem(ItemRequest request, Principal principal) {

        Item item = new Item();

        item.setItemName(request.getItemName());
        item.setDescription(request.getDescription());
        item.setCategory(request.getCategory());
        item.setLocation(request.getLocation());
        item.setLostDate(request.getLostDate());
        item.setStatus(request.getStatus());

        String username = null;
        if (principal != null) {
            username = principal.getName();
        }

        if (username != null && !username.isBlank()) {
            item.setPostedByUsername(username);
            User currentUser = userRepo.findByEmail(username);
            if (currentUser != null) {
                item.setPostedByName(currentUser.getName());
                item.setPostedByEmail(currentUser.getEmail());
                item.setPostedByPhoneNumber(currentUser.getPhoneNumber());
            } else {
                item.setPostedByEmail(username);
            }
        }

        try {
        if (request.getImage() != null && !request.getImage().isEmpty()) {
            item.setImage(request.getImage().getBytes());
        }
        } catch (IOException e) {
        throw new RuntimeException("Error while uploading image", e);
        }

        itemRepo.save(item);

        return "Item Added Successfully";
    }

    public List<ItemResponse> getAllItems() {

    List<Item> items = itemRepo.findAll();

    return items.stream().map(item -> {

        ItemResponse response = new ItemResponse();

        response.setId(item.getId());
        response.setItemName(item.getItemName());
        response.setDescription(item.getDescription());
        response.setCategory(item.getCategory());
        response.setLocation(item.getLocation());
        response.setLostDate(item.getLostDate());
        response.setStatus(item.getStatus());
        response.setPostedByUsername(item.getPostedByUsername());
        response.setPostedByName(item.getPostedByName());
        response.setPostedByEmail(item.getPostedByEmail());
        response.setPostedByPhoneNumber(item.getPostedByPhoneNumber());

        response.setImageUrl("/api/items/" + item.getId() + "/image");

        return response;

    }).collect(Collectors.toList());
    }
    
    public ItemResponse getItemById(Long id) {

    Item item = itemRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Item not found"));

    ItemResponse response = new ItemResponse();

    response.setId(item.getId());
    response.setItemName(item.getItemName());
    response.setDescription(item.getDescription());
    response.setCategory(item.getCategory());
    response.setLocation(item.getLocation());
    response.setLostDate(item.getLostDate());
    response.setStatus(item.getStatus());
    response.setPostedByUsername(item.getPostedByUsername());
    response.setPostedByName(item.getPostedByName());
    response.setPostedByEmail(item.getPostedByEmail());
    response.setPostedByPhoneNumber(item.getPostedByPhoneNumber());

    response.setImageUrl("/api/items/" + item.getId() + "/image");

    return response;
    }

    public ResponseEntity<byte[]> getItemImage(Long id) {

    Item item = itemRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Item not found"));

    return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_PNG)
            .body(item.getImage());
}
}