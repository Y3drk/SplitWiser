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
        Optional<Group> group = groupRepository.findById(groupId);
        List<User> receivers = new ArrayList<>();
        Optional<User> payer = userRepository.findById(payerId);

        if (group.isPresent() && payer.isPresent()) {
            for (int receiverId : receiverIDs) {
                Optional<User> user = userRepository.findById(receiverId);
                user.ifPresent(receivers::add);
            }
            payment.setReceivers(receivers);
            payment.setPayer(payer.get());
            payment.setGroup(group.get());
            int id = paymentRepository.save(payment).getId();

            group.get().addPayment(payment);
            groupRepository.save(group.get());
            return id;
        }
        return -1;
    }

    public void addPaymentToGroup(Payment payment, int groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        group.get().addPayment(payment); // check if exists later todo
        paymentRepository.save(payment).getId();
        groupRepository.save(group.get());
    }
}
