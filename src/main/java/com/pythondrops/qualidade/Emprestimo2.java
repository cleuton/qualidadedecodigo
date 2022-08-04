package com.pythondrops.qualidade;

public class Emprestimo2 {
    private int tipo;
    private Cliente cliente;
    public double calcularTaxa (double montante,int meses) {
        /*
         * Calcula a taxa a ser usada no próprio emprestimo. Tem 4 tipos:
         * 1=consignado com desconto em folha
         * 2=normal
         * 3=emergencial. Se tiver mais de 1, aumenta a taxa em 15%
         * 4=especial
         * ele pega a taxa padrão do banco de dados, mas pode aumentar ou
         *  diminuir
         * de acordo com outros parâmetros.
         *
         */
        double tusar = lerTaxaPadrao(); // Lê a taxa padrão do banco de dados
        if (meses < 6 && (tipo == 2 || tipo == 4)) {
            // empréstimo de curto prazo, adicionar percentual à taxa padrão
            tusar = tusar * calcPercEcp();
        }
        switch (tipo) {
            case 1:
                tusar = calcTxc(); // é consignado use a taxa consig
                if (meses < 2) {
                    tusar = tusar * 0.80d; // Se menor que 2 meses, dê um desconto
                }
                break;
            case 2:
                // Empréstimo normal
                if (cliente.lcredcli < montante) {
                    // cliente pede mais que o limite
                    tusar = tusar * tcalcprcli(cliente); // calculo da tx risco
                }
                break;
            case 3:
                if (cliente.emprestimos.size() > 0) {
                    // ver se já tem outro emergencial. Se tiver, aumenta taxa em 15%
                    int conta = 0;
                    for (Emprestimo emp : cliente.emprestimos) {
                        if (emp.getTipo() == 3) {
                            conta++;
                        }
                    }
                    if (conta > 2) {
                        tusar = tusar * tcalctxempemadic(conta);
                    }
                }
                // tipo 4 é comum usar tx padr
        }
        return tusar;
    }

    private double tcalctxempemadic(int conta) {
        // fake tx para mais de 2 emp emerg
        return 1.18;
    }

    public Emprestimo2(int tipo, Cliente cliente) {
        this.tipo = tipo;
        this.cliente = cliente;
    }

    public int getTipo() {
        return this.tipo;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    private double tcalcprcli(Cliente cliente) {
        // % risco
        return 1.10d;
    }

    private double calcTxc() {
        // fake tx consignado
        return 5.0d;
    }

    private double calcPercEcp() {
        // fake
        return 1.02;
    }

    private double lerTaxaPadrao() {
        // finge que vai no banco:
        return 8.0d;
    }
}
