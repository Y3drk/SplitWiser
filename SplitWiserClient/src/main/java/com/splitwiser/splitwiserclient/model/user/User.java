package com.splitwiser.splitwiserclient.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.splitwiser.splitwiserclient.model.group.Group;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

@JsonIgnoreProperties(value = {"group"})
public class User {
    int id;

    @JsonProperty("firstName")
    private StringProperty firstName = new SimpleStringProperty();


    @JsonProperty("lastName")
    private StringProperty lastName = new SimpleStringProperty();

    private ObjectProperty<Group> group = new SimpleObjectProperty<>();

    public User(String firstName, String lastName, Group group) {
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.group.set(group);
    }

    // for Jackson
    public User() {
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public Group getGroup() {
        return group.get();
    }

    public ObjectProperty<Group> groupProperty() {
        return group;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public void setGroup(Group group) {
        this.group.set(group);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
