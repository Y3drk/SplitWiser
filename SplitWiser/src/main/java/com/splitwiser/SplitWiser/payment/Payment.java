package com.splitwiser.SplitWiser.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.splitwiser.SplitWiser.group.Group;
import com.splitwiser.SplitWiser.user.User;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "PAYMENTS")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JsonIgnoreProperties("payments")
    private Group group;

    private BigDecimal amount;

    @ManyToOne
    @JsonIgnoreProperties("group")
    private User payer;

    @ManyToOne
    @JsonIgnoreProperties("group")
    private User receiver;

    private Date date;

    private String description;

    public Payment(Group group, BigDecimal amount, Date date, String description, User payer) {
        this.group = group;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.payer = payer;
    }

    public Payment(Group group, BigDecimal amount, Date date, String description, User payer, User receiver) {
        this.group = group;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.payer = payer;
        this.receiver = receiver;
    }

    public Payment() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public User getPayer() {
        return payer;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public long getId() {
        return id;
    }

    public Group getGroup() {
        return group;
    }

    public User getReceiver() {
        return receiver;
    }

}
