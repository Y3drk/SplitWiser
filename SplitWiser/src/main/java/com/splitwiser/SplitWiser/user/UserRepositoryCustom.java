package com.splitwiser.SplitWiser.user;

import com.splitwiser.SplitWiser.payment.Payment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepositoryCustom {
    List<Payment> findAllUserPayments(int groupId, int userId);
}
