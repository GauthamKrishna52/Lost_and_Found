package com.intern.lostandfound.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.intern.lostandfound.dto.ItemRequest;
import com.intern.lostandfound.model.Item;
import com.intern.lostandfound.model.ItemStatus;
import com.intern.lostandfound.model.User;
import com.intern.lostandfound.repo.ItemRepo;
import com.intern.lostandfound.repo.Userrepo;

class ItemServiceTest {

    @Mock
    private ItemRepo itemRepo;

    @Mock
    private Userrepo userRepo;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addItemShouldStoreAuthenticatedUserDetails() {
        User currentUser = new User();
        currentUser.setName("Alice");
        currentUser.setEmail("alice@example.com");
        currentUser.setPhoneNumber("1234567890");

        when(userRepo.findByEmail("alice@example.com")).thenReturn(currentUser);
        when(itemRepo.save(any(Item.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ItemRequest request = new ItemRequest();
        request.setItemName("Wallet");
        request.setDescription("Black wallet");
        request.setCategory("Personal");
        request.setLocation("Library");
        request.setLostDate(LocalDate.now());
        request.setStatus(ItemStatus.LOST);

        Principal principal = () -> "alice@example.com";

        String result = itemService.addItem(request, principal);

        assertEquals("Item Added Successfully", result);

        ArgumentCaptor<Item> itemCaptor = ArgumentCaptor.forClass(Item.class);
        verify(itemRepo).save(itemCaptor.capture());

        Item savedItem = itemCaptor.getValue();
        assertEquals("alice@example.com", savedItem.getPostedByUsername());
        assertEquals("Alice", savedItem.getPostedByName());
        assertEquals("alice@example.com", savedItem.getPostedByEmail());
        assertEquals("1234567890", savedItem.getPostedByPhoneNumber());
    }
}
