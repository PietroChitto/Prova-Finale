package server.socketServer;

import Client.InterfacciaClient;
import Client.Messaggio;
import partita.Partita;
import partita.componentiDelTabellone.Giocatore;
import partita.eccezioniPartita.DadiNonTiratiException;
import partita.eccezioniPartita.TurnoException;
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

    }

    @Override
    public void spostaFamiliareZonaRaccolto(int zona) throws RemoteException {

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
    public void scegliScomunica(boolean appoggiaChiesa) throws RemoteException {

    }

    @Override
    public void aumentaForzaFamiliare(String coloreDado, int id) throws RemoteException {

    }

    @Override
    public void saltaMossa(int id) {

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
    public void forzaAumentata(String colore, int forza) throws RemoteException {

    }

    @Override
    public void risorseIncrementate(int pietra, int legna, int servitori, int monete, int puntiMilitari, int puntiFede, int puntiVittoria) throws RemoteException {

    }

    @Override
    public void spostatoFamiliareMercato(int zonaMercato, String coloreDado, int id) throws RemoteException {
        
    }

    @Override
    public void spostatoFamiliarePalazzoDelConsiglio(String coloreDado, int id) throws RemoteException {

    }

    @Override
    public void spostatoFamiliareZonaProduzione(String coloreDado, int id, int zona) throws RemoteException {

    }

    @Override
    public void spostatoFamiliareZonaRaccolto(String coloreDado, int id, int zona) throws RemoteException {

    }

    @Override
    public void mossaSaltata(int id) {

    }

    @Override
    public void avvisoInizioTurno(ArrayList<String> nomiCarte) {

    }

    class SocketHandler implements Runnable{

        private Messaggio messaggio;
        boolean run=true;

        @Override
        public void run() {

            System.out.println("thread socket partito");
            String comando;
            String tempString1;
            int tempInt1;

            while(run){
                try {
                    comando=(String) in.readObject();
                    if(comando.startsWith("TIRADADI")){
                        System.out.println("messaggio ricevuto: TIRADADI");
                       tiraIDadi();
                    }
                    else if(comando.startsWith("SELEZIONA")){
                        messaggio=(Messaggio) in.readObject();
                        tempString1=messaggio.getMessasggio();
                        tempInt1=in.readInt();
                        mosseGiocatore.selezionaFamiliare(tempString1, tempInt1);
                    }
                    else if(comando.startsWith("DESELEZIONA")){

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
                    e.printStackTrace();
                } catch (DadiNonTiratiException e) {
                    e.printStackTrace();
                }


            }

        }

        private void stop() {
            run=false;
        }
    }
}
