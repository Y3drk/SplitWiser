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

    @GetMapping("/payment/{id}")
    public Payment getPayment(@PathVariable int id) {
        return paymentService.getPayment(id);
    }

    @PostMapping("/payment/group/{groupId}")
    @ResponseBody
    public Payment addPayment(@RequestBody Payment payment, @PathVariable int groupId) {
        paymentService.addPaymentToGroup(payment, groupId);
        return payment;
    }

}
