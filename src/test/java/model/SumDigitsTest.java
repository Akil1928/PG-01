package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SumDigitsTest {

    @Test
    void sumDigits_con20() {
        assertEquals(2, Recursion.sumDigitsRecursive(20));
    }

    @Test
    void sumDigits_con100() {
        assertEquals(1, Recursion.sumDigitsRecursive(100));
    }

    @Test
    void sumDigits_con1000() {
        assertEquals(1, Recursion.sumDigitsRecursive(1000));
    }

    @Test
    void sumDigits_con5000() {
        assertEquals(5, Recursion.sumDigitsRecursive(5000));
    }

    @Test
    void sumDigits_con10000() {
        assertEquals(1, Recursion.sumDigitsRecursive(10000));
    }

    //Probar de manera segura
    @Test
    void safeSumDigits_con10000() {
        assertEquals(1, Recursion.safeSumDigitsRecursive(100000000));
    }
}