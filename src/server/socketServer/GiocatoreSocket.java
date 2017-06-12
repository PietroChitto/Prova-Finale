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

/**
 * Created by Pietro on 16/05/2017.
 */
 class GiocatoreSocket extends GiocatoreRemoto implements Runnable, ServerInterface{

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String nickName;
    private MosseGiocatore mosseGiocatore;


    public GiocatoreSocket(Socket socket) throws RemoteException {
        super();
        this.socket=socket;
      mosseGiocatore=new MosseGiocatore(this);
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
             System.out.println(nickName+" connesso");
             partecipaAPartita(nickName, null);
          }

       } catch (IOException e) {
          e.printStackTrace();
       } catch (ClassNotFoundException e) {
          e.printStackTrace();
       }

       new Thread(new SocketHandler()).start();


    }



   @Override
   public InterfaciaRemotaRMI partecipaAPartita(String username, InterfacciaClient controller) throws RemoteException {
      Server.giocatori.add(this);
      this.setUsername(nickName);
      System.out.println("dentro metodo sk "+Server.giocatori.size()+" connessi");
      if(Server.giocatori.size()==Partita.N_GIOCATORI){
            System.out.println("sono nell'if");
            Partita p=new Partita();
            System.out.println("partitaCreata, aggiungo i giocatori");
            for(int i=0; i<Partita.N_GIOCATORI; i++) {
               Giocatore modelloGiocatore=new Giocatore(i);
               Server.giocatori.get(i).setGiocatore(modelloGiocatore);
               p.addGiocatore(Server.giocatori.get(i));
               System.out.println("aggiunto giocatore"+i);
               Server.giocatori.get(i).setPartita(p);
            }
            p.iniziaPartita();
            Server.giocatori.clear();
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
    public void spostaFamiliarePiano(int numeroTorre, int numeroPiano) throws RemoteException {

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

    }

    @Override
    public void aumentaForzaFamiliare(String coloreDado, int id) throws RemoteException {

    }

    @Override
    public void saltaMossa(int id) {

    }

    @Override
    public void sceltaPergamena(int scelta) throws RemoteException {

    }

    //-----------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void iniziaPartita(int mioId, ArrayList<String> carte, ArrayList<String> giocatori, int[] risorse, ArrayList<String> scomuniche) {
        try {
            out.writeObject("INIZIOPARTITA");
            out.flush();
            out.writeInt(mioId);
            out.flush();
            System.out.println("messaggio inviato: "+mioId);
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

    @Override
    public void spostatoFamiliarePiano(int numeroTorre, int numeroPiano, String coloreDado, int idGiocatore) throws RemoteException {
        try {
            out.writeObject("SPOSTATOFAMILIAREPIANO");
            out.flush();
            out.writeObject(numeroTorre);
            out.flush();
            out.writeObject(numeroPiano);
            out.flush();
            out.writeObject(coloreDado);
            out.flush();
            System.out.println("id familiare spostato (socket): "+idGiocatore);
            out.writeObject(idGiocatore);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dadiTirati(int ar, int ne, int bi) throws IOException {

        out.writeObject("DADITIRATI");
        out.flush();
        out.writeObject(ar);
        out.flush();
        out.writeObject(ne);
        out.flush();
        out.writeObject(bi);
        out.flush();
    }

    @Override
    public void messaggio(String s) throws RemoteException {

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

    }

    @Override
    public void scegliPergamena() throws RemoteException {

    }

    @Override
    public void scegliScomunica() throws RemoteException {

    }

    @Override
    public void giocatoreScomunicato(int id) throws RemoteException {

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
            int numeroTorre;
            int numeroPiano;
            int zonaMercato;

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
                        try {
                            mosseGiocatore.spostaFamiliarePiano(numeroTorre,numeroPiano);
                        } catch (FamiliareNonSelezionatoExcepion familiareNonSelezionatoExcepion) {
                            out.writeObject("MESSAGGIO");
                            out.flush();
                            out.writeObject("Familiare non selezionato");
                            out.flush();
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
                        } catch (RisorseInsufficientiException e) {
                            out.writeObject("MESSAGGIO");
                            out.flush();
                            out.writeObject("Risorse insufficienti");
                            out.flush();
                        } catch (TorreOccupataException e) {
                            out.writeObject("MESSAGGIO");
                            out.flush();
                            out.writeObject("Hai già un familiare sulla torre");
                            out.flush();
                        }
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
