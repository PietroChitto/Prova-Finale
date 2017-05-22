package server;

import partita.Partita;
import partita.componentiDelTabellone.Familiare;
import server.rmiServer.InterfaciaRemotaRMI;
import server.rmiServer.RMIServer;
import server.socketServer.SocketServer;

import java.rmi.RemoteException;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Pietro on 16/05/2017.
 */
public class Server implements ServerInterface {
    private static final int RMI_PORT=8000;
    private static final int SOCKET_PORT=8001;
    public  static ArrayList<GiocatoreRemoto> giocatori;
    public static Partita partita;
    private SocketServer socketServer;
    private RMIServer rmiServer;
    private static final Object PLAYERS_MUTEX = new Object();

    public Server() throws NetworkException, RemoteException {
        giocatori = new ArrayList<GiocatoreRemoto>();
        partita=null;
        socketServer=new SocketServer(/*this*/);
        rmiServer = new RMIServer(/*this*/);
    }

     public static void main(String[] args) throws ServerException {
        try {
            Server server = new Server();
            server.startServer(SOCKET_PORT, RMI_PORT);
        } catch (NetworkException e) {
        } catch (RemoteException e) {
            e.printStackTrace();
        }
     }

    private void startServer(int socketPort, int rmiPort) throws NetworkException, ServerException {
        socketServer.startServer(SOCKET_PORT);
        rmiServer.startServer(RMI_PORT);
        System.out.print("server lanciati");
    }

    @Override
    public InterfaciaRemotaRMI partecipaAPartita(String username) throws RemoteException {
        return null;
    }
/*
    @Override
    public void creaPartita(int code) throws RuntimeException {

    }

    @Override
    public void login(String id, GiocatoreRemoto giocatoreRemoto) throws RuntimeException {
        synchronized (PLAYERS_MUTEX) {
            if (!giocatori.containsKey(id)) {
                giocatori.put(id, giocatoreRemoto);
                giocatoreRemoto.setNome(id);
            } else {
                System.out.print("Giocatore già presente");
            }
        }
    }*/
}
