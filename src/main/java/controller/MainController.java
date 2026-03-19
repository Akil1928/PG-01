package controller;

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

    }
}
