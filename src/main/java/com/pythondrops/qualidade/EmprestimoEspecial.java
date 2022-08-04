package com.pythondrops.qualidade;

public class EmprestimoEspecial extends EmprestimoPassivelCurtoPrazo {

    public EmprestimoEspecial(Cliente cliente, int prazoMeses, double montante) {
        super(cliente, prazoMeses, montante);
    }
}
