package com.example.ecommerce_service.reservation_product.presentation;

import com.example.ecommerce_service.common.response.Response;
import com.example.ecommerce_service.reservation_product.application.ReservationProductReadService;
import com.example.ecommerce_service.reservation_product.application.ReservationProductService;
import com.example.ecommerce_service.reservation_product.domain.ReservationProduct;
import com.example.ecommerce_service.reservation_product.domain.ReservationProductCreate;
import com.example.ecommerce_service.reservation_product.domain.ReservationProductStock;
import com.example.ecommerce_service.reservation_product.domain.ReservationProductUpdate;
import com.example.ecommerce_service.reservation_product.presentation.response.ReservationProductResponse;
import com.example.ecommerce_service.reservation_product.presentation.response.ReservationProductStockResponse;
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
@RequestMapping("/v1/reservation-products")
public class ReservationProductApiController {

    private final ReservationProductService reservationProductService;
    private final ReservationProductReadService reservationProductReadService;

    @PostMapping
    public Response<Void> create(@RequestBody final ReservationProductCreate reservationProductCreate) {
        reservationProductService.create(reservationProductCreate);
        return Response.success();
    }

    /**
     * 예약 상품 단건 조회
     */
    @GetMapping("/{id}")
    public Response<ReservationProductResponse> read(@PathVariable Long id) {
        ReservationProduct reservationProduct = reservationProductReadService.find(id);
        return Response.success(ReservationProductResponse.from(reservationProduct));
    }

    /**
     * 예약 상품 전체 조회
     */
    @GetMapping
    public Response<Page<ReservationProductResponse>> readAll() {
        final  Page<ReservationProduct> reservationProducts = reservationProductReadService.findAll();
        return Response.success(reservationProducts.map(ReservationProductResponse::from));
    }

    /**
     * 예약 상품 재고 수량 조회
     */
    @GetMapping("/{id}/stock")
    public Response<ReservationProductStockResponse> readStockCount(@PathVariable Long id) {
        final ReservationProductStock reservationProductStock = reservationProductReadService.readStockCount(id);
        return Response.success(ReservationProductStockResponse.from(reservationProductStock));
    }

    /**
     * 예약 상품 정보 수정 테스트
     */
    @PutMapping("/{id}")
    public Response<Void> update(
            @PathVariable final Long id,
            @RequestBody final ReservationProductUpdate reservationProductUpdate
    ) {
        reservationProductService.update(id, reservationProductUpdate);
        return Response.success();
    }
}
