package server;

import Client.InterfacciaClient;
import partita.Partita;
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
    private static final int RMI_PORT=8008;
    private static final int SOCKET_PORT=8009;
    //mappa di liste, la chiave è il numero di giocatori della partita, l'oggetto è la lista dei giocatori in attesa per quella partita
    public  static HashMap<Integer,ArrayList<GiocatoreRemoto>> giocatori;
    public static Partita partita;
    private SocketServer socketServer;
    private RMIServer rmiServer;
    private static final Object PLAYERS_MUTEX = new Object();

    public Server() throws NetworkException, RemoteException {
        giocatori = new HashMap<>();
        ArrayList<GiocatoreRemoto> lista2G=new ArrayList<>();
        ArrayList<GiocatoreRemoto> lista3G=new ArrayList<>();
        ArrayList<GiocatoreRemoto> lista4G=new ArrayList<>();
        giocatori.put(2,lista2G);
        giocatori.put(3,lista3G);
        giocatori.put(4,lista4G);

        partita=null;
        socketServer=new SocketServer(/*this*/);
        rmiServer = new RMIServer(/*this*/);
    }

     public static void main(String[] args) throws ServerException {

         System.out.print("server lanciati");
        try {
            Server server = new Server();
            server.startServer(SOCKET_PORT, RMI_PORT);
        } catch (NetworkException e) {
        } catch (RemoteException e) {
            e.printStackTrace();
        }
     }

    private void startServer(int socketPort, int rmiPort) throws NetworkException, ServerException {

        System.out.print("lancio i server");
        socketServer.startServer(SOCKET_PORT);
        rmiServer.startServer(RMI_PORT);
        System.out.print("server lanciati");
    }

    @Override
    public InterfaciaRemotaRMI partecipaAPartita(String username, InterfacciaClient controller, int numeroGiocatori) throws RemoteException {
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
