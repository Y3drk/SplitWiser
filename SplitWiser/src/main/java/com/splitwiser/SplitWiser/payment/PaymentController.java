package com.splitwiser.SplitWiser.payment;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("")
    public List<Payment> getPayments() {
        return paymentService.getPayments();
    }


    @PostMapping("/{groupId}")
    public void postPayment(@PathVariable int groupId, BigDecimal amount, Date date, String description, int payerId, Optional<Integer> receiverId) {
        paymentService.postPayment(groupId, amount, date, description, payerId, receiverId);
    }

}
