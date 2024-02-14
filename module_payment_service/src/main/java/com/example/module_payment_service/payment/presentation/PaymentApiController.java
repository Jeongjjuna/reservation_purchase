package com.example.module_payment_service.payment.presentation;

import com.example.module_payment_service.common.response.Response;
import com.example.module_payment_service.payment.application.PaymentService;
import com.example.module_payment_service.payment.domain.PaymentCreate;
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
    public Response<String> create(@RequestBody final PaymentCreate paymentCreate) {
        String result = paymentService.create(paymentCreate);

        if (result.equals("failure")) {
            return Response.error("payment failure");
        }
        return Response.success();
    }
}
