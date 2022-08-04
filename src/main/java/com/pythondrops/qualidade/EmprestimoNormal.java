package com.pythondrops.qualidade;

public class EmprestimoNormal extends EmprestimoPassivelCurtoPrazo {

    public EmprestimoNormal(Cliente cliente, int prazoMeses, double montante) {
        super(cliente, prazoMeses, montante);
        // nota: cliente.lcredcli = limite de crédito do cliente (refatorar em breve)
        if (this.cliente.lcredcli < montante) {
            this.taxa *= calcularAumentoTaxaEmprestimoMaiorQueLimite();
        }
    }

    private double calcularAumentoTaxaEmprestimoMaiorQueLimite() {
        // % risco
        return 1.10d;
    }

}
