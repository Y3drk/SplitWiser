package com.splitwiser.splitwiserclient.auxiliary;

import com.splitwiser.splitwiserclient.model.category.Category;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class CategoryCellFactory implements Callback<ListView<Category>, ListCell<Category>> {
    @Override
    public ListCell<Category> call(ListView<Category> param) {
        return new ListCell<>() {
            @Override
            public void updateItem(Category category, boolean empty) {
                super.updateItem(category, empty);
                if (empty || category == null) {
                    setText(null);
                } else {
                    setText(category.toString());
                }
            }
        };
    }
} 
