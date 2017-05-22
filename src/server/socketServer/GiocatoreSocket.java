package server.socketServer;

import Client.Messaggio;
import partita.Partita;
import partita.componentiDelTabellone.Giocatore;
import server.GiocatoreRemoto;
import server.Server;
import server.ServerInterface;
import server.rmiServer.InterfaciaRemotaRMI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * Created by Pietro on 16/05/2017.
 */
 class GiocatoreSocket extends GiocatoreRemoto implements Runnable, InterfaciaRemotaRMI, ServerInterface {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Partita partita;
    private String nickName;

    public GiocatoreSocket(Socket socket)
    {
      this.socket=socket;
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
             partecipaAPartita(nickName);
          }

       } catch (IOException e) {
          e.printStackTrace();
       } catch (ClassNotFoundException e) {
          e.printStackTrace();
       }


    }



   @Override
   public InterfaciaRemotaRMI partecipaAPartita(String username) throws RemoteException {
      Server.giocatori.add(this);
      this.setUsername(nickName);
      System.out.println("dentro metodo sk "+Server.giocatori.size()+" connessi");
      if(Server.giocatori.size()==4){
            System.out.println("sono nell'if");
            Partita p=new Partita();
            System.out.println("partitaCreata, aggiungo i giocatori");
            for(int i=0; i<4; i++) {
               Giocatore modelloGiocatore=new Giocatore(i);
               Server.giocatori.get(i).setGiocatore(modelloGiocatore);
               p.addGiocatore(Server.giocatori.get(i));
               System.out.println("aggiunto giocatore"+i);
               Server.giocatori.get(i).setPartita(p);
            }
            p.iniziaPartita();
            Server.giocatori.clear();
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

    }

    @Override
    public void scegliScomunica(boolean appoggiaChiesa) throws RemoteException {

    }
}
