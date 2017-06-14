package server;

import Client.InterfacciaClient;
import server.rmiServer.InterfaciaRemotaRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Pietro on 16/05/2017.
 */
public interface ServerInterface extends Remote {

    //public void login(String username) throws RemoteException;
    InterfaciaRemotaRMI partecipaAPartita(String username, InterfacciaClient controllerClientRMI, int numeroGiocatori) throws RemoteException;


    /*
    public void selezionaFamiliare(String colore, int idGiocatore) throws RemoteException;
    public void deselezionaFamiliare(String colore, int idGiocatore) throws RemoteException;
    public void spostaFamiliarePiano(Familiare f,int numeroTorre, int numeroPiano) throws RemoteException;
    public void spostaFamiliareMercato(Familiare f, int zonaMercato) throws RemoteException;
    public void spostaFamiliarePalazzoDelConsiglio(Familiare f) throws RemoteException;
    public void spostaFamiliareZonaProduzione(Familiare f, int zona) throws RemoteException;
    public void spostaFamiliareZonaRaccolto(Familiare f, int zona) throws RemoteException;
    public void tiraIDadi(Familiare f, int zona) throws RemoteException;
    public void scegliScomunica(boolean appoggiaChiesa) throws RemoteException;
    */

}
