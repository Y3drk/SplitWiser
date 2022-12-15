package com.splitwiser.SplitWiser.group;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.splitwiser.SplitWiser.payment.Payment;
import com.splitwiser.SplitWiser.user.User;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "GROUPS")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    // without ignore - infinite loop
    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name="GROUP_ID")
    @JsonIgnoreProperties("group")
    private List<User> members;

    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name="GROUP_ID")
    @JsonIgnoreProperties("group")
    private List<Payment> payments;


    protected Group() {
    } // for JPA only

    public Group(String name) {
        this.name = name;
    }

    public void addPayment(Payment payment) {
        this.payments.add(payment);
    }

    public void addMember(User user) {
        this.members.add(user);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<User> getMembers() {
        return members;
    }

    public List<Payment> getPayments() {
        return payments;
    }
}
