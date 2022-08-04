package com.pythondrops.qualidade;

public class Emprestimo3 {
    private int tipoEmprestimo;
    public static final int LIMITE_MESES_CURTO_PRAZO = 6;
    public static final int LIMITE_EMPRESTIMOS_EMERGENCIAIS = 2;
    public static final int LIMITE_MESES_DESCONTO_TAXA_CONSIGNADO = 2;
    public static final int TIPO_EMPRESTIMO_CONSIGNADO = 1;
    public static final int TIPO_EMPRESTIMO_NORMAL = 2;
    public static final int TIPO_EMPRESTIMO_EMERGENCIAL = 3;
    public static final int TIPO_EMPRESTIMO_ESPECIAL = 4;
    public static final double PERCENTUAL_DESCONTO_CONSIGNADO_CURTO_PRAZO = 0.80d;
    private Cliente cliente;
    public double calcularTaxa (double montante,int meses) {

        double taxaDoEmprestimo = lerTaxaPadraoDoDatabase();
        if (meses < LIMITE_MESES_CURTO_PRAZO && (tipoEmprestimo == TIPO_EMPRESTIMO_NORMAL || tipoEmprestimo == TIPO_EMPRESTIMO_ESPECIAL)) {
            taxaDoEmprestimo *= calcularPercentualEmprestimoCurtoPrazo();
        }
        switch (tipoEmprestimo) {
            case TIPO_EMPRESTIMO_CONSIGNADO:
                taxaDoEmprestimo = calcularTaxaEmprestimoConsignado();
                if (meses < LIMITE_MESES_DESCONTO_TAXA_CONSIGNADO) {
                    taxaDoEmprestimo *= PERCENTUAL_DESCONTO_CONSIGNADO_CURTO_PRAZO;
                }
                break;
            case TIPO_EMPRESTIMO_NORMAL:
                // nota: cliente.lcredcli = limite de crédito do cliente (refatorar em breve)
                if (cliente.lcredcli < montante) {
                    taxaDoEmprestimo *= calcularAumentoTaxaEmprestimoMaiorQueLimite(cliente);
                }
                break;
            case TIPO_EMPRESTIMO_EMERGENCIAL:
                if (!cliente.emprestimos.isEmpty()) {
                    int conta = (int) cliente.emprestimos.stream()
                        .filter(e -> e.getTipo() == TIPO_EMPRESTIMO_EMERGENCIAL).count();
                    if (conta > LIMITE_EMPRESTIMOS_EMERGENCIAIS) {
                        taxaDoEmprestimo *= calcularAumentoTaxaMuitosEmergenciais(conta);
                    }
                }

            default:
                break;
        }
        return taxaDoEmprestimo;
    }

    private double calcularAumentoTaxaMuitosEmergenciais(int conta) {
        // fake tx para mais de 2 emp emerg
        return 1.18;
    }

    public Emprestimo3(int tipoEmprestimo, Cliente cliente) {
        this.tipoEmprestimo = tipoEmprestimo;
        this.cliente = cliente;
    }

    public int getTipoEmprestimo() {
        return this.tipoEmprestimo;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    private double calcularAumentoTaxaEmprestimoMaiorQueLimite(Cliente cliente) {
        // % risco
        return 1.10d;
    }

    private double calcularTaxaEmprestimoConsignado() {
        // fake tx consignado
        return 5.0d;
    }

    private double calcularPercentualEmprestimoCurtoPrazo() {
        // fake
        return 1.02;
    }

    private double lerTaxaPadraoDoDatabase() {
        // finge que vai no banco:
        return 8.0d;
    }
}
