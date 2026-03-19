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
import model.Recursion;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

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
    private Label lblFactCalls;

    @FXML
    private Label lblFactResult;

    @FXML
    private ListView<String> listSteps;

    @FXML
    private Slider sliderFactN;
    @FXML
    private Label lblFactN;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupFactTab();
    }

    private void setupFactTab() {
        sliderFactN.setMin(1);
        sliderFactN.setMax(12);
        sliderFactN.setValue(5);
        sliderFactN.setMajorTickUnit(1);
        sliderFactN.setSnapToTicks(true);
        sliderFactN.valueProperty().addListener((observable, oldValue, newValue) -> {
            lblFactN.setText(String.valueOf(newValue.intValue()));
        });
        btnFactCalc.setOnAction(event -> runFactorial());
        btnFactReset.setOnAction(e -> resetFactTab());
    }

    private void resetFactTab() {
        lblFactResult.setText("-");
        lblFactCalls.setText("-");
        lblComplexity.setText("-");
        listSteps.getItems().clear();
    }

    private void runFactorial() {
        int n = (int) sliderFactN.getValue();
        AtomicInteger counter = new AtomicInteger(0);
        long result = Recursion.factorial(n, counter);
        lblFactResult.setText(util.Utility.format(result));
        lblFactCalls.setText(String.valueOf(counter.get()));

        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i <= n; i++) {
            items.add(String.format("[%02d]", i+1));
        }
        listSteps.setItems(items);
        lblComplexity.setText("0(n) = 0("+n+") llamadas");

    }


}
