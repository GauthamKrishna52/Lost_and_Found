package com.intern.lostandfound.service;

import org.springframework.stereotype.Service;
import java.io.IOException;
import com.intern.lostandfound.dto.ItemRequest;
import com.intern.lostandfound.model.Item;
import com.intern.lostandfound.repo.ItemRepo;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.intern.lostandfound.dto.ItemResponse;

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
    public String updateItem(Long id, ItemRequest request) {

    Item item = itemRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Item not found"));

    item.setItemName(request.getItemName());
    item.setDescription(request.getDescription());
    item.setCategory(request.getCategory());
    item.setLocation(request.getLocation());
    item.setLostDate(request.getLostDate());
    item.setStatus(request.getStatus());

    try {
        if (request.getImage() != null && !request.getImage().isEmpty()) {
            item.setImage(request.getImage().getBytes());
        }
    } catch (Exception e) {
        throw new RuntimeException("Error updating image", e);
    }

    itemRepo.save(item);

    return "Item Updated Successfully";
    }
    
    public String deleteItem(Long id) {
    Item item = itemRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Item not found"));

    itemRepo.delete(item);

    return "Item Deleted Successfully";
    }
}