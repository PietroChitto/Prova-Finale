package server;

import partita.Partita;
import server.rmiServer.InterfaciaServer;
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
    private final SocketServer socketServer;
    private final RMIServer rmiServer;

    public Server() throws NetworkException, RemoteException {
        giocatori = new HashMap<>();
        ArrayList<GiocatoreRemoto> lista2G=new ArrayList<>();
        ArrayList<GiocatoreRemoto> lista3G=new ArrayList<>();
        ArrayList<GiocatoreRemoto> lista4G=new ArrayList<>();
        giocatori.put(2,lista2G);
        giocatori.put(3,lista3G);
        giocatori.put(4,lista4G);

        partita=null;
        socketServer=new SocketServer();
        rmiServer = new RMIServer();
    }

     public static void main(String[] args) throws ServerException {

        System.out.print("server lanciato");
        try {
            Server server = new Server();
            server.startServer(SOCKET_PORT, RMI_PORT);
        } catch (RemoteException | NetworkException e) {
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
    public InterfaciaServer partecipaAPartita(String username, InterfacciaClient controller, int numeroGiocatori) throws RemoteException {
        return null;
    }
}
