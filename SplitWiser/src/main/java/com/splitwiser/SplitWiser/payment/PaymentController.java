package com.splitwiser.SplitWiser.payment;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/payments")
    public List<Payment> getPayments() {
        return paymentService.getPayments();
    }


    @PostMapping("/payment/group/{groupId}/{payerId}/{receiverIds}")
    public void addPayment(@RequestBody Payment payment, @PathVariable int groupId, @PathVariable int payerId, @PathVariable("receiverIds") Integer[] receiverIds) {
        paymentService.addPayment(payment, groupId, payerId, List.of(receiverIds));
    }

}
