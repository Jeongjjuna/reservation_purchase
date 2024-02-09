package com.example.ecommerce_service.reservation_product.application;

import com.example.ecommerce_service.common.exception.GlobalException;
import com.example.ecommerce_service.reservation_product.application.port.ReservationProductRepository;
import com.example.ecommerce_service.reservation_product.application.port.ReservationProductStockRepository;
import com.example.ecommerce_service.reservation_product.domain.ReservationProduct;
import com.example.ecommerce_service.reservation_product.domain.ReservationProductStock;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ReservationProductReadService {

    private final ReservationProductRepository reservationProductRepository;
    private final ReservationProductStockRepository reservationProductStockRepository;

    public ReservationProduct find(final Long productId) {
        return reservationProductRepository.findById(productId).orElseThrow(() ->
                new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] reservation product not found"));
    }

    public Page<ReservationProduct> findAll() {
        final Pageable pageable = PageRequest.of(0, 20);
        return reservationProductRepository.findAll(pageable);
    }

    public ReservationProductStock readStockCount(final Long productId) {
        return reservationProductStockRepository.findById(productId)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] reservation product stock not found"));
    }
}
