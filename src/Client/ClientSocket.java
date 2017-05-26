package Client;

import Client.GUI.ControllerGioco;
import Client.GUI.ControllerLogin;
import javafx.application.Platform;
import partita.componentiDelTabellone.Giocatore;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Pietro on 16/05/2017.
 */
public class ClientSocket implements InterfacciaClient, ClientGenerico{
    private Socket socket;
    private String nickName;
    private Scanner stdin;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Messaggio messaggio;
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
    public void iniziaPartita(int mioId, ArrayList<String> carte, ArrayList<String> giocatori) throws RemoteException {

        HashMap<Integer,String > mappaGiocatori=new HashMap<>();
        int i=0;
        for (String g: giocatori){
            mappaGiocatori.put(i,g);
            i++;
        }
        Platform.runLater(()->{
            controllerGioco.inizializza(mappaGiocatori, carte,mioId);
        });

    }

    @Override
    public void spostatoFamiliarePiano(int numeroTorre, int numeroPiano, String coloreDado, int idGiocatore) throws RemoteException {

    }

    class ClientHandler implements Runnable{
        private Messaggio messaggio;
        private String comando;
        private Messaggio m;


        @Override
        public void run() {
            System.out.println("thread partito");

            while (true){
                try {
                    messaggio=(Messaggio) in.readObject();

                    if(messaggio.getMessasggio().equals("INIZIOPARTITA")){
                        System.out.println("messaggio ricevuto: INIZIOPARTITA");
                        ArrayList<String> carte;
                        ArrayList<String> giocatori;
                        id=in.readInt();
                        System.out.println("il mio id: "+ id);
                        carte=(ArrayList<String>) in.readObject();
                        giocatori=(ArrayList<String>) in.readObject();

                        System.out.println("situazione iniziale inviata");

                        iniziaPartita(id,carte,giocatori);



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
