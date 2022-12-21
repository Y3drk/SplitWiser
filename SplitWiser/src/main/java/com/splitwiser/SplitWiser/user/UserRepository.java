package com.splitwiser.SplitWiser.user;


import com.splitwiser.SplitWiser.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT p FROM Payment p WHERE (p.group.id = :groupId AND " +
            "(p.payer.id = :userId OR (SELECT u FROM User u WHERE u.id = :userId)" +
            " IN elements(p.receivers)))")
    List<Payment> findAllUserPayments(int groupId, int userId);

}
