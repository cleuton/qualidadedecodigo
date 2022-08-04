package com.pythondrops.qualidade;

public class EmprestimoConsignado extends EmprestimoBase {

    public EmprestimoConsignado(Cliente cliente, int prazoMeses, double montante) {
        this.cliente = cliente;
        this.prazoMeses = prazoMeses;
        this.montante = montante;
        this.taxa = calcularTaxaEmprestimoConsignado();
        if (this.prazoMeses < LIMITE_MESES_DESCONTO_TAXA_CONSIGNADO) {
            this.taxa *= PERCENTUAL_DESCONTO_CONSIGNADO_CURTO_PRAZO;
        }
    }

    private double calcularTaxaEmprestimoConsignado() {
        // fake tx consignado (foi no database etc)
        return 5.0d;
    }
}
