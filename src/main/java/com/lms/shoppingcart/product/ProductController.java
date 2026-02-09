package com.lms.shoppingcart.product;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/peoducts")
public class ProductController {
    private final ProductService productService;

}
