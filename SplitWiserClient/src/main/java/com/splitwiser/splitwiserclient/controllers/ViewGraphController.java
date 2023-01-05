package com.splitwiser.splitwiserclient.controllers;

import com.splitwiser.splitwiserclient.util.libs.graphs.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer;
import com.splitwiser.splitwiserclient.util.libs.graphs.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewGraphController {

    @FXML
    private Label descriptionLabel;
    @FXML
    private Pane GraphPane;
    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setGraphView(SmartGraphPanel graphView){
        this.GraphPane.getChildren().add(new SmartGraphDemoContainer(graphView));
    }

    public void setDescriptionLabel(String description){
        this.descriptionLabel.setText(description);
    }

    @FXML
    private void initialize(){
    }

    @FXML
    private void handleComebackAction(ActionEvent actionEvent) {
        dialogStage.close();
    }

}
