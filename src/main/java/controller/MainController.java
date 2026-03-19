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
import model.RecursionEngine;
import model.TreePainter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    // ===== FACTORIAL =====
    @FXML
    private Canvas canvasTree;
    @FXML
    private Slider sliderFactN;
    @FXML
    private Label lblFactN;
    @FXML
    private Button btnFactReset;
    @FXML
    private Button btnFactCalc;
    @FXML
    private Label lblComplexity;
    @FXML
    private Label lblFactCalls;
    @FXML
    private Label lblFactResult;
    @FXML
    private ListView<String> listSteps;

    // ===== FIB SIN MEMORIZACIÓN =====
    @FXML
    private Canvas canvasFibTree;
    @FXML
    private Slider sliderFibN;
    @FXML
    private Label lblFibN;
    @FXML
    private Button btnFibCalc;
    @FXML
    private Button btnFibReset;
    @FXML
    private Label lblFibResult;
    @FXML
    private Label lblFibCalls;
    @FXML
    private Label lblFibComplexity;
    @FXML
    private Label lblFibSaved;
    @FXML
    private ListView<String> listFibSteps;

    // ===== FIB CON MEMORIZACIÓN =====
    @FXML
    private Canvas canvasFibMemoTree;
    @FXML
    private Slider sliderFibMemoN;
    @FXML
    private Label lblFibMemoN;
    @FXML
    private Button btnFibMemoCalc;
    @FXML
    private Button btnFibMemoReset;
    @FXML
    private Label lblFibMemoResult;
    @FXML
    private Label lblFibMemoCalls;
    @FXML
    private Label lblFibMemoComplexity;
    @FXML
    private Label lblFibMemoSaved;
    @FXML
    private ListView<String> listFibMemoSteps;

    private final RecursionEngine engine = new RecursionEngine();
    private final TreePainter painter = new TreePainter();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupFactTab();
        setupFibTab();
        setupFibMemoTab();
    }

    // ================= FACTORIAL =================
    private void setupFactTab() {
        sliderFactN.setMin(1);
        sliderFactN.setMax(12);
        sliderFactN.setValue(5);
        sliderFactN.setMajorTickUnit(1);
        sliderFactN.setMinorTickCount(0);
        sliderFactN.setSnapToTicks(true);

        lblFactN.setText(String.valueOf((int) sliderFactN.getValue()));

        sliderFactN.valueProperty().addListener((observable, oldValue, newValue) ->
                lblFactN.setText(String.valueOf(newValue.intValue()))
        );

        btnFactCalc.setOnAction(event -> runFactorial());
        btnFactReset.setOnAction(event -> resetFactTab());
    }

    private void resetFactTab() {
        lblFactResult.setText("-");
        lblFactCalls.setText("-");
        lblComplexity.setText("-");
        listSteps.getItems().clear();
        painter.paint(canvasTree, null, 0, List.of());
    }

    private void runFactorial() {
        int n = (int) sliderFactN.getValue();

        engine.computeFactorial(n);
        RecursionEngine.CallNode root = engine.getTreeRoot();
        List<RecursionEngine.CallNode> bfs = TreePainter.collectBFS(root);

        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < engine.getSteps().size(); i++) {
            RecursionEngine.Step step = engine.getSteps().get(i);
            items.add(String.format("[%02d] %s", i + 1, step.description));
        }

        listSteps.setItems(items);
        lblFactResult.setText(String.valueOf(root.result));
        lblFactCalls.setText(String.valueOf(engine.getCallCount()));
        lblComplexity.setText("O(n) = O(" + n + ") llamadas");

        painter.paint(canvasTree, root, bfs.size(), bfs);
    }

    // ================= FIB SIN MEMORIZACIÓN =================
    private void setupFibTab() {
        sliderFibN.setMin(0);
        sliderFibN.setMax(15);
        sliderFibN.setValue(6);
        sliderFibN.setMajorTickUnit(1);
        sliderFibN.setMinorTickCount(0);
        sliderFibN.setSnapToTicks(true);

        lblFibN.setText(String.valueOf((int) sliderFibN.getValue()));

        sliderFibN.valueProperty().addListener((observable, oldValue, newValue) ->
                lblFibN.setText(String.valueOf(newValue.intValue()))
        );

        btnFibCalc.setOnAction(event -> runFib());
        btnFibReset.setOnAction(event -> resetFibTab());
    }

    private void resetFibTab() {
        lblFibResult.setText("-");
        lblFibCalls.setText("-");
        lblFibComplexity.setText("-");
        lblFibSaved.setText("--");
        listFibSteps.getItems().clear();
        painter.paint(canvasFibTree, null, 0, List.of());
    }

    private void runFib() {
        int n = (int) sliderFibN.getValue();

        long result = engine.computeFibonacci(n);
        RecursionEngine.CallNode root = engine.getTreeRoot();
        List<RecursionEngine.CallNode> bfs = TreePainter.collectBFS(root);

        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < engine.getSteps().size(); i++) {
            RecursionEngine.Step step = engine.getSteps().get(i);
            items.add(String.format("[%02d] %s", i + 1, step.description));
        }

        listFibSteps.setItems(items);
        lblFibResult.setText(String.valueOf(result));
        lblFibCalls.setText(String.valueOf(engine.getCallCount()));
        lblFibComplexity.setText("O(2^n) ≈ O(" + engine.getCallCount() + ") sin memorización");
        lblFibSaved.setText("--");

        painter.paint(canvasFibTree, root, bfs.size(), bfs);
    }

    // ================= FIB CON MEMORIZACIÓN =================
    private void setupFibMemoTab() {
        sliderFibMemoN.setMin(0);
        sliderFibMemoN.setMax(15);
        sliderFibMemoN.setValue(6);
        sliderFibMemoN.setMajorTickUnit(1);
        sliderFibMemoN.setMinorTickCount(0);
        sliderFibMemoN.setSnapToTicks(true);

        lblFibMemoN.setText(String.valueOf((int) sliderFibMemoN.getValue()));

        sliderFibMemoN.valueProperty().addListener((observable, oldValue, newValue) ->
                lblFibMemoN.setText(String.valueOf(newValue.intValue()))
        );

        btnFibMemoCalc.setOnAction(event -> runFibMemo());
        btnFibMemoReset.setOnAction(event -> resetFibMemoTab());
    }

    private void resetFibMemoTab() {
        lblFibMemoResult.setText("-");
        lblFibMemoCalls.setText("-");
        lblFibMemoComplexity.setText("-");
        lblFibMemoSaved.setText("-");
        listFibMemoSteps.getItems().clear();
        painter.paint(canvasFibMemoTree, null, 0, List.of());
    }

    private void runFibMemo() {
        int n = (int) sliderFibMemoN.getValue();

        long result = engine.computeFibonacciMemo(n);
        RecursionEngine.CallNode root = engine.getTreeRoot();
        List<RecursionEngine.CallNode> bfs = TreePainter.collectBFS(root);

        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < engine.getSteps().size(); i++) {
            RecursionEngine.Step step = engine.getSteps().get(i);
            items.add(String.format("[%02d] %s", i + 1, step.description));
        }

        listFibMemoSteps.setItems(items);
        lblFibMemoResult.setText(String.valueOf(result));
        lblFibMemoCalls.setText(String.valueOf(engine.getCallCount()));
        lblFibMemoComplexity.setText("O(n) = O(" + n + ") con memorización");
        lblFibMemoSaved.setText(String.valueOf(engine.getSavedCalls()));

        painter.paint(canvasFibMemoTree, root, bfs.size(), bfs);
    }
}