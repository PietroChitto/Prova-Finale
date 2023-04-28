package server;

import server.rmiServer.InterfaciaServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Pietro on 16/05/2017.
 */
public interface ServerInterface extends Remote {
    InterfaciaServer partecipaAPartita(String username, InterfacciaClient controllerClientRMI, int numeroGiocatori) throws RemoteException;

}
