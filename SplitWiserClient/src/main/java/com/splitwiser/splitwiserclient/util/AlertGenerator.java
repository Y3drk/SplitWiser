package com.splitwiser.splitwiserclient.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertGenerator {

    public static void showConfirmationAlert(String text){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, text, ButtonType.OK);
        alert.showAndWait();
    }

    public static void showErrorAlert(String text){
        Alert alert = new Alert(Alert.AlertType.ERROR, text, ButtonType.OK);
        alert.showAndWait();
    }
}
