package com.example.module_product_service.product.application.port;

import com.example.module_product_service.product.domain.ReservationTime;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTime reservationTime);

}
