package com.pythondrops.qualidade;

public class Calculador2 {
    public double [] executar (double a, double b, double c) {
        double d = calcularDelta(a, b, c);
        return d == Double.NaN ? new double[]{Double.NaN, Double.NaN}
            : d == 0 ? new double[]{(-b+d)/(2*a),(-b+d)/(2*a)}
                :  new double[]{(-b+Math.sqrt(d))/(2*a),(-b-Math.sqrt(d))/(2*a)};
    }

    /**
     * Calcula o valor Delta de equação do segundo grau
     * @param coeficienteA double Primeiro coeficiente da equação
     * @param coeficienteB double Segundo coeficiente da equação
     * @param coeficienteC double Terceiro coeficiente da equação
     * @return double valor do delta ou NaN, se for menor que zero
     */
    public double calcularDelta (double coeficienteA, double coeficienteB, double coeficienteC) {
        double valorDelta = Math.pow(coeficienteB,2) - 4 * coeficienteA * coeficienteC;
        if (valorDelta < 0) {
            return Double.NaN;
        }
        return valorDelta;
    }
}
