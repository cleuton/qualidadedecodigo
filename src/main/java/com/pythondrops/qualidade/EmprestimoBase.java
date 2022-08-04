package com.pythondrops.qualidade;

public abstract class EmprestimoBase {

    public static final int LIMITE_MESES_CURTO_PRAZO = 6;
    public static final int LIMITE_EMPRESTIMOS_EMERGENCIAIS = 2;
    public static final int LIMITE_MESES_DESCONTO_TAXA_CONSIGNADO = 2;
    public static final int TIPO_EMPRESTIMO_CONSIGNADO = 1;
    public static final int TIPO_EMPRESTIMO_NORMAL = 2;
    public static final int TIPO_EMPRESTIMO_EMERGENCIAL = 3;
    public static final int TIPO_EMPRESTIMO_ESPECIAL = 4;
    public static final double PERCENTUAL_DESCONTO_CONSIGNADO_CURTO_PRAZO = 0.80d;

    protected Cliente cliente;
    protected int prazoMeses;
    protected double montante;
    protected double taxa;

    public EmprestimoBase(Cliente cliente, int prazoMeses, double montante) {
        this.cliente = cliente;
        this.prazoMeses = prazoMeses;
        this.montante = montante;
        this.taxa = lerTaxaPadraoDoDatabase();
    }

    protected EmprestimoBase() {
    }

    public Cliente getCliente() {
        return cliente;
    }

    public int getPrazoMeses() {
        return prazoMeses;
    }

    public double getMontante() {
        return montante;
    }

    public double getTaxa() {
        return taxa;
    }

    protected double lerTaxaPadraoDoDatabase() {
        // fake
        return 8.0d;
    }

}
