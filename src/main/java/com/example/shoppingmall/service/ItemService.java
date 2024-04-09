package com.example.shoppingmall.service;

import com.example.shoppingmall.domain.entity.Image;
import com.example.shoppingmall.domain.entity.Item;
import com.example.shoppingmall.domain.entity.Tag;
import com.example.shoppingmall.domain.repository.ItemRepository;
import com.example.shoppingmall.web.controller.request.ItemRegisterRequest;
import com.example.shoppingmall.web.controller.response.ItemLogResponse;
import com.example.shoppingmall.web.dto.ItemDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final TagService tagService;
    private final ImageService imageService;

    @Value("${upload.path}")
    private String uploadPath;

    @Transactional(readOnly = true)
    public List<ItemLogResponse> searchItemByTag(String tag) throws Exception {

        Tag searchTag = tagService.searchItemByTag(tag);

        List<ItemLogResponse> itemList = itemRepository.findById(searchTag.getItem().getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return itemList;
    }

    @Transactional(readOnly = true)
    public List<ItemLogResponse> searchItemList() {

        return itemRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ItemLogResponse mapToResponse(Item item) {
        return ItemLogResponse.EntityToDTO(item);
    }

    @Transactional
    public ItemDTO registerItem(ItemRegisterRequest itemRegisterRequest) throws Exception {
        // 상품명 중복 체크
        if (itemRepository.findByTitle(itemRegisterRequest.getTitle()).isPresent() &&
                !itemRepository.findByTitle(itemRegisterRequest.getTitle()).isEmpty()) {
            throw new Exception("ITEM_ALREADY_EXISTS");
        }
        log.info("registerItem service itemRegisterRequest :{}", itemRegisterRequest);


        Item item = itemRepository.save(Item.toEntity(itemRegisterRequest));
        log.info("registerItem service item :{}", item);
        tagService.registerTag(item, itemRegisterRequest);
        log.info("tag save test");

        //로컬 이미지 저장 test
        if (itemRegisterRequest.getFile() != null) {
            Image image = imageService.registerImage(itemRegisterRequest.getFile(), item);
            log.info("registerItem service image : {}", image.getImageName());
            item.updateImage(image);
        }

        log.info("ItemService - register item, tag : {}", item.getTitle());

        return ItemDTO.fromEntity(item);
    }

    public ItemDTO updateItem(Long itemId, ItemRegisterRequest itemRegisterRequest, MultipartFile file) throws Exception {

        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new Exception("ITEM_DOES_NOT_EXIST"));
        item.updateItem(Item.toEntity(itemRegisterRequest));
        item.updateImage(Image.of(file.getName(), uploadPath, item));
        itemRepository.save(item);

        tagService.updateTag(item, itemRegisterRequest);
        imageService.updateImage(itemId, file, item);
        log.info("ItemService - update item : {}", item.getTitle());

        return ItemDTO.fromEntity(item);
    }

    public void deleteItem(Long itemId) throws Exception {
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new Exception("ITEM_DOES_NOT_EXIST"));
        itemRepository.delete(item);
    }

}
