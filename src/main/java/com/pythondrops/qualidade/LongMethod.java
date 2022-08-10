package com.pythondrops.qualidade;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LongMethod {
    public static Logger logger = Logger.getGlobal();



    private boolean obterStatus (Transacao tr) throws Exception {
        long start_time = System.nanoTime();
        long tId = Thread.currentThread().getId();
        String thx = QueueProcessMgmt.getThsx();
        if (thx!=null) {
            logger.log(Level.INFO,"Inicio: " + thx + " thread Id: " + tId + " tempo: " + 0);
        }
        if (tr.isOpen()) {
            if(thx==null)
                throw new Exception();
            ClFin c = ClienteRepo.getRefreshCliente();
            if (c==null) {
                long evTime = System.nanoTime();
                long difTime = evTime - start_time;
                thx = QueueProcessMgmt.getThsx();
                logger.log(Level.SEVERE,"Err cli update: " + thx + " thread Id: " + tId + " tempo: " + difTime);
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
                long evTime = System.nanoTime();
                long difTime = evTime - start_time;
                thx = QueueProcessMgmt.getThsx();
                logger.log(Level.INFO,"cli tem trx: " + thx + " thread Id: " + tId + " tempo: " + difTime);
                tr.listSt.add((new Date()) + " acesso ");
                boolean stUtr = TxRepo.update(tr);
                if (!stUtr) {
                    evTime = System.nanoTime();
                    difTime = evTime - start_time;
                    thx = QueueProcessMgmt.getThsx();
                    logger.log(Level.SEVERE,"erro updtx: " + thx + " thread Id: " + tId + " tempo: " + difTime);
                    throw new Exception();
                }
                return true;
            }
            long evTime = System.nanoTime();
            long difTime = evTime - start_time;
            thx = QueueProcessMgmt.getThsx();
            logger.log(Level.INFO,"cli nao tem trx: " + thx + " thread Id: " + tId + " tempo: " + difTime);
            tr.listSt.add((new Date()) + " adicionada ");
            boolean stUtr = TxRepo.update(tr);
            if (!stUtr) {
                evTime = System.nanoTime();
                difTime = evTime - start_time;
                thx = QueueProcessMgmt.getThsx();
                logger.log(Level.SEVERE,"erro updtx: " + thx + " thread Id: " + tId + " tempo: " + difTime);
                throw new Exception();
            }
            c.trans.add(tr);
            boolean bz = ClienteRepo.updC(c);
            if (!bz) {
                evTime = System.nanoTime();
                difTime = evTime - start_time;
                thx = QueueProcessMgmt.getThsx();
                logger.log(Level.SEVERE,"erro updc: " + thx + " thread Id: " + tId + " tempo: " + difTime);
                throw new Exception();
            }
            return false;
        }
        long evTime = System.nanoTime();
        long difTime = evTime - start_time;
        thx = QueueProcessMgmt.getThsx();
        logger.log(Level.SEVERE,"final: " + thx + " thread Id: " + tId + " tempo: " + difTime);
    }






    // Fake classes and methods to support above method......
    private class Transacao {

        public boolean chave;
        public List<String> listSt;

        public boolean isOpen() {
        }
    }

    private static class QueueProcessMgmt {

        public static String getThsx() {
            return null;
        }
    }

    private static class ClienteRepo {

        public static ClFin getRefreshCliente() {
            return null;
        }

        public static boolean updC(ClFin c) {
        }
    }

    private class ClFin {

        public Iterator<Transacao> trxs;
        public List<Transacao> trans;
    }

    private static class TxRepo {

        public static boolean update(Transacao tr) {
        }
    }
}
