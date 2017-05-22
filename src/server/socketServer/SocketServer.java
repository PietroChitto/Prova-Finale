package server.socketServer;

import server.*;
import server.rmiServer.InterfaciaRemotaRMI;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * Created by Pietro on 16/05/2017.
 */
public class SocketServer implements ServerInterface{

    private ServerSocket serverSocket;
    public SocketServer(/*ServerInterface controller*/) {
       // super(/*controller*/);

    }

    /*@Override*/
    public void startServer(int port) throws NetworkException {
        try {
            serverSocket = new ServerSocket(port);
            new RequestHandler().start();
        } catch (IOException e) {
            throw new NetworkException("");
        }

    }

    @Override
    public InterfaciaRemotaRMI partecipaAPartita(String username) throws RemoteException {
        return null;
    }

    private class RequestHandler extends Thread{

        /**
         * Loop that listen for new clients and initialize their handlers.
         */
        @Override
        public void run() {
            while (true) try {
                Socket socket = serverSocket.accept();
                GiocatoreSocket giocatoreSocket = new GiocatoreSocket(socket);
                new Thread(giocatoreSocket).start();


            } catch (IOException e) {
                break;
            }
        }
    }

}