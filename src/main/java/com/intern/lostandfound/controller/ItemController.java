package com.intern.lostandfound.controller;

import org.springframework.web.bind.annotation.*;

import com.intern.lostandfound.dto.ItemRequest;
import com.intern.lostandfound.service.ItemService;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public String addItem(@RequestBody ItemRequest request) {
        return itemService.addItem(request);
    }
}