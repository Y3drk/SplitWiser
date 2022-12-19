package com.splitwiser.splitwiserclient.auxiliary;

import com.splitwiser.splitwiserclient.model.group.Group;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

//TODO: for all cellFactories replace them with cellValueFactories, but
// "Trzeba tylko zapewnić by model zwracał propertiesy lub tworzyć je w locie, jeśli nie są potrzebne do bindowania"

public class GroupCellFactory implements Callback<ListView<Group>, ListCell<Group>> {
    @Override
    public ListCell<Group> call(ListView<Group> param) {
        return new ListCell<>(){
            @Override
            public void updateItem(Group group, boolean empty) {
                super.updateItem(group, empty);
                if (empty || group == null) {
                    setText(null);
                } else {
                    setText(group.getName());
                }
            }
        };
    }
}
