package com.splitwiser.SplitWiser.payment;

import com.splitwiser.SplitWiser.group.Group;
import com.splitwiser.SplitWiser.group.GroupRepository;
import com.splitwiser.SplitWiser.user.User;
import com.splitwiser.SplitWiser.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Payment getPayment(int id) {
        Optional<Payment> result = this.paymentRepository.findById(id);
        return result.orElse(null);
    }

    public int addPayment(Payment payment, int groupId, int payerId, List<Integer> receiverIDs) {
        List<User> receivers = new ArrayList<>();

        return groupRepository.findById(groupId).map(group ->
               userRepository.findById(payerId).map(payer -> {
                    for (int receiverId : receiverIDs) {
                        Optional<User> user = userRepository.findById(receiverId);
                        user.ifPresent(receivers::add);
                    }
                    payment.setReceivers(receivers);
                    payment.setPayer(payer);
                    payment.setGroup(group);
                    int id = paymentRepository.save(payment).getId();

                    group.addPayment(payment);
                    groupRepository.save(group);
                    return id;
                }).orElse(-1)
            ).orElse(-1);
    }

    public void addPaymentToGroup(Payment payment, int groupId) {
        groupRepository.findById(groupId).map(group -> {
            group.addPayment(payment);
            paymentRepository.save(payment);
            return groupRepository.save(group);
        });
    }
}
