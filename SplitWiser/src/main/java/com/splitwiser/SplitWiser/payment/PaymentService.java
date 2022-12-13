package com.splitwiser.SplitWiser.payment;

import com.splitwiser.SplitWiser.group.Group;
import com.splitwiser.SplitWiser.group.GroupRepository;
import com.splitwiser.SplitWiser.user.User;
import com.splitwiser.SplitWiser.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public PaymentService(PaymentRepository paymentRepository, GroupRepository groupRepository, UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }


    public void addPayment(int groupId, BigDecimal amount, Date date, String description, int payerId, Optional<Integer> receiverId) {
        Optional<Group> group = groupRepository.findById(groupId);
        Optional<User> payer = userRepository.findById(payerId);

        if (group.isEmpty() || payer.isEmpty()) {
            return;
        }
        if (receiverId.isEmpty()) {
            Payment groupPayment = new Payment(group.get(), amount, date, description, payer.get());
            paymentRepository.save(groupPayment);
        } else {
            Optional<User> receiver = userRepository.findById(receiverId.get());
            if (receiver.isPresent()) {
                Payment singlePayment = new Payment(group.get(), amount, date, description, payer.get(), receiver.get());
                paymentRepository.save(singlePayment);
            }
        }
    }
}
