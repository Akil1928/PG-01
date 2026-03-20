package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import model.Recursion;
import model.RecursionEngine;
import model.TreePainter;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class MainController implements Initializable {

    // ===== FACT-FIB =====
    @FXML private TextField txtFactFibN;
    @FXML private RadioButton rbFactorial;
    @FXML private RadioButton rbFibonacci;
    @FXML private ToggleGroup tgFactFib;
    @FXML private Button btnFactFibCalc;
    @FXML private Button btnFactFibReset;
    @FXML private Label lblFactFibResult;
    @FXML private Label lblFactFibTime;
    @FXML private ListView<String> listFactFibSteps;

    // ===== FACTORIAL =====
    @FXML private Canvas canvasTree;
    @FXML private Slider sliderFactN;
    @FXML private Label lblFactN;
    @FXML private Button btnFactReset;
    @FXML private Button btnFactCalc;
    @FXML private Label lblComplexity;
    @FXML private Label lblFactCalls;
    @FXML private Label lblFactResult;
    @FXML private ListView<String> listSteps;

    // ===== FIB SIN MEMORIZACIÓN =====
    @FXML private Canvas canvasFibTree;
    @FXML private Slider sliderFibN;
    @FXML private Label lblFibN;
    @FXML private Button btnFibCalc;
    @FXML private Button btnFibReset;
    @FXML private Label lblFibResult;
    @FXML private Label lblFibCalls;
    @FXML private Label lblFibComplexity;
    @FXML private Label lblFibSaved;
    @FXML private ListView<String> listFibSteps;

    // ===== FIB CON MEMORIZACIÓN =====
    @FXML private Canvas canvasFibMemoTree;
    @FXML private Slider sliderFibMemoN;
    @FXML private Label lblFibMemoN;
    @FXML private Button btnFibMemoCalc;
    @FXML private Button btnFibMemoReset;
    @FXML private Label lblFibMemoResult;
    @FXML private Label lblFibMemoCalls;
    @FXML private Label lblFibMemoComplexity;
    @FXML private Label lblFibMemoSaved;
    @FXML private ListView<String> listFibMemoSteps;

    // ===== GRÁFICO =====
    @FXML private TabPane mainTabPane;
    @FXML private Tab tabGrafico;
    @FXML private BarChart<String, Number> barChartTimes;
    @FXML private BarChart<String, Number> barChartCalls;
    @FXML private CategoryAxis xAxisTimes;
    @FXML private NumberAxis yAxisTimes;
    @FXML private CategoryAxis xAxisCalls;
    @FXML private NumberAxis yAxisCalls;

    private final RecursionEngine engine = new RecursionEngine();
    private final TreePainter painter = new TreePainter();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupFactFibTab();
        setupFactTab();
        setupFibTab();
        setupFibMemoTab();
        setupGraphTab();
    }

    // ==================== FACT-FIB TAB ====================

    private void setupFactFibTab() {
        txtFactFibN.setOnAction(e -> runFactFib());
        btnFactFibCalc.setOnAction(e -> runFactFib());
        btnFactFibReset.setOnAction(e -> resetFactFibTab());

        // Solo permitir dígitos en el TextField
        txtFactFibN.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                txtFactFibN.setText(newVal.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void resetFactFibTab() {
        txtFactFibN.clear();
        lblFactFibResult.setText("---");
        lblFactFibTime.setText("---");
        listFactFibSteps.getItems().clear();
    }

    private void runFactFib() {
        String input = txtFactFibN.getText().trim();

        if (input.isEmpty()) {
            showFactFibError("Ingrese un valor para n.");
            return;
        }

        int n;
        try {
            n = Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            showFactFibError("Valor inválido. Ingrese un entero positivo.");
            return;
        }

        if (n < 0) {
            showFactFibError("n debe ser mayor o igual a 0.");
            return;
        }

        boolean isFactorial = rbFactorial.isSelected();

        if (isFactorial && n > 20) {
            showFactFibError("Factorial: use n <= 20 para evitar desbordamiento.");
            return;
        }
        if (!isFactorial && n > 30) {
            showFactFibError("Fibonacci sin memo: use n <= 30.");
            return;
        }

        long startNs = System.nanoTime();

        if (isFactorial) {
            computeFactFibFactorial(n);
        } else {
            computeFactFibFibonacci(n);
        }

        long elapsedNs = System.nanoTime() - startNs;
        lblFactFibTime.setText(formatNs(elapsedNs));
    }

    private void computeFactFibFactorial(int n) {
        engine.computeFactorial(n);
        long result = engine.getTreeRoot().result;
        lblFactFibResult.setText(formatLong(result));

        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < engine.getSteps().size(); i++) {
            items.add(String.format("[%02d] %s", i + 1, engine.getSteps().get(i).description));
        }
        listFactFibSteps.setItems(items);
    }

    private void computeFactFibFibonacci(int n) {
        engine.computeFibonacci(n);
        long result = engine.getTreeRoot().result;
        lblFactFibResult.setText(formatLong(result));

        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < engine.getSteps().size(); i++) {
            items.add(String.format("[%02d] %s", i + 1, engine.getSteps().get(i).description));
        }
        listFactFibSteps.setItems(items);
    }

    private void showFactFibError(String msg) {
        lblFactFibResult.setText("Error");
        lblFactFibTime.setText("---");
        listFactFibSteps.setItems(FXCollections.observableArrayList("⚠  " + msg));
    }

    // ==================== FACTORIAL TAB ====================

    private void setupFactTab() {
        sliderFactN.setMin(1);
        sliderFactN.setMax(12);
        sliderFactN.setValue(5);
        sliderFactN.setMajorTickUnit(1);
        sliderFactN.setMinorTickCount(0);
        sliderFactN.setSnapToTicks(true);
        lblFactN.setText(String.valueOf((int) sliderFactN.getValue()));
        sliderFactN.valueProperty().addListener((obs, o, nv) ->
                lblFactN.setText(String.valueOf(nv.intValue())));
        btnFactCalc.setOnAction(e -> runFactorial());
        btnFactReset.setOnAction(e -> resetFactTab());
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
            items.add(String.format("[%02d] %s", i + 1, engine.getSteps().get(i).description));
        }
        listSteps.setItems(items);
        lblFactResult.setText(String.valueOf(root.result));
        lblFactCalls.setText(String.valueOf(engine.getCallCount()));
        lblComplexity.setText("O(n) = O(" + n + ") llamadas");
        painter.paint(canvasTree, root, bfs.size(), bfs);
    }

    // ==================== FIB SIN MEMO TAB ====================

    private void setupFibTab() {
        sliderFibN.setMin(0);
        sliderFibN.setMax(15);
        sliderFibN.setValue(6);
        sliderFibN.setMajorTickUnit(1);
        sliderFibN.setMinorTickCount(0);
        sliderFibN.setSnapToTicks(true);
        lblFibN.setText(String.valueOf((int) sliderFibN.getValue()));
        sliderFibN.valueProperty().addListener((obs, o, nv) ->
                lblFibN.setText(String.valueOf(nv.intValue())));
        btnFibCalc.setOnAction(e -> runFib());
        btnFibReset.setOnAction(e -> resetFibTab());
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
            items.add(String.format("[%02d] %s", i + 1, engine.getSteps().get(i).description));
        }
        listFibSteps.setItems(items);
        lblFibResult.setText(String.valueOf(result));
        lblFibCalls.setText(String.valueOf(engine.getCallCount()));
        lblFibComplexity.setText("O(2^n) ≈ O(" + engine.getCallCount() + ") sin memorización");
        lblFibSaved.setText("--");
        painter.paint(canvasFibTree, root, bfs.size(), bfs);
    }

    // ==================== FIB CON MEMO TAB ====================

    private void setupFibMemoTab() {
        sliderFibMemoN.setMin(0);
        sliderFibMemoN.setMax(15);
        sliderFibMemoN.setValue(6);
        sliderFibMemoN.setMajorTickUnit(1);
        sliderFibMemoN.setMinorTickCount(0);
        sliderFibMemoN.setSnapToTicks(true);
        lblFibMemoN.setText(String.valueOf((int) sliderFibMemoN.getValue()));
        sliderFibMemoN.valueProperty().addListener((obs, o, nv) ->
                lblFibMemoN.setText(String.valueOf(nv.intValue())));
        btnFibMemoCalc.setOnAction(e -> runFibMemo());
        btnFibMemoReset.setOnAction(e -> resetFibMemoTab());
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
            items.add(String.format("[%02d] %s", i + 1, engine.getSteps().get(i).description));
        }
        listFibMemoSteps.setItems(items);
        lblFibMemoResult.setText(String.valueOf(result));
        lblFibMemoCalls.setText(String.valueOf(engine.getCallCount()));
        lblFibMemoComplexity.setText("O(n) = O(" + n + ") con memorización");
        lblFibMemoSaved.setText(String.valueOf(engine.getSavedCalls()));
        painter.paint(canvasFibMemoTree, root, bfs.size(), bfs);
    }

    // ==================== GRÁFICO TAB ====================

    private void setupGraphTab() {
        xAxisTimes.setLabel("n");
        yAxisTimes.setLabel("Tiempo (ns)");
        xAxisCalls.setLabel("n");
        yAxisCalls.setLabel("Número de llamadas");
        tabGrafico.setOnSelectionChanged(e -> {
            if (tabGrafico.isSelected()) loadBenchmarkCharts();
        });
    }

    private void loadBenchmarkCharts() {
        int[] values = {5, 10, 12, 15, 20};

        XYChart.Series<String, Number> timeArr  = new XYChart.Series<>(); timeArr.setName("Fib Memo Array");
        XYChart.Series<String, Number> timeHash = new XYChart.Series<>(); timeHash.setName("Fib Memo HashMap");
        XYChart.Series<String, Number> timeRec  = new XYChart.Series<>(); timeRec.setName("Fib Recursive");
        XYChart.Series<String, Number> callArr  = new XYChart.Series<>(); callArr.setName("Fib Memo Array");
        XYChart.Series<String, Number> callHash = new XYChart.Series<>(); callHash.setName("Fib Memo HashMap");
        XYChart.Series<String, Number> callRec  = new XYChart.Series<>(); callRec.setName("Fib Recursive");

        for (int n : values) {
            BenchmarkResult ar = benchmarkFibArray(n);
            BenchmarkResult hm = benchmarkFibHashMap(n);
            BenchmarkResult rc = benchmarkFibRecursive(n);
            String cat = String.valueOf(n);
            timeArr.getData().add(new XYChart.Data<>(cat, ar.timeNs));
            timeHash.getData().add(new XYChart.Data<>(cat, hm.timeNs));
            timeRec.getData().add(new XYChart.Data<>(cat, rc.timeNs));
            callArr.getData().add(new XYChart.Data<>(cat, ar.calls));
            callHash.getData().add(new XYChart.Data<>(cat, hm.calls));
            callRec.getData().add(new XYChart.Data<>(cat, rc.calls));
        }

        barChartTimes.getData().clear();
        barChartCalls.getData().clear();
        barChartTimes.getData().addAll(timeArr, timeHash, timeRec);
        barChartCalls.getData().addAll(callArr, callHash, callRec);
        barChartTimes.setTitle("Gráfico de Tiempos de ejecución T(n)");
        barChartCalls.setTitle("Gráfico de Llamadas Recursivas");
        barChartTimes.setAnimated(false);
        barChartCalls.setAnimated(false);
    }

    // ==================== BENCHMARK HELPERS ====================

    private static class BenchmarkResult {
        long timeNs; int calls;
        BenchmarkResult(long t, int c) { timeNs = t; calls = c; }
    }

    private BenchmarkResult benchmarkFibRecursive(int n) {
        long total = 0; int calls = 0;
        for (int i = 0; i < 5; i++) {
            AtomicInteger c = new AtomicInteger();
            long s = System.nanoTime();
            Recursion.fibonacci(n, c);
            total += System.nanoTime() - s;
            calls = c.get();
        }
        return new BenchmarkResult(total / 5, calls);
    }

    private BenchmarkResult benchmarkFibHashMap(int n) {
        long total = 0; int calls = 0;
        for (int i = 0; i < 5; i++) {
            AtomicInteger c = new AtomicInteger();
            Map<Integer, Long> memo = new HashMap<>();
            long s = System.nanoTime();
            Recursion.fibMemo(n, memo, c);
            total += System.nanoTime() - s;
            calls = c.get();
        }
        return new BenchmarkResult(total / 5, calls);
    }

    private BenchmarkResult benchmarkFibArray(int n) {
        long total = 0; int calls = 0;
        for (int i = 0; i < 5; i++) {
            AtomicInteger c = new AtomicInteger();
            long[] memo = new long[n + 1];
            long s = System.nanoTime();
            Recursion.fibMemoArray(n, memo, c);
            total += System.nanoTime() - s;
            calls = c.get();
        }
        return new BenchmarkResult(total / 5, calls);
    }

    // ==================== UTILIDADES ====================

    /** Formatea nanosegundos: ns / µs / ms / s */
    private String formatNs(long ns) {
        return String.format("%,d", ns).replace(",", " ") + " ns";
    }

    /**
     * Formatea números grandes con espacio como separador de miles,
     * tal como aparece en las capturas: "2 432 902 008 176 640 000"
     */
    private String formatLong(long value) {
        return String.format("%,d", value).replace(",", " ");
    }
}