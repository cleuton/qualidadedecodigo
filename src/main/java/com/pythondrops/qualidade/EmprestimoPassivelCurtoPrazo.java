package com.pythondrops.qualidade;

public class EmprestimoPassivelCurtoPrazo extends EmprestimoBase {

    public EmprestimoPassivelCurtoPrazo(Cliente cliente, int prazoMeses, double montante) {
        super(cliente, prazoMeses, montante);
        this.taxa *= calcularPercentualEmprestimoCurtoPrazo();
    }

    protected double calcularPercentualEmprestimoCurtoPrazo() {
        if (this.prazoMeses < EmprestimoBase.LIMITE_MESES_CURTO_PRAZO) {
            // fake
            return 1.02d;
        }
        return 1.0d;
    }
}
