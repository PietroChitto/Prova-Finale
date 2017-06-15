package server.socketServer;

import Client.InterfacciaClient;
import Client.Messaggio;
import partita.Partita;
import partita.componentiDelTabellone.Giocatore;
import partita.eccezioniPartita.*;
import server.GiocatoreRemoto;
import server.MosseGiocatore;
import server.Server;
import server.ServerInterface;
import server.rmiServer.InterfaciaRemotaRMI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Pietro on 16/05/2017.
 */
 public class GiocatoreSocket extends GiocatoreRemoto implements Runnable, ServerInterface{

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String nickName;
    private MosseGiocatore mosseGiocatore;
    private int numeroGiocatori;


    public GiocatoreSocket(Socket socket) throws RemoteException {
        super();
        this.socket=socket;
        mosseGiocatore=new MosseGiocatore(this);
        super.setMosse(mosseGiocatore);
    }


    @Override
    public void run() {
       try {

          in = new ObjectInputStream(socket.getInputStream());
          out = new ObjectOutputStream(socket.getOutputStream());
          System.out.println("Stream cerati");
          Messaggio messaggio;
          messaggio =(Messaggio) in.readObject();
          System.out.println("messaggio letto:"+messaggio.getMessasggio());

          if (messaggio.getMessasggio().equals("PARTECIPA")) {
             messaggio= (Messaggio) in.readObject();
             nickName=messaggio.getMessasggio();
             numeroGiocatori=(int) in.readObject();
             System.out.println(nickName+" connesso");
             partecipaAPartita(nickName, null,numeroGiocatori);
          }

       } catch (IOException e) {
          e.printStackTrace();
       } catch (ClassNotFoundException e) {
          e.printStackTrace();
       }

       new Thread(new SocketHandler()).start();


    }



   @Override
   public synchronized InterfaciaRemotaRMI partecipaAPartita(String username, InterfacciaClient controller, int numeroGiocatori) throws RemoteException {
      Server.giocatori.get(numeroGiocatori).add(this);
      this.setUsername(nickName);
      System.out.println("dentro metodo sk "+Server.giocatori.size()+" connessi");
      if(Server.giocatori.get(numeroGiocatori).size()==numeroGiocatori){
            System.out.println("sono nell'if");
            Partita p=new Partita(numeroGiocatori);
            System.out.println("partitaCreata, aggiungo i giocatori");
            for(int i=0; i<Partita.N_GIOCATORI; i++) {
               Giocatore modelloGiocatore=new Giocatore(i);
               Server.giocatori.get(numeroGiocatori).get(i).setGiocatore(modelloGiocatore);
               p.addGiocatore(Server.giocatori.get(numeroGiocatori).get(i));
               System.out.println("aggiunto giocatore"+i);
               Server.giocatori.get(numeroGiocatori).get(i).setPartita(p);
            }
            p.iniziaPartita();
            Server.giocatori.get(numeroGiocatori).clear();
            System.out.print("svuoto lista giocatori");
      }
         return null;
   }

    @Override
    public void selezionaFamiliare(String colore, int idGiocatore) throws RemoteException {

    }

    @Override
    public void deselezionaFamiliare() throws RemoteException {

    }

    @Override
    public void spostaFamiliarePiano(int numeroTorre, int numeroPiano) throws IOException {
        try {
            mosseGiocatore.spostaFamiliarePiano(numeroTorre,numeroPiano);
        } catch (FamiliareNonSelezionatoExcepion familiareNonSelezionatoExcepion) {
            out.writeObject("MESSAGGIO");
            out.flush();
            out.writeObject("Familiare non selezionato");
            out.flush();
        } catch (ForzaInsufficienteException e) {
            //se la torre era occupata il giocatore aveva speso 3 monete, siccome la mossa non è andata a buon fine le restituisco
            if(getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).isOccupata())
                getGiocatore().setMonete(getGiocatore().getMonete()+3);

            out.writeObject("MESSAGGIO");
            out.flush();
            out.writeObject("Forza del familiare insufficiente");
            out.flush();
        } catch (ZonaOccupataExcepion zonaOccupataExcepion) {
            //se la torre era occupata il giocatore aveva speso 3 monete, siccome la mossa non è andata a buon fine le restituisco
            if(getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).isOccupata())
                getGiocatore().setMonete(getGiocatore().getMonete()+3);

            out.writeObject("MESSAGGIO");
            out.flush();
            out.writeObject("La zona è già occupata");
            out.flush();
        } catch (RisorseInsufficientiException e) {
            //se la torre era occupata il giocatore aveva speso 3 monete, siccome la mossa non è andata a buon fine le restituisco
            if(getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).isOccupata())
                getGiocatore().setMonete(getGiocatore().getMonete()+3);

            out.writeObject("MESSAGGIO");
            out.flush();
            out.writeObject("Risorse insufficienti");
            out.flush();
        } catch (TorreOccupataException e) {
            out.writeObject("MESSAGGIO");
            out.flush();
            out.writeObject("Hai già un familiare sulla torre");
            out.flush();
        } catch (TurnoException e) {
            out.writeObject("MESSAGGIO");
            out.flush();
            out.writeObject("Nonè il tuo turno");
            out.flush();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void spostaFamiliareMercato(int zonaMercato) throws RemoteException {

    }

    @Override
    public void spostaFamiliarePalazzoDelConsiglio() throws RemoteException {

    }

    @Override
    public void spostaFamiliareZonaProduzione(int zona) throws RemoteException {
        try {
            mosseGiocatore.spostaFamiliareZonaProduzione(zona);
        } catch (ForzaInsufficienteException e) {
            try {
                out.writeObject("MESSAGGIO");
                out.flush();
                out.writeObject("Forza Insufficiente");
                out.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } catch (TurnoException e) {
            try {
                out.writeObject("MESSAGGIO");
                out.flush();
                out.writeObject("Non è il tuo turno");
                out.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (ZonaOccupataExcepion zonaOccupataExcepion) {
            try {
                out.writeObject("MESSAGGIO");
                out.flush();
                out.writeObject("Zona già occupata");
                out.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (DadiNonTiratiException e) {
            try {
                out.writeObject("MESSAGGIO");
                out.flush();
                out.writeObject("Zona già occupata");
                out.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } catch (FamiliareNonSelezionatoExcepion familiareNonSelezionatoExcepion) {
            try {
                out.writeObject("MESSAGGIO");
                out.flush();
                out.writeObject("Familiare non selezionato");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void spostaFamiliareZonaRaccolto(int zona) throws RemoteException {
        try {
            mosseGiocatore.spostaFamiliareZonaRaccolto(zona);
        } catch (ForzaInsufficienteException e) {
            try {
                out.writeObject("MESSAGGIO");
                out.flush();
                out.writeObject("Forza Insufficiente");
                out.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } catch (TurnoException e) {
            try {
                out.writeObject("MESSAGGIO");
                out.flush();
                out.writeObject("Non è il tuo turno");
                out.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (ZonaOccupataExcepion zonaOccupataExcepion) {
            try {
                out.writeObject("MESSAGGIO");
                out.flush();
                out.writeObject("Zona già occupata");
                out.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (FamiliareNonSelezionatoExcepion familiareNonSelezionatoExcepion) {
            try {
                out.writeObject("MESSAGGIO");
                out.flush();
                out.writeObject("Familiare non selezionato");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (DadiNonTiratiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tiraIDadi() throws RemoteException {
        try {
            mosseGiocatore.tiraIDadi();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sceltaScomunica(boolean appoggiaChiesa) throws RemoteException {
        mosseGiocatore.sceltaScomunica(appoggiaChiesa);
    }

    @Override
    public void aumentaForzaFamiliare(String coloreDado, int id) throws RemoteException {

    }

    @Override
    public void saltaMossa(int id) {

    }

    @Override
    public void sceltaPergamena(int scelta) throws RemoteException {
        mosseGiocatore.sceltaPergamena(scelta);
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void iniziaPartita(int mioId, ArrayList<String> carte, ArrayList<String> giocatori, int[] risorse, ArrayList<String> scomuniche) {
        if(out!=null) {
            try {
                out.writeObject("INIZIOPARTITA");
                out.flush();
                out.writeInt(mioId);
                out.flush();
                System.out.println("messaggio inviato: " + mioId);
                out.writeObject(carte);
                out.flush();
                out.writeObject(giocatori);
                out.flush();
                out.writeObject(risorse);
                out.flush();
                out.writeObject(scomuniche);
                out.flush();
                System.out.println("situazione iniziale inviata");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void spostatoFamiliarePiano(int numeroTorre, int numeroPiano, String coloreDado, int idGiocatore) throws RemoteException {
        try {
            if(out!=null) {
                out.writeObject("SPOSTATOFAMILIAREPIANO");
                out.flush();
                out.writeObject(numeroTorre);
                out.flush();
                out.writeObject(numeroPiano);
                out.flush();
                out.writeObject(coloreDado);
                out.flush();
                System.out.println("id familiare spostato (socket): " + idGiocatore);
                out.writeObject(idGiocatore);
                out.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dadiTirati(int ar, int ne, int bi) throws IOException {
        if(out!=null) {
            out.writeObject("DADITIRATI");
            out.flush();
            out.writeObject(ar);
            out.flush();
            out.writeObject(ne);
            out.flush();
            out.writeObject(bi);
            out.flush();
        }
    }

    @Override
    public void messaggio(String s) throws RemoteException {
        if(out!=null) {
            try {
                out.writeObject("MESSAGGIO");
                out.flush();
                out.writeObject(s);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void forzaAumentata(String colore, int forza){
        try {
            out.writeObject("FORZAAUMENTATA");
            out.flush();
            out.writeObject(colore);
            out.flush();
            out.writeObject(forza);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void risorseIncrementate(int pietra, int legna, int servitori, int monete, int puntiMilitari, int puntiFede, int puntiVittoria) throws RemoteException {
        try {
            out.writeObject("RISORSEINCREMENTATE");
            out.flush();
            out.writeObject(pietra);
            out.flush();
            out.writeObject(legna);
            out.flush();
            out.writeObject(servitori);
            out.flush();
            out.writeObject(monete);
            out.flush();
            out.writeObject(puntiMilitari);
            out.flush();
            out.writeObject(puntiFede);
            out.flush();
            out.writeObject(puntiVittoria);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void spostatoFamiliareMercato(int zonaMercato, String coloreDado, int idGiocatore) throws RemoteException {
        try {
            out.writeObject("SPOSTATOFAMILIAREMERCATO");
            out.flush();
            out.writeObject(zonaMercato);
            out.flush();
            out.writeObject(coloreDado);
            out.flush();
            out.writeObject(idGiocatore);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void spostatoFamiliarePalazzoDelConsiglio(String coloreDado, int idGiocatore) throws RemoteException {
        try {
            out.writeObject("SPOSTATOPALAZZOCONSIGLIO");
            out.writeObject(coloreDado);
            out.flush();
            out.writeObject(idGiocatore);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void spostatoFamiliareZonaProduzione(String coloreDado, int id, int zona) throws RemoteException {
        try {
            System.out.println("invio risposta zona produzione");
            out.writeObject("SPOSTATOPRODUZIONE");
            out.flush();
            out.writeObject(coloreDado);
            out.flush();
            out.writeObject(id);
            out.flush();
            out.writeObject(zona);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void spostatoFamiliareZonaRaccolto(String coloreDado, int id, int zona) throws RemoteException {
        try {
            out.writeObject("SPOSTATORACCOLTO");
            out.flush();
            out.writeObject(coloreDado);
            out.flush();
            out.writeObject(id);
            out.flush();
            out.writeObject(zona);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mossaSaltata(int id) {

    }

    @Override
    public void avvisoInizioTurno(ArrayList<String> nomiCarte) {
        if(out!=null) {
            try {
                out.writeObject("AVVISOINIZIOTURNO");
                out.flush();
                out.writeObject(nomiCarte);
                out.flush();
                System.out.println("(Socket) mando inizio turno");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void scegliPergamena() throws RemoteException {
        try {
            out.writeObject("SCEGLIPERGAMENA");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void scegliScomunica() throws RemoteException {
        try {
            out.writeObject("SCEGLISCOMUNICA");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void giocatoreScomunicato(int idGiocatore, int periodo) throws RemoteException {
        if(out!=null) {
            try {
                out.writeObject("GIOCATORESCOMUNICATO");
                out.flush();
                out.writeObject(idGiocatore);
                out.flush();
                out.writeObject(periodo);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void finePartita(HashMap<String,Integer> classifica) throws RemoteException {
        if(out!=null) {
            try {
                out.writeObject("FINEPARTITA");
                out.flush();
                out.writeObject(classifica);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class SocketHandler implements Runnable{

        private Messaggio messaggio;
        boolean run=true;

        @Override
        public void run() {

            System.out.println("thread socket partito");
            String comando;
            String coloreDado;
            int id;
            int scelta;
            int numeroTorre;
            int numeroPiano;
            int zonaMercato;
            boolean bool;

            while(run){
                try {
                    comando=(String) in.readObject();
                    if(comando.startsWith("TIRADADI")){
                        System.out.println("messaggio ricevuto: TIRADADI");
                       tiraIDadi();
                    }
                    else if(comando.startsWith("SELEZIONA")){
                        coloreDado=(String) in.readObject();
                        id=(int) in.readObject();
                        mosseGiocatore.selezionaFamiliare(coloreDado, id);
                        System.out.println("familiare selezionato: ");
                    }
                    else if(comando.startsWith("DESELEZIONA")){
                        mosseGiocatore.deselezionaFamiliare();
                    }
                    else if(comando.startsWith("SPOSTAFAMILIAREPIANO")){
                        numeroTorre=(int) in.readObject();
                        numeroPiano=(int) in.readObject();
                        spostaFamiliarePiano(numeroTorre,numeroPiano);

                    }
                    else if (comando.startsWith("SPOSTAFAMILIAREMERCATO")){
                        zonaMercato=(int) in.readObject();
                        try {
                            mosseGiocatore.spostaFamiliareMercato(zonaMercato);
                        } catch (ForzaInsufficienteException e) {
                            out.writeObject("MESSAGGIO");
                            out.flush();
                            out.writeObject("Forza del familiare insufficiente");
                            out.flush();
                        } catch (ZonaOccupataExcepion zonaOccupataExcepion) {
                            out.writeObject("MESSAGGIO");
                            out.flush();
                            out.writeObject("La zona è già occupata");
                            out.flush();
                        } catch (FamiliareNonSelezionatoExcepion familiareNonSelezionatoExcepion) {
                            out.writeObject("MESSAGGIO");
                            out.flush();
                            out.writeObject("familiare non selezioinato");
                            out.flush();
                        }
                    }
                    else if(comando.startsWith("SPOSTAPALAZZOCONSIGLIO")){

                        try {
                            mosseGiocatore.spostaFamiliarePalazzoDelConsiglio();
                        } catch (ForzaInsufficienteException e) {
                            out.writeObject("MESSAGGIO");
                            out.flush();
                            out.writeObject("Forza del familiare insufficiente");
                            out.flush();
                        } catch (ZonaOccupataExcepion zonaOccupataExcepion) {
                            out.writeObject("MESSAGGIO");
                            out.flush();
                            out.writeObject("La zona è già occupata");
                            out.flush();
                        } catch (FamiliareNonSelezionatoExcepion familiareNonSelezionatoExcepion) {
                            out.writeObject("MESSAGGIO");
                            out.flush();
                            out.writeObject("Familiare non selezionato");
                            out.flush();
                        }
                    }
                    else if(comando.startsWith("AUMENTAFORZAFAMILIARE")){
                        coloreDado=(String) in.readObject();
                        id=(int) in.readObject();
                            mosseGiocatore.aumentaForzaFamiliare(coloreDado,id);
                    }
                    else if(comando.startsWith("SALTAMOSSA")){
                        id=(int) in.readObject();
                        mosseGiocatore.saltaMossa(id);
                        out.writeObject("MESSAGGIO");
                        out.flush();
                        out.writeObject("Mossa Saltata");
                    }
                    else if(comando.startsWith("SPOSTAZONAPRODUZIONE")){
                        zonaMercato=(int) in.readObject();
                        spostaFamiliareZonaProduzione(zonaMercato);
                    }
                    else if(comando.startsWith("SPOSTAZONARACCOLTO")){
                        zonaMercato=(int) in.readObject();
                        spostaFamiliareZonaRaccolto(zonaMercato);
                    }
                    else if(comando.startsWith("SCELTASCOMUNICA")){
                        bool=(boolean) in.readObject();
                        sceltaScomunica(bool);
                    }
                    else if(comando.startsWith("SCELTAPERGAMENA")){
                        scelta=(int) in.readObject();
                        sceltaPergamena(scelta);
                    }
                } catch (SocketException e){
                    try {
                        in.close();
                        out.close();
                        System.out.println("chiudo gli stream");
                        stop();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } catch (IOException e) {

                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (TurnoException e) {
                    try {
                        out.writeObject("MESSAGGIO");
                        out.flush();
                        out.writeObject("Non è il tuo turno");
                        out.flush();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } catch (DadiNonTiratiException e) {
                    try {
                        out.writeObject("MESSAGGIO");
                        out.flush();
                        out.writeObject("Dadi non tirati");
                        out.flush();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                }


            }

        }

        private void stop() {
            run=false;
        }
    }
}
