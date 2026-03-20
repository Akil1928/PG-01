package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecursionEngineTest {

    @Test
    void computeFactorial_deberiaDarVeinticuatro() {
        RecursionEngine engine = new RecursionEngine();

        long result = engine.computeFactorial(4);

        assertEquals(24, result);
        assertNotNull(engine.getTreeRoot());
        assertTrue(engine.getCallCount() > 0);
    }

    @Test
    void computeFibonacci_deberiaDarCinco() {
        RecursionEngine engine = new RecursionEngine();

        long result = engine.computeFibonacci(5);

        assertEquals(5, result);
        assertNotNull(engine.getTreeRoot());
        assertTrue(engine.getCallCount() > 0);
    }

    @Test
    void computeFibonacciMemo_deberiaDarOcho() {
        RecursionEngine engine = new RecursionEngine();

        long result = engine.computeFibonacciMemo(6);

        assertEquals(8, result);
        assertNotNull(engine.getTreeRoot());
        assertTrue(engine.getCallCount() > 0);
        assertTrue(engine.getSavedCalls() >= 0);
    }
}