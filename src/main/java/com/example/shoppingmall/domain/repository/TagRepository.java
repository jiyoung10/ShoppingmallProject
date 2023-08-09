package com.example.shoppingmall.domain.repository;

import com.example.shoppingmall.domain.entity.Item;
import com.example.shoppingmall.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByTagName(String tagName);

    Optional<Tag> findByItemId(Long itemId);
}
