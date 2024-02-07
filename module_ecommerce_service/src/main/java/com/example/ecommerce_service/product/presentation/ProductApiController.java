package com.example.ecommerce_service.product.presentation;

import com.example.ecommerce_service.common.response.Response;
import com.example.ecommerce_service.product.application.ProductService;
import com.example.ecommerce_service.product.domain.ProductCreate;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/products")
public class ProductApiController {

    private final ProductService productService;

    @PostMapping
    public Response<Void> create(@RequestBody final ProductCreate productCreate) {
        productService.create(productCreate);
        return Response.success();
    }
}
