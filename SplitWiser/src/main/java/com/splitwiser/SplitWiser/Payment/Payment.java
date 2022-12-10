package com.splitwiser.SplitWiser.Payment;

import com.splitwiser.SplitWiser.Group.Group;
import com.splitwiser.SplitWiser.User.User;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "PAYMENTS")
@DiscriminatorColumn(name = "type")
public abstract class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    private Group group;

    private BigDecimal amount;

    @OneToOne
    private User payer;

    private Date date;

    private String description;


}
