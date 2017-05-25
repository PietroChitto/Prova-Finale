package server.socketServer;

import Client.InterfacciaClient;
import Client.Messaggio;
import partita.Partita;
import partita.carteDaGioco.CartaSviluppo;
import partita.componentiDelTabellone.Giocatore;
import server.GiocatoreRemoto;
import server.MosseGiocatore;
import server.Server;
import server.ServerInterface;
import server.rmiServer.InterfaciaRemotaRMI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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


    public GiocatoreSocket(Socket socket)
    {
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


    }



   @Override
   public InterfaciaRemotaRMI partecipaAPartita(String username, InterfacciaClient controller) throws RemoteException {
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

    }

    @Override
    public void scegliScomunica(boolean appoggiaChiesa) throws RemoteException {

    }

    @Override
    public void iniziaPartita(int mioId, ArrayList<String> carte, ArrayList<String> giocatori) {
        Messaggio messaggio=new Messaggio("INIZIOPARTITA");
        try {
            out.writeObject(messaggio);
            out.flush();
            out.writeInt(mioId);
            out.flush();
            System.out.println("messaggio inviato: "+mioId);
            for(String nomeCarta: carte){
                messaggio.setMessasggio(nomeCarta);
                System.out.println("messaggio inviato: "+nomeCarta);
                out.writeObject(messaggio);
                out.flush();
            }
            /*for (String giocatore: giocatori){
                messaggio.setMessasggio(giocatore);
                out.writeObject(messaggio);
                out.flush();
            }*/

            System.out.println("situazione iniziale inviata");

            //passare lista giocatori
            //passare lista carte
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void spostatoFamiliarePiano(int numeroTorre, int numeroPiano, String coloreDado, int idGiocatore) throws RemoteException {

    }

    class SocketHandler implements Runnable{

        private Messaggio messaggio;

        @Override
        public void run() {


            String comando;
            String tempString1;
            String tempString2;
            int tempInt1;

            while(true){
                try {
                    messaggio= (Messaggio) in.readObject();
                    comando=messaggio.getMessasggio();
                    if(comando.startsWith("SELEZIONA")){
                        messaggio=(Messaggio) in.readObject();
                        tempString1=messaggio.getMessasggio();
                        tempInt1=in.readInt();
                        mosseGiocatore.selezionaFamiliare(tempString1, tempInt1);
                    }
                    else if(comando.startsWith("DESELEZIONA")){

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }



            }

        }
    }
}
