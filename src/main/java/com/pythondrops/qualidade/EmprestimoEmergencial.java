package com.pythondrops.qualidade;

public class EmprestimoEmergencial extends EmprestimoPassivelCurtoPrazo {

    public EmprestimoEmergencial(Cliente cliente, int prazoMeses, double montante) {
        super(cliente, prazoMeses, montante);
        if (!cliente.emprestimos.isEmpty()) {
            int conta = (int) cliente.emprestimos.stream()
                .filter(e -> e.getTipo() == TIPO_EMPRESTIMO_EMERGENCIAL).count();
            if (conta > LIMITE_EMPRESTIMOS_EMERGENCIAIS) {
                this.taxa *= calcularAumentoTaxaMuitosEmergenciais(conta);
            }
        }
    }

    private double calcularAumentoTaxaMuitosEmergenciais(int conta) {
        // fake tx para mais de 2 emp emerg
        return 1.18;
    }
}
