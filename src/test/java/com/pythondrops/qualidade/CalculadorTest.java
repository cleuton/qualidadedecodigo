package com.pythondrops.qualidade;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class CalculadorTest {

    @Test
    void executar() {
        Calculador3 calc = new Calculador3();
        // x2 - 5x + 6 delta > 0
        assertTrue(Arrays.equals(calc.executar(1, -5, 6), new double[]{3.0, 2.0}));
        // 4x2 - 4x + 1 delta = 0
        assertTrue(Arrays.equals(calc.executar(4, -4, 1), new double[]{0.5, 0.5}));
        // 5x2 + x + 6 delta < 0
        assertTrue(Arrays.equals(calc.executar(5, 1, 6), new double[]{Double.NaN, Double.NaN}));
    }
}