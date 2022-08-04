package com.pythondrops.qualidade;

public class Calculador {
    public double [] executar (double a, double b, double c) {
        double d = calcularDelta(a, b, c);
        return d == Double.NaN ? new double[]{Double.NaN, Double.NaN}
            : d == 0 ? new double[]{(-b+d)/(2*a),(-b+d)/(2*a)}
                :  new double[]{(-b+d)/(2*a),(-b-d)/(2*a)};
    }

    public double calcularDelta (double a, double b, double c) {
        return (b*b-4*a*c)<0?Double.NaN:Math.sqrt(b*b-4*a*c);
    }
}
