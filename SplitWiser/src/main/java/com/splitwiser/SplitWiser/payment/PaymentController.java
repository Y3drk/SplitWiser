package com.splitwiser.SplitWiser.payment;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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


    @PostMapping("/payment/{groupId}")
    public void addPayment(@PathVariable int groupId, BigDecimal amount, Date date, String description, int payerId, Optional<Integer> receiverId) {
        paymentService.addPayment(groupId, amount, date, description, payerId, receiverId);
    }

}
