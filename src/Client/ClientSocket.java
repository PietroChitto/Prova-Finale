package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Pietro on 16/05/2017.
 */
public class ClientSocket implements InterfacciaClient {
    Socket socket;
    String nickName;
    Scanner stdin;
    ObjectInputStream in;
    ObjectOutputStream out;
    public ClientSocket() throws IOException {
        try {
            socket=new Socket("localhost", 8001);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stdin = new Scanner(System.in);
        System.out.println("Client connesso alla porta 8001 attraverso socket1");
        startClient();
    }

    private void startClient() throws IOException {
        //CreaInterfaccia
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in= new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Segli un nickname");
        nickName=stdin.next();
        out.writeObject(new Messaggio("PARTECIPA"));
        out.flush();
        out.writeObject(new Messaggio(nickName));
        out.flush();


        //messaggio inizia partita
        //while true
    }
}
