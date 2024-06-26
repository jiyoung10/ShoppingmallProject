package com.example.shoppingmall.domain.repository;

import com.example.shoppingmall.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findById(Long id);

    Optional<Item> findByTitle(String title);
}
