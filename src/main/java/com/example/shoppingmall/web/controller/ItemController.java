package com.example.shoppingmall.web.controller;

import com.example.shoppingmall.service.ItemService;
import com.example.shoppingmall.web.controller.request.ItemRegisterRequest;
import com.example.shoppingmall.web.controller.response.ItemLogResponse;
import com.example.shoppingmall.web.dto.ItemDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/item")
@RestController
public class ItemController {

    private final ItemService itemService;

    // 관리자(ADMIN) 권한만 접근 가능
    @PostMapping(value = "/admin/register", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ItemDTO> RegisterItem(@RequestPart(value = "itemRegisterRequest") ItemRegisterRequest itemRegisterRequest,
                                                @RequestPart(value = "file") MultipartFile file) throws Exception {
        ItemDTO registeredItem = itemService.registerItem(itemRegisterRequest, file);

        return new ResponseEntity<>(registeredItem, HttpStatus.OK);
    }

    @PutMapping(value = "/admin/update/{itemId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ItemDTO> RegisterItem(@PathVariable Long itemId,
                                                @RequestPart(value = "itemUpdateRequest") ItemRegisterRequest itemRegisterRequest,
                                                @RequestPart(value = "file") MultipartFile file) throws Exception {
        ItemDTO updatedItem = itemService.updateItem(itemId, itemRegisterRequest, file);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    @DeleteMapping("/admin/delete/{itemId}")
    public ResponseEntity<Void> RegisterItem(@PathVariable Long itemId) throws Exception {
        itemService.deleteItem(itemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 모든 사용자 접근 가능
    @GetMapping("/shop/search")
    public ResponseEntity<List<ItemLogResponse>> ItemList() {
        List<ItemLogResponse> itemList = itemService.searchItemList();
        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }

    @GetMapping("/shop/search/{tag}")
    public ResponseEntity<List<ItemLogResponse>> SearchItem(@PathVariable String tag) throws Exception {
        List<ItemLogResponse> itemList = itemService.searchItemByTag(tag);
        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }
}
