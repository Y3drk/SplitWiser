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

    public void setGraphView(SmartGraphPanel graphView) {
        graphView.setMinSize(400, 400);
        SmartGraphDemoContainer graphContainer = new SmartGraphDemoContainer(graphView);
        graphContainer.setMaxHeight(475);
        graphContainer.setMaxWidth(725);
        this.GraphPane.getChildren().add(graphContainer);
    }

    public void setDescriptionLabel(String description) {
        this.descriptionLabel.setText(description);
    }

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleComebackAction(ActionEvent actionEvent) {
        dialogStage.close();
    }

}
