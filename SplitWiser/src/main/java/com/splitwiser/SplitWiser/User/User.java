package com.splitwiser.SplitWiser.User;


import com.splitwiser.SplitWiser.Group.Group;
import jakarta.persistence.*;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @OneToOne
    private Group group;

    protected User() {
    } // for JPA only

    public User(String firstName, String lastName, Group group) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
    }
}
