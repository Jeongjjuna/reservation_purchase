package com.example.module_product_service.product.presentation;


import com.example.module_product_service.common.response.Response;
import com.example.module_product_service.product.application.ProductReadService;
import com.example.module_product_service.product.application.ProductService;
import com.example.module_product_service.product.domain.Product;
import com.example.module_product_service.product.domain.ProductCreate;
import com.example.module_product_service.product.domain.ProductUpdate;
import com.example.module_product_service.product.presentation.response.ProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/products")
public class ProductApiController {

    private final ProductService productService;
    private final ProductReadService productReadService;

    /**
     * 상품 생성
     */
    @PostMapping
    public Response<Void> create(@RequestBody final ProductCreate productCreate) {
        productService.create(productCreate);
        return Response.success();
    }

    /**
     * 상품 단건 조회
     */
    @GetMapping("/{productId}")
    public Response<ProductResponse> read(@PathVariable Long productId) {
        Product product = productReadService.find(productId);
        return Response.success(ProductResponse.from(product));
    }

    /**
     * 상품 전체 조회
     */
    @GetMapping
    public Response<Page<ProductResponse>> readAll() {
        final  Page<Product> reservationProducts = productReadService.findAll();
        return Response.success(reservationProducts.map(ProductResponse::from));
    }

    /**
     * 예약 상품 정보 수정 테스트
     */
    @PutMapping("/{productId}")
    public Response<Void> update(
            @PathVariable final Long productId,
            @RequestBody final ProductUpdate productUpdate
    ) {
        productService.update(productId, productUpdate);
        return Response.success();
    }
}
