package com.pythondrops.qualidade;

public class Calculador3 {

    /**
     * Calcula raízes de equação do segundo grau segundo a fórmula de Bhaskara
     * @param coeficienteA double O primeiro coeficiente da equação
     * @param coeficienteB double O segundo coeficiente da equação
     * @param coeficienteC double o terceiro coeficiente da equação
     * @return double [] com as raízes encontradas. Podem ser iguais, se delta zero ou ambas NaN, se menor que zero.
     */
    public double[] executar (double coeficienteA, double coeficienteB, double coeficienteC) {
        double valorDoDelta = calcularDelta(coeficienteA, coeficienteB, coeficienteC);
        return calcularRaizes(coeficienteA, coeficienteB, valorDoDelta);
    }

    /**
     * Calcula a(s) raiz(es) da equação (se houver) de acordo com a fórmula de Bhaskara
     * @param coeficienteA double Coeficiente A da equação
     * @param coeficienteB double Coeficiente B da equação
     * @param valorDoDelta double valor do delta
     * @return double[] vetor com as raízes
     */
    public double[] calcularRaizes(double coeficienteA, double coeficienteB, double valorDoDelta) {
        if (valorDoDelta < 0) {
            return new double[]{Double.NaN, Double.NaN};
        }
        if (valorDoDelta == 0) {
            double valorRaizUnica = calcularRaiz(coeficienteA, coeficienteB, valorDoDelta);
            return new double[]{valorRaizUnica, valorRaizUnica};
        }
        return new double[] {calcularRaiz(coeficienteA, coeficienteB, valorDoDelta),
                             calcularRaiz(coeficienteA, coeficienteB,
                                 -1 * Math.sqrt(valorDoDelta))};
    }

    private double calcularRaiz(double coeficienteA, double coeficienteB, double valorDoDelta) {
        return (-1 * coeficienteB + valorDoDelta) / (2 * coeficienteA);
    }

    /**
     * Calcula o valor Delta da equação do segundo grau.
     * @param coeficienteA double Primeiro coeficiente da equação
     * @param coeficienteB double Segundo coeficiente da equação
     * @param coeficienteC double Terceiro coeficiente da equação
     * @return valor do delta segundo a fórmula de Bhaskara
     */
    public double calcularDelta (double coeficienteA, double coeficienteB, double coeficienteC) {
        return Math.pow(coeficienteB,2) - 4 * coeficienteA * coeficienteC;
    }
}
