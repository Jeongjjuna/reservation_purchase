package com.example.ecommerce_service.product.presentation;

import com.example.ecommerce_service.common.response.Response;
import com.example.ecommerce_service.product.application.ProductService;
import com.example.ecommerce_service.product.domain.Product;
import com.example.ecommerce_service.product.domain.ProductCreate;
import com.example.ecommerce_service.product.presentation.response.ProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    /**
     * 상품 단건 조회
     */
    @GetMapping("/{id}")
    public Response<ProductResponse> read(@PathVariable Long id) {
        Product product = productService.find(id);
        return Response.success(ProductResponse.from(product));
    }
}
