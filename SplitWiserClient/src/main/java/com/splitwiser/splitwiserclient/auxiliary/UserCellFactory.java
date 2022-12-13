package com.splitwiser.splitwiserclient.auxiliary;

import com.splitwiser.splitwiserclient.model.user.User;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class UserCellFactory implements Callback<ListView<User>, ListCell<User>> {
    @Override
    public ListCell<User> call(ListView<User> param) {
        return new ListCell<>(){
            @Override
            public void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                if (empty || user == null) {
                    setText(null);
                } else {
                    setText(user.getFirstName() + " " + user.getLastName()+ ", " + user.getGroup().getName());
                }
            }
        };
    }
}
