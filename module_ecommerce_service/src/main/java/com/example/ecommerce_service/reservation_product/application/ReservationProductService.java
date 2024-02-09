package com.example.ecommerce_service.reservation_product.application;

import com.example.ecommerce_service.common.exception.GlobalException;
import com.example.ecommerce_service.reservation_product.application.port.ReservationProductRepository;
import com.example.ecommerce_service.reservation_product.application.port.ReservationProductStockRepository;
import com.example.ecommerce_service.reservation_product.domain.ReservationProduct;
import com.example.ecommerce_service.reservation_product.domain.ReservationProductCreate;
import com.example.ecommerce_service.reservation_product.domain.ReservationProductStock;
import com.example.ecommerce_service.reservation_product.domain.ReservationProductUpdate;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class ReservationProductService {

    private final ReservationProductRepository reservationProductRepository;
    private final ReservationProductStockRepository reservationProductStockRepository;

    /**
     * 예약 상품 등록
     */
    @Transactional
    public Long create(final ReservationProductCreate reservationProductCreate) {
        final ReservationProduct reservationProduct = ReservationProduct.create(reservationProductCreate);

        final ReservationProduct saved = reservationProductRepository.save(reservationProduct);
        final ReservationProductStock reservationProductStock = ReservationProductStock.create(saved.getId(), reservationProductCreate);

        return reservationProductStockRepository.save(reservationProductStock).getProductId();
    }

    /**
     * 예약 상품 정보 수정
     */
    @Transactional
    public void update(final Long productId, final ReservationProductUpdate reservationProductUpdate) {
        reservationProductRepository.findById(productId)
                .map(product -> product.update(reservationProductUpdate))
                .map(reservationProductRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] reservation product not found"));

        reservationProductStockRepository.findById(productId)
                .map(productStock -> productStock.update(reservationProductUpdate))
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] reservation product stock not found"));
    }
}
