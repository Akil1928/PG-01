package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    @javafx.fxml.FXML
    private Canvas canvasTree;
    @javafx.fxml.FXML
    private Slider sliderFactN;
    @javafx.fxml.FXML
    private Label lblFact;
    @javafx.fxml.FXML
    private Button btnFactCalc;
    @javafx.fxml.FXML
    private Button btnFactReset;
    @javafx.fxml.FXML
    private Label lblComplexity;
    @javafx.fxml.FXML
    private Label lblFactCalls;
    @javafx.fxml.FXML
    private ListView listSteps;
    @javafx.fxml.FXML
    private Label lblFactResult;
//Jav

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Deprecated
    public void calcularOnAction(ActionEvent actionEvent) {
    }

    @Deprecated
    public void limpiarOnAction(ActionEvent actionEvent) {
    }
}