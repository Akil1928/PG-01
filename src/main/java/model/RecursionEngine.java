package model;

import java.util.*;

public class RecursionEngine {

    public static class CallNode {
        public final String label;
        public final int n;
        public final List<CallNode> children = new ArrayList<>();
        public long result = -1;
        public boolean fromemo = false;
        public int depth;

        public CallNode(String label, int n, int depth) {
            this.label = label;
            this.n = n;
            this.depth = depth;
        }
    }

    public static class Step {
        public final String description;
        public final String expression;
        public final long partialResult;
        public final boolean isMemo;
        public final int callCount;

        public Step(String description, String expression, long partialResult, boolean memo, int calls) {
            this.description = description;
            this.expression = expression;
            this.partialResult = partialResult;
            this.isMemo = memo;
            this.callCount = calls;
        }
    }

    private final Map<Integer, Long> memo = new HashMap<>();
    private int callcaount;
    private final List<Step> steps = new ArrayList<>();
    private CallNode treeRoot;
    private int savedCalls;

    public void reset(){
        memo.clear();
        callcaount = 0;
        steps.clear();
        treeRoot = null;
        savedCalls = 0;
    }

    // ================= FACTORIAL =================
    public long computeFactorial(int n){
        reset();
        treeRoot = new CallNode("fact(" + n + ")", n, 0);
        long result = factorial(n, treeRoot, 0);
        steps.add(new Step(
                "Resultado final: fact(" + n + ") = " + result,
                "fact(" + n + ") = " + result,
                result,
                false,
                callcaount
        ));
        return result;
    }

    private long factorial(int n, CallNode parent, int depth) {
        callcaount++;
        String label = "fact(" + n + ")";

        steps.add(new Step(
                "Llamada No." + callcaount + ": " + label,
                label,
                -1,
                false,
                callcaount
        ));

        if(n <= 1) {
            parent.result = 1;
            steps.add(new Step(
                    "Caso base: " + label + " = 1",
                    label + " = 1",
                    1,
                    false,
                    callcaount
            ));
            return 1;
        }

        CallNode child = new CallNode("fact(" + (n - 1) + ")", n - 1, depth + 1);
        parent.children.add(child);

        long sub = factorial(n - 1, child, depth + 1);
        long result = (long) n * sub;
        parent.result = result;

        steps.add(new Step(
                "Retorno: " + label + " = " + n + " * " + sub + " = " + result,
                label + " = " + result,
                result,
                false,
                callcaount
        ));

        return result;
    }

    // ================= FIB NORMAL =================
    public long computeFibonacci(int n){
        reset();
        treeRoot = new CallNode("fib(" + n + ")", n, 0);
        long result = fib(n, treeRoot, 0);
        steps.add(new Step(
                "Resultado final: fib(" + n + ") = " + result,
                "fib(" + n + ") = " + result,
                result,
                false,
                callcaount
        ));
        return result;
    }

    private long fib(int n, CallNode parent, int depth){
        callcaount++;
        String label = "fib(" + n + ")";

        steps.add(new Step(
                "Llamada No." + callcaount + ": " + label,
                label,
                -1,
                false,
                callcaount
        ));

        if(n == 0){
            parent.result = 0;
            steps.add(new Step(
                    "Caso base: fib(0) = 0",
                    "fib(0) = 0",
                    0,
                    false,
                    callcaount
            ));
            return 0;
        }

        if(n == 1){
            parent.result = 1;
            steps.add(new Step(
                    "Caso base: fib(1) = 1",
                    "fib(1) = 1",
                    1,
                    false,
                    callcaount
            ));
            return 1;
        }

        CallNode left = new CallNode("fib(" + (n - 1) + ")", n - 1, depth + 1);
        CallNode right = new CallNode("fib(" + (n - 2) + ")", n - 2, depth + 1);

        parent.children.add(left);
        parent.children.add(right);

        long a = fib(n - 1, left, depth + 1);
        long b = fib(n - 2, right, depth + 1);

        long result = a + b;
        parent.result = result;

        steps.add(new Step(
                "Retorno: " + label + " = " + a + " + " + b + " = " + result,
                label + " = " + result,
                result,
                false,
                callcaount
        ));

        return result;
    }

    // ================= FIB MEMO =================
    public long computeFibonacciMemo(int n){
        reset();
        treeRoot = new CallNode("fib(" + n + ")", n, 0);
        long result = fibMemo(n, treeRoot, 0);
        steps.add(new Step(
                "Resultado final: fib(" + n + ") = " + result,
                "fib(" + n + ") = " + result,
                result,
                false,
                callcaount
        ));
        return result;
    }

    private long fibMemo(int n, CallNode parent, int depth){
        callcaount++;
        String label = "fib(" + n + ")";

        steps.add(new Step(
                "Llamada No." + callcaount + ": " + label,
                label,
                -1,
                false,
                callcaount
        ));

        if(memo.containsKey(n)){
            long memoValue = memo.get(n);
            parent.result = memoValue;
            parent.fromemo = true;
            savedCalls++;

            steps.add(new Step(
                    "Cache hit: " + label + " = " + memoValue,
                    label + " = " + memoValue,
                    memoValue,
                    true,
                    callcaount
            ));

            return memoValue;
        }

        if(n == 0){
            memo.put(0, 0L);
            parent.result = 0;

            steps.add(new Step(
                    "Caso base: fib(0) = 0",
                    "fib(0) = 0",
                    0,
                    false,
                    callcaount
            ));

            return 0;
        }

        if(n == 1){
            memo.put(1, 1L);
            parent.result = 1;

            steps.add(new Step(
                    "Caso base: fib(1) = 1",
                    "fib(1) = 1",
                    1,
                    false,
                    callcaount
            ));

            return 1;
        }

        CallNode left = new CallNode("fib(" + (n - 1) + ")", n - 1, depth + 1);
        CallNode right = new CallNode("fib(" + (n - 2) + ")", n - 2, depth + 1);

        parent.children.add(left);
        parent.children.add(right);

        long a = fibMemo(n - 1, left, depth + 1);
        long b = fibMemo(n - 2, right, depth + 1);

        long res = a + b;
        memo.put(n, res);
        parent.result = res;

        steps.add(new Step(
                "Retorno: " + label + " = " + a + " + " + b + " = " + res,
                label + " = " + res,
                res,
                false,
                callcaount
        ));

        return res;
    }

    // ================= GETTERS =================
    public List<Step> getSteps(){ return steps; }
    public CallNode getTreeRoot(){ return treeRoot; }
    public int getCallCount(){ return callcaount; }
    public int getSavedCalls(){ return savedCalls; }
}