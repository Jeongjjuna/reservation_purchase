package com.example.ecommerce_service.payment.presentation;

import com.example.ecommerce_service.common.response.Response;
import com.example.ecommerce_service.payment.application.PaymentService;
import com.example.ecommerce_service.payment.domain.PaymentCreate;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/payments")
public class PaymentApiController {

    private final PaymentService paymentService;

    @PostMapping
    public Response create(@RequestBody final PaymentCreate paymentCreate) {
        String result = paymentService.create(paymentCreate);

        if (result.equals("failure")) {
            return Response.error("payment failure");
        }
        return Response.success();
    }
}
