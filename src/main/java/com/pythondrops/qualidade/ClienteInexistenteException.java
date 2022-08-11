package com.pythondrops.qualidade;

public class ClienteInexistenteException extends Exception{
    public ClienteInexistenteException() {
        super("Cliente inexistente");
    }
}
