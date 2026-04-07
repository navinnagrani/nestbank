package com.nestbank.controller;

import com.nestbank.dto.CardPaymentRequest;
import com.nestbank.dto.TransferRequest;
import com.nestbank.dto.TransferResponse;
import com.nestbank.service.PaymentService;
import com.nestbank.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final TransferService transferService;
    private final PaymentService paymentService;

    public PaymentController(TransferService transferService,PaymentService paymentService) {
        this.transferService = transferService;
        this.paymentService = paymentService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(@RequestBody  TransferRequest request) {
        TransferResponse response = transferService.transfer(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/card")
    public ResponseEntity<?> payWithCard(@RequestBody CardPaymentRequest req) {

        String result = paymentService.payWithCard(req);

        return ResponseEntity.ok(
                Map.of("message", result)
        );
    }
}
