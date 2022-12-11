package com.splitwiser.splitwiserclient.mockData;

import com.splitwiser.splitwiserclient.model.group.Group;
import com.splitwiser.splitwiserclient.model.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MockDataProvider {

    public static ObservableList<User> getMockUsers(){
        Group group1 = new Group("G1");
        Group group2 = new Group("G2");

        User user1 = new User("Jan", "Kowalski", group1);
        User user2 = new User("Adam", "Nowak", group1);
        User user3 = new User("Jane", "Doe", group2);

        ObservableList<User> users = FXCollections.observableArrayList();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        return users;
    }
}
