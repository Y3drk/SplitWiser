package com.splitwiser.SplitWiser.payment;

import com.splitwiser.SplitWiser.group.Group;
import com.splitwiser.SplitWiser.user.User;
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
    public void postPayment(@PathVariable String groupId, BigDecimal amount, Date date, String description, Long payer, Optional<Long> receiver) {
        if (receiver.isEmpty()) {
            paymentService.postGroupPayment(Long.parseLong(groupId), amount, date, description, payer);
        } else {
            paymentService.postSinglePayment(Long.parseLong(groupId), amount, date, description, payer, receiver.get());
        }
    }

}
