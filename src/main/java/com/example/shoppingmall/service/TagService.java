package com.example.shoppingmall.service;

import com.example.shoppingmall.domain.entity.Item;
import com.example.shoppingmall.domain.entity.Tag;
import com.example.shoppingmall.domain.repository.TagRepository;
import com.example.shoppingmall.web.controller.request.ItemRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public void registerTag(Item item, ItemRegisterRequest itemRegisterRequest) {
        // 저장할 키워드 추출
        String[] tags = itemRegisterRequest.getTag();

        if (tags != null) {
            // 키워드 저장
            Arrays.stream(tags).forEach(tag -> {
                tagRepository.save(Tag.toEntity(tag, item));
            });
        }

    }

    @Transactional
    public List<Tag> updateTag(Item item, ItemRegisterRequest itemRegisterRequest) throws Exception {

        Tag tag = tagRepository.findByItemId(item.getId()).orElseThrow(
                () -> new Exception("TAG_DOES_NOT_EXIST"));
        tagRepository.delete(tag);

        if (itemRegisterRequest.getTag() == null) {
            log.error("TAG_NULL_ERROR");
        }

        return Arrays.stream(itemRegisterRequest.getTag())
                .map(updateTag -> forTagUpdate(updateTag, Item.toEntity(itemRegisterRequest)))
                .map(tagRepository::save)
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public Tag searchItemByTag(String tag) throws Exception {
        return tagRepository.findByTagName(tag).orElseThrow(
                () -> new Exception("Tag_DOES_NOT_EXIST"));
    }

    private Tag forTagUpdate(String tagName, Item item) {
        Tag tag = new Tag();
        return tag.updateTag(tagName, item);
    }

}
