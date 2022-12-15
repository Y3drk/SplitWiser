package com.splitwiser.SplitWiser.user;

import com.splitwiser.SplitWiser.payment.Payment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepositoryImpl implements UserRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Payment> findAllUserPayments(int groupId, int userId) {
        String hql = "SELECT p FROM Payment p WHERE (p.group.id = :groupId AND " +
                "(p.payer.id = :userId OR (SELECT u FROM User u WHERE u.id = :userId)" +
                " IN elements(p.receivers)))";
        TypedQuery<Payment> query = entityManager.createQuery(hql, Payment.class);
        query.setParameter("groupId", groupId);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
