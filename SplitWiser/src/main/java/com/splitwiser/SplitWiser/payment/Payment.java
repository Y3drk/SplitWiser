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
    private int id;

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

//    group payment
    public Payment(Group group, BigDecimal amount, Date date, String description, User payer) {
        this.group = group;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.payer = payer;
    }

//    single payment
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

    public int getId() {
        return id;
    }

    public Group getGroup() {
        return group;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setPayer(User payer) {
        this.payer = payer;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
