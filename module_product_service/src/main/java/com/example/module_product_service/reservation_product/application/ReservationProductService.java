package com.example.module_product_service.reservation_product.application;


import com.example.module_product_service.common.exception.GlobalException;
import com.example.module_product_service.reservation_product.application.port.ReservationProductRepository;
import com.example.module_product_service.reservation_product.application.port.ReservationProductStockRepository;
import com.example.module_product_service.reservation_product.domain.ReservationProduct;
import com.example.module_product_service.reservation_product.domain.ReservationProductCreate;
import com.example.module_product_service.reservation_product.domain.ReservationProductStock;
import com.example.module_product_service.reservation_product.domain.ReservationProductUpdate;
import java.util.Optional;
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
        final ReservationProductStock saved = Optional.of(ReservationProduct.create(reservationProductCreate))  // 예약 상품 생성/저장
                .map(reservationProductRepository::save)
                .map(savedProduct -> ReservationProductStock.create(savedProduct.getId(), reservationProductCreate)) // 예약 상품 재고 생성/저장
                .map(reservationProductStockRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR, "[ERROR] reservation product save fail"));
        return saved.getProductId();
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
                .map(productStock -> productStock.update(reservationProductUpdate.getStockCount()))
                .map(reservationProductStockRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] reservation product stock not found"));
    }

    /**
     * 재소 수량 변경
     */
    @Transactional
    public ReservationProductStock updateStock(final Long reservationProductId, final ReservationProductStock reservationProductStock) {
        return reservationProductStockRepository.findById(reservationProductId)
                .map(productStock -> productStock.update(reservationProductStock.getStockCount()))
                .map(reservationProductStockRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] reservation product stock not found"));
    }
}
