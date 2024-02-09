package com.example.ecommerce_service.reservation_product.application.port;

import com.example.ecommerce_service.reservation_product.domain.ReservationProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface ReservationProductRepository {
    ReservationProduct save(ReservationProduct reservationProduct);

    Optional<ReservationProduct> findById(Long productId);

    Page<ReservationProduct> findAll(Pageable pageable);
}
