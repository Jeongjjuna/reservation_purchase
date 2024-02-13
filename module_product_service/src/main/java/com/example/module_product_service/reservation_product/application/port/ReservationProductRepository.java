package com.example.module_product_service.reservation_product.application.port;


import com.example.module_product_service.reservation_product.domain.ReservationProduct;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationProductRepository {
    ReservationProduct save(ReservationProduct reservationProduct);

    Optional<ReservationProduct> findById(Long productId);

    Page<ReservationProduct> findAll(Pageable pageable);
}
