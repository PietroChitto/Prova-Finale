package Client;

import Client.GUI.ControllerGioco;
import javafx.application.Platform;
import server.rmiServer.InterfaciaRemotaRMI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Pietro on 16/05/2017.
 */
public class ClientSocket implements InterfacciaClient, InterfaciaRemotaRMI{
    private Socket socket;
    private String nickName;
    private Scanner stdin;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String comando;
    private int id;
    private boolean partitatInCorso;
    private ControllerGioco controllerGioco;

    public ClientSocket(String userName, ControllerGioco controllerGioco) throws IOException {
        nickName=userName;
        try {
            socket=new Socket("localhost", 8001);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Client connesso alla porta 8001 attraverso socket1");
        this.controllerGioco=controllerGioco;
        this.controllerGioco.setClientGenerico(this);
        startClient(nickName);
    }

    private void startClient(String userName) throws IOException {
        System.out.println("dentro start client");
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.writeObject(new Messaggio("PARTECIPA"));
        out.flush();
        out.writeObject(new Messaggio(nickName));
        out.flush();
        System.out.println("parte il thread");
        new Thread(new ClientHandler()).start();
    }

    @Override
    public void iniziaPartita(int mioId, ArrayList<String> carte, ArrayList<String> giocatori, int[] risorse, ArrayList<String> scomuniche) throws RemoteException {

        HashMap<Integer,String > mappaGiocatori=new HashMap<>();
        int i=0;
        for (String g: giocatori){
            mappaGiocatori.put(i,g);
            i++;
        }
        Platform.runLater(()->{
            controllerGioco.inizializza(mappaGiocatori, carte,mioId, risorse, scomuniche);
        });

    }

    @Override
    public void spostatoFamiliarePiano(int numeroTorre, int numeroPiano, String coloreDado, int idGiocatore) throws RemoteException {
        Platform.runLater(()->{
            try {
                controllerGioco.spostatoFamiliarePiano(numeroTorre,numeroPiano,coloreDado,idGiocatore);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void dadiTirati(int ar, int ne, int bi) {
        Platform.runLater(()->{
            try {
                controllerGioco.dadiTirati(ar, ne, bi);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void messaggio(String s) throws RemoteException {
        System.out.println("modifico il server message");
        Platform.runLater(()->{
            try {
                controllerGioco.messaggio(s);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void forzaAumentata(String colore, int forza) throws RemoteException {

    }

    @Override
    public void risorseIncrementate(int pietra, int legna, int servitori, int monete, int puntiMilitari, int puntiFede, int puntiVittoria) throws RemoteException {

    }

    @Override
    public void spostatoFamiliareMercato(int zonaMercato, String coloreDado, int idGiocatore) throws RemoteException {
        Platform.runLater(()->{
            try {
                controllerGioco.spostatoFamiliareMercato(zonaMercato,coloreDado,idGiocatore);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
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

    @Override
    public void scegliPergamena() throws RemoteException {

    }

    @Override
    public void scegliScomunica() throws RemoteException {

    }

    @Override
    public void giocatoreScomunicato(int id) throws RemoteException {

    }

    //----------------------------------------------------------------------------------------------------------------------------------

    @Override
    public void selezionaFamiliare(String colore, int idGiocatore) throws RemoteException {
        try {
            out.writeObject("SELEZIONA");
            out.writeObject(colore);
            out.flush();
            out.writeObject(idGiocatore);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deselezionaFamiliare() throws RemoteException {
        try {
            out.writeObject("DESELEZIONA");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void spostaFamiliarePiano(int numeroTorre, int numeroPiano) throws RemoteException {
        try {
            out.writeObject("SPOSTAFAMILIAREPIANO");
            out.flush();
            out.writeObject(numeroTorre);
            out.flush();
            out.writeObject(numeroPiano);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void spostaFamiliareMercato(int zonaMercato) throws RemoteException {
        try {
            out.writeObject("SPOSTAFAMILIAREMERCATO");
            out.flush();
            out.writeObject(zonaMercato);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void spostaFamiliarePalazzoDelConsiglio() throws RemoteException {
        try {
            out.writeObject("SPOSTAPALAZZOCONSIGLIO");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            out.writeObject("TIRADADI");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sceltaScomunica(boolean appoggiaChiesa) throws RemoteException {

    }

    @Override
    public void aumentaForzaFamiliare(String coloreDado, int idGiocatore) throws RemoteException {
        try {
            out.writeObject("AUMENTAFORZAFAMILIARE");
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
    public void saltaMossa(int id) {

    }

    @Override
    public void sceltaPergamena(int scelta) throws RemoteException {

    }

    class ClientHandler implements Runnable{
        private String comando;
        private int idGiocatore;
        private int numeroTorre;
        private int numeroPiano;
        private int zonaMercato;
        private String coloreDado;
        private String messaggio;
        private boolean run=true;


        @Override
        public void run() {
            System.out.println("thread partito");

            while (run){
                try {
                    comando=(String) in.readObject();


                    if(comando.equals("INIZIOPARTITA")){
                        System.out.println("messaggio ricevuto: INIZIOPARTITA");
                        ArrayList<String> carte;
                        ArrayList<String> giocatori;
                        ArrayList<String> scomuniche;
                        int[] risorse;
                        id=in.readInt();
                        System.out.println("il mio id: "+ id);
                        carte=(ArrayList<String>) in.readObject();
                        giocatori=(ArrayList<String>) in.readObject();
                        risorse=(int[]) in.readObject();
                        scomuniche=(ArrayList<String>) in.readObject();

                        System.out.println("situazione iniziale inviata");

                        iniziaPartita(id,carte,giocatori, risorse,scomuniche);
                    }
                    else if(comando.startsWith("MESSAGGIO")){
                        messaggio=(String) in.readObject();
                        messaggio(messaggio);
                    }
                    else if(comando.startsWith("DADITIRATI")){
                        System.out.println("messaggio ricevuto: DADITIRATI");
                        int ar, ne, bi;
                        ar=(int)in.readObject();
                        ne=(int)in.readObject();
                        bi=(int)in.readObject();
                        System.out.println(ar+""+ne+""+bi);
                        dadiTirati(ar,ne,bi);
                    }
                    else if(comando.startsWith("SPOSTATOFAMILIAREPIANO")){
                        numeroTorre=(int) in.readObject();
                        numeroPiano=(int) in.readObject();
                        coloreDado=(String) in.readObject();
                        idGiocatore=(int) in.readObject();
                        System.out.println("id giocatore arrivato (socket): "+idGiocatore);
                        spostatoFamiliarePiano(numeroTorre,numeroPiano,coloreDado,idGiocatore);
                    }
                    else if(comando.startsWith("SPOSTATOFAMILIAREMERCATO")){
                        zonaMercato=(int) in.readObject();
                        coloreDado=(String) in.readObject();
                        idGiocatore=(int) in.readObject();

                        spostatoFamiliareMercato(zonaMercato,coloreDado,idGiocatore);
                    }
                    else if(comando.startsWith("SPOSTATOPALAZZOCONSIGLIO")){
                        coloreDado=(String) in.readObject();
                        idGiocatore=(int) in.readObject();
                        controllerGioco.spostatoFamiliarePalazzoDelConsiglio(coloreDado, idGiocatore);
                    }
                }
                catch (SocketException e){
                    try {
                        in.close();
                        out.close();
                        System.out.println("stream chiusi");
                        stop();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }




        }

        private void stop() {
            run=false;
        }
    }
}
