package com.splitwiser.SplitWiser.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.splitwiser.SplitWiser.group.Group;
import com.splitwiser.SplitWiser.user.User;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "PAYMENTS")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    @JoinColumn(name = "USER_ID")
    @JsonIgnoreProperties("group")
    private User payer;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "payments_receivers_users",
            joinColumns = @JoinColumn(name = "payment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnoreProperties("group")
    private List<User> receivers;


    @ManyToOne()
    @JsonIgnoreProperties("payments")
    private Group group;

    private BigDecimal amount;

    private LocalDate date;

    private String description;

    public Payment(BigDecimal amount, LocalDate date, String description) {
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public Payment() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getDate() {
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

    public List<User> getReceivers() {
        return receivers;
    }

    public User getPayer() {
        return payer;
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

    public void setReceivers(List<User> receivers) {
        this.receivers = receivers;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPayer(User payer) {
        this.payer = payer;
    }
}