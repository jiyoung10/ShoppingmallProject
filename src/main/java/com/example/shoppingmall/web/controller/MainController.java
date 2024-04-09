package com.example.shoppingmall.web.controller;

import com.example.shoppingmall.domain.entity.Image;
import com.example.shoppingmall.service.ImageService;
import com.example.shoppingmall.service.ItemService;
import com.example.shoppingmall.web.controller.response.ItemLogResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {

    private final ItemService itemService;

    @GetMapping("/")
    public String main(Model model){
        log.info("MAIN CONTROLLER ACCESS SUCCESS");
        List<ItemLogResponse> itemList = itemService.searchItemList();
        model.addAttribute("products", itemList);
        return "main";
    }

    @GetMapping("/admin")
    public String admin_main(Model model){
        log.info("MAIN ADMIN CONTROLLER ACCESS SUCCESS");
        List<ItemLogResponse> itemList = itemService.searchItemList();
        model.addAttribute("products", itemList);
        return "auth/admin/main";
    }
}
