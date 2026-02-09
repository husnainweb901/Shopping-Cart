package com.lms.shoppingcart.image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProduct_ProductId(Long productId);
}
