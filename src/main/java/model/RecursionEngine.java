package model;
//Motor de recursividad implementa factorial y fibonacci en javafx

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RecursionEngine {
    //nodo de un arbol de llamadas recursivas
    public static class CallNode {

        public final String label;//Escribir factorial de 5 ejm
        public final int n;//
        public final List<CallNode> children = new ArrayList<>();
        public long result = -1;
        public boolean fromemo = false;//para fib con memorizacion
        public int depth;

        public CallNode(String label, int n, int depth) {
            this.label = label;
            this.n = n;
            this.depth = depth;
        }

    }

    //Datos de cada llamada recursiva o paso recursivo
    public static class Step {
        public final String description;
        public final String expression;
        public final long parcialResult;
        public final boolean isMemo;
        public final int callCount;//Contador de llamadas recursivas

        public Step(String description, String expression, long result, boolean memo, int calls) {
            this.description = description;
            this.expression = expression;
            this.parcialResult = result;
            this.isMemo = memo;
            this.callCount = calls;
        }
    }

    //atributos del estdo interno
    private final Map<Integer, Long> memo = new HashMap<>();
    private int callCount;
    private final List<Step> steps = new ArrayList<>();
    private CallNode treeRoot;//Raiz del arbol de llamadas recursivas

    public void reset() {
        memo.clear();
        callCount = 0;
        steps.clear();
        treeRoot = null;
    }

    //factorial
    public long computeFactorial(int n) {
        reset();
        treeRoot = new CallNode("Fact(" + n + ")", n, 0);
        long result = factorial(n, treeRoot, 0);
        steps.add(new Step("Resultado final", n + "! = " + result, result, false, callCount));
        return result;
    }

    private long factorial(int n, CallNode parent, int depth) {
        callCount++;
        String label = "Fact(" + n + ")";
        steps.add(new Step("Llamada No. " + callCount + label, buildFactExp(n), -1, false, callCount));

        if (n <= 1) {
            parent.result = 1;
            steps.add(new Step("Caso base: " + label + " = 1", "Fact(1) = 1", 1, false, callCount));
            return 1;
        }
        CallNode child = new CallNode("fact(" + (n - 1) + ")", n - 1, depth + 1);
        parent.children.add(child);
        long sub = factorial(n - 1, child, depth + 1);
        long result = (long) n * sub;
        parent.result = result;
        steps.add(new Step("Retorno: " + label + " = " + n + " * " + sub + " = " + result, label + " = " + result, result, false, callCount));
        return result;
    }

    private String buildFactExp(int n) {
        if (n <= 1) return "1";
        StringBuilder sb = new StringBuilder();

        for (int i = n; i >= 1; i--) {
            sb.append(i);
            if (i > 1) sb.append(" * ");

        }
        return sb.toString();
    }

    //creamos los getters
    public List<Step> getSteps() {
        return steps;
    }

    public CallNode getTreeRoot() {
        return treeRoot;
    }

    public int getCallCount() {
        return callCount;
    }

    public Map<Integer, Long> getMemo() {
        return memo;
    }

}
