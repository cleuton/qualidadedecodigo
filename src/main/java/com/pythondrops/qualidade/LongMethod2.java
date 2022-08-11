package com.pythondrops.qualidade;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LongMethod2 {
    public static Logger logger = Logger.getGlobal();

/*
Primeiro refactoring: Removendo código duplicado: Logger
 */

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
