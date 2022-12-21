package com.splitwiser.SplitWiser.payment;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("")
    public List<Payment> getPayments() {
        return paymentService.getPayments();
    }

    @GetMapping("/{id}")
    public Payment getPayment(@PathVariable int id) {
        return paymentService.getPayment(id);
    }

    @PostMapping("/groups/{groupId}")
    @ResponseBody
    public Payment addPayment(@RequestBody Payment payment, @PathVariable int groupId) {
        paymentService.addPaymentToGroup(payment, groupId);
        return payment;
    }

}
