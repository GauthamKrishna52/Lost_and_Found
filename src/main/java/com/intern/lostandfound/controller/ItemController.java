package com.intern.lostandfound.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.intern.lostandfound.dto.ItemRequest;
import com.intern.lostandfound.service.ItemService;
import java.util.List;

import com.intern.lostandfound.dto.ItemResponse;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object addItem(@ModelAttribute ItemRequest request) {
        return itemService.addItem(request);
    }
    @GetMapping
    public List<ItemResponse> getAllItems() {
    return itemService.getAllItems();
    }
    @GetMapping("/{id}")
    public ItemResponse getItemById(@PathVariable Long id) {
    return itemService.getItemById(id);
    }
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getItemImage(@PathVariable Long id) {
    return itemService.getItemImage(id);
    }
}