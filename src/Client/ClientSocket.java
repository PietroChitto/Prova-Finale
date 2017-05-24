package Client;

import Client.GUI.ControllerLogin;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
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

    public ClientSocket(String userName) throws IOException {
        nickName=userName;
        try {
            socket=new Socket("localhost", 8001);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stdin = new Scanner(System.in);
        System.out.println("Client connesso alla porta 8001 attraverso socket1");
        startClient(nickName);
    }

    private void startClient(String userName) throws IOException {

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
    }



        //attendo il messaggio di inzio partita
        /*try {
            messaggio=(Messaggio) in.readObject();
            comando=messaggio.getMessasggio();
            System.out.println("messaggio ricevuto: "+comando);
            int mioId;
            mioId=in.readInt();
            iniziaPartita(mioId);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public void iniziaPartita(int mioId) throws RemoteException {
        this.id=mioId;
        partitatInCorso=true;
        System.out.println("sono "+nickName+" con id "+id);
        //faccio partire il thread cherimane in ascolto delle risposte del server
        new Thread(new ClientHandler()).start();
    }

    @Override
    public void spostatoFamiliarePiano(int numeroTorre, int numeroPiano, String coloreDado, int idGiocatore) throws RemoteException {

    }

    class ClientHandler implements Runnable{


        @Override
        public void run() {


        }
    }
}
