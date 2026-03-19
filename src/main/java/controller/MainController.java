package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button btnFactCalc;

    @FXML
    private Button btnFactReset;

    @FXML
    private Canvas canvasTree;

    @FXML
    private Label lblComplexity;

    @FXML
    private Label lblFact;

    @FXML
    private Label lblFactCalls;

    @FXML
    private Label lblFactResult;

    @FXML
    private ListView<?> listSteps;

    @FXML
    private Slider sliderFactN;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupFactTab();
    }

    private void setupFactTab() {
sliderFactN.setMin(1);sliderFactN.setMax(12);sliderFactN.setValue(5);
sliderFactN.setMajorTickUnit(1);sliderFactN.setSnapToTicks(true);
sliderFactN.valueProperty().addListener((observable, oldValue, newValue) -> {
    lblFact.setText(String.valueOf(newValue));
});
btnFactCalc.setOnAction(e -> runFactorial());
btnFactReset.setOnAction(e -> resetFactorial());
    }

    private void resetFactorial() {
    }

    private void runFactorial() {
    }
}
