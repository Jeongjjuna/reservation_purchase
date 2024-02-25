package com.example.module_product_service.product.application.port;

import com.example.module_product_service.product.domain.ReservationTime;
import java.util.Optional;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTime reservationTime);

    Optional<ReservationTime> findByProductId(Long productId);

}
