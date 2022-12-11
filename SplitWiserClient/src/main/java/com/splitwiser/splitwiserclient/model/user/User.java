package com.splitwiser.splitwiserclient.model.user;

import com.splitwiser.splitwiserclient.model.group.Group;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private StringProperty firstName;

    private StringProperty lastName;

    private ObjectProperty<Group> group;

    public User(String firstName, String lastName, Group group){
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.group = new SimpleObjectProperty<>(group);
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
}
