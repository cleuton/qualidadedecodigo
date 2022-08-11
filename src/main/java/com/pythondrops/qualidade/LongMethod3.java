package com.pythondrops.qualidade;

import com.google.common.base.Preconditions;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.StreamSupport;

public class LongMethod3 {
    public static Logger logger = Logger.getGlobal();
    private final String MSG_INICIO = "Inicio: ";
    private final String CLIENTE_TEM_TRANSACAO = "cli tem trx: ";
    private final String CLIENTE_NAO_TEM_TRANSACAO = "cli nao tem trx: ";
    private long start_time;
/*
Segundo refactoring: Resolvendo múltiplas responsabilidades
 */

    private boolean criarAtualizarTransacaoCliente(Transacao tr) throws ThxNulaException, ClienteInexistenteException {
        Preconditions.checkNotNull(tr, "Transacao nula!");
        Preconditions.checkArgument(tr.isOpen(), "Transacao com isOpen() false");
        start_time = System.nanoTime();
        String thx = Optional.ofNullable(QueueProcessMgmt.getThsx()).orElseThrow(ThxNulaException::new);
        logMsg(Level.INFO,MSG_INICIO, start_time);
        ClFin clienteFinanceiro = Optional.ofNullable(ClienteRepo.getRefreshCliente(tr.getCliId()))
                .orElseThrow(ClienteInexistenteException::new);
        Optional<Transacao> transacaoDoCliente = Optional.ofNullable(obterTransacaoDoCliente(clienteFinanceiro, tr);
        if (transacaoDoCliente.isPresent()) {
            logMsg(Level.INFO,CLIENTE_TEM_TRANSACAO, start_time);
            tr.listSt.add((new Date()) + " acesso ");
            atualizarStatusTransacao(tr);
            return true;
        }
        else {
            logMsg(Level.INFO, CLIENTE_NAO_TEM_TRANSACAO, start_time);
            tr.listSt.add((new Date()) + " adicionada ");
            atualizarStatusTransacao(tr);
            adicionarTransacaoAoCliente(clienteFinanceiro, tr);
            return false;
        }
    }

    private Optional<Transacao> obterTransacaoDoCliente(ClFin clienteFinanceiro, Transacao tr) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(clienteFinanceiro.trxs, Spliterator.ORDERED),
                false)
                .filter(t -> t.chave == tr.chave)
                .findFirst();
    }

    private void adicionarTransacaoAoCliente(ClFin clienteFinanceiro, Transacao tr) {
    }

    private void atualizarStatusTransacao(Transacao tr) {
    }


    private boolean obterStatus (Transacao tr) throws Exception {
        long start_time = System.nanoTime();
        String thx = QueueProcessMgmt.getThsx();
        if (thx!=null) {
            logMsg(Level.INFO, "Inicio: ", start_time);
        }
        if (tr.isOpen()) {
            if(thx==null)
                throw new Exception();
            ClFin c = ClienteRepo.getRefreshCliente(tr.getCliId());
            if (c==null) {
                logMsg(Level.SEVERE, "Err cli update: ", start_time);
                throw new Exception();
            }
            boolean ctem = false;
            while (c.trxs.hasNext()) {
                Transacao tc = c.trxs.next();
                if (tc.chave == tr.chave) {
                    ctem = true;
                    break;
                }
            }
            if (ctem) {
                logMsg(Level.INFO,"cli tem trx: ", start_time);
                tr.listSt.add((new Date()) + " acesso ");
                boolean stUtr = TxRepo.update(tr);
                if (!stUtr) {
                    logMsg(Level.SEVERE, "erro updtx: ", start_time);
                    throw new Exception();
                }
                return true;
            }
            logMsg(Level.INFO, "cli nao tem trx: ", start_time);
            tr.listSt.add((new Date()) + " adicionada ");
            boolean stUtr = TxRepo.update(tr);
            if (!stUtr) {
                logMsg(Level.SEVERE, "erro updtx: ", start_time);
                throw new Exception();
            }
            c.trans.add(tr);
            boolean bz = ClienteRepo.updC(c);
            if (!bz) {
                logMsg(Level.SEVERE, "erro updc: ", start_time);
                throw new Exception();
            }
            return false;
        }
        logMsg(Level.SEVERE,"tr not open ", start_time);
        return false;
    }

    private void logMsg(Level nivel, String mensagem, long inicio) {
        long tId = Thread.currentThread().getId();
        long evTime = System.nanoTime();
        long difTime = evTime - inicio;
        String thx = QueueProcessMgmt.getThsx();
        logger.log(nivel, String.format("%s %d thread Id: %d tempo: %d",thx,tId,difTime));
    }


    // Fake classes and methods to support above method......
    private class Transacao {

        public boolean chave;
        public List<String> listSt;

        public boolean isOpen() {
            return true;
        }

        public int getCliId() {
            return 0;
        }
    }

    private static class QueueProcessMgmt {

        public static String getThsx() {
            return null;
        }
    }

    private static class ClienteRepo {

        public static ClFin getRefreshCliente(int cliId) {
            return null;
        }

        public static boolean updC(ClFin c) {
            return true;
        }
    }

    private class ClFin {

        public Iterator<Transacao> trxs;
        public List<Transacao> trans;
    }

    private static class TxRepo {

        public static boolean update(Transacao tr) {
            return true;
        }
    }
}
