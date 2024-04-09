package com.example.shoppingmall.web.controller;

import com.example.shoppingmall.domain.entity.User;
import com.example.shoppingmall.service.AuthService;
import com.example.shoppingmall.service.ItemService;
import com.example.shoppingmall.web.controller.request.ItemRegisterRequest;
import com.example.shoppingmall.web.controller.response.ItemLogResponse;
import com.example.shoppingmall.web.dto.ItemDTO;
import com.example.shoppingmall.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ItemController {
    private final ItemService itemService;
    private final AuthService authService;

    // 관리자(ADMIN) 권한만 접근 가능
    @GetMapping(value = "/admin/register")
    public String RegisterItemForm(Authentication authentication) {
        log.info("GET RegisterItemForm : {}", authentication);
        return "auth/admin/itemRegister";
    }

    @PostMapping(value = "/admin/register", consumes = "multipart/form-data")
    public String RegisterItem(Model model, Authentication authentication,
                               @ModelAttribute(value = "itemRegisterRequest") ItemRegisterRequest itemRegisterRequest) throws Exception {
        ItemDTO itemDTO = itemService.registerItem(itemRegisterRequest);
        log.info("POST RegisterItem itemDto : {}", itemDTO.getTitle());

        List<ItemLogResponse> itemList = itemService.searchItemList();
        String Name = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Name Test : {}", Name);
        UserDTO userInfo = (UserDTO) authentication.getPrincipal();
        log.info("itemCont userName: {}", userInfo.getUserName());

        model.addAttribute("userInfo", userInfo);
        model.addAttribute("products", itemList);

        return "/auth/admin/main";
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
    public String ItemList(Model model) {
        List<ItemLogResponse> itemList = itemService.searchItemList();
        model.addAttribute("products", itemList);
        return "main";
    }

    @GetMapping("/shop/search/{tag}")
    public ResponseEntity<List<ItemLogResponse>> SearchItem(@PathVariable String tag) throws Exception {
        List<ItemLogResponse> itemList = itemService.searchItemByTag(tag);
        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }

//    // 관리자(ADMIN) 권한만 접근 가능
//    @PostMapping(value = "/admin/register", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<ItemDTO> RegisterItem(@RequestPart(value = "itemRegisterRequest") ItemRegisterRequest itemRegisterRequest,
//                                                @RequestPart(value = "file") MultipartFile file) throws Exception {
//        ItemDTO registeredItem = itemService.registerItem(itemRegisterRequest, file);
//
//        return new ResponseEntity<>(registeredItem, HttpStatus.OK);
//    }
//
//    @PutMapping(value = "/admin/update/{itemId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<ItemDTO> RegisterItem(@PathVariable Long itemId,
//                                                @RequestPart(value = "itemUpdateRequest") ItemRegisterRequest itemRegisterRequest,
//                                                @RequestPart(value = "file") MultipartFile file) throws Exception {
//        ItemDTO updatedItem = itemService.updateItem(itemId, itemRegisterRequest, file);
//        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/admin/delete/{itemId}")
//    public ResponseEntity<Void> RegisterItem(@PathVariable Long itemId) throws Exception {
//        itemService.deleteItem(itemId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    // 모든 사용자 접근 가능
//    @GetMapping("/shop/search")
//    public ResponseEntity<List<ItemLogResponse>> ItemList(Model model) {
//        List<ItemLogResponse> itemList = itemService.searchItemList();
//        model.addAttribute("products", itemList);
//        return new ResponseEntity<>(itemList, HttpStatus.OK);
//    }
//
//    @GetMapping("/shop/search/{tag}")
//    public ResponseEntity<List<ItemLogResponse>> SearchItem(@PathVariable String tag) throws Exception {
//        List<ItemLogResponse> itemList = itemService.searchItemByTag(tag);
//        return new ResponseEntity<>(itemList, HttpStatus.OK);
//    }
}
