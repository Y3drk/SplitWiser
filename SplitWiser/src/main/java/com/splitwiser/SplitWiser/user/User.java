package com.splitwiser.SplitWiser.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.splitwiser.SplitWiser.group.Group;
import com.splitwiser.SplitWiser.payment.Payment;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"receiverPayments"})
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;

    // without ignore - infinite loop
    @ManyToOne
    @JsonIgnoreProperties("members")
    private Group group;

    //    list of payments where user is receiver
    @ManyToMany(mappedBy = "receivers", fetch = FetchType.EAGER)
    private List<Payment> receiverPayments;

    protected User() {
    } // for JPA only

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

//    for tests
    public User(String firstName, String lastName, Group group) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setReceiverPayments(List<Payment> receiverPayments) {
        this.receiverPayments = receiverPayments;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Group getGroup() {
        return group;
    }

    public List<Payment> getReceiverPayments() {
        return receiverPayments;
    }

    public void addPaymentToReceiver(Payment payment) {
        receiverPayments.add(payment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(group, user.group) && Objects.equals(receiverPayments, user.receiverPayments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, group, receiverPayments);
    }
}
