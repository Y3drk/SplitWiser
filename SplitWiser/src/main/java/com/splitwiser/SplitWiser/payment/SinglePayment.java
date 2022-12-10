package com.splitwiser.SplitWiser.payment;

import com.splitwiser.SplitWiser.user.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
@DiscriminatorValue("single")
public class SinglePayment extends Payment {

    @OneToOne
    private User receiver;

}
