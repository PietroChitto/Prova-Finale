package server;

import Client.InterfacciaClient;
import partita.Partita;
import partita.componentiDelTabellone.Giocatore;
import server.rmiServer.InterfaciaRemotaRMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Pietro on 16/05/2017.
 */
public abstract class GiocatoreRemoto extends UnicastRemoteObject implements InterfaciaRemotaRMI, InterfacciaClient{


    private transient Giocatore giocatore;
    private String username;
    private transient Partita partita;
    private MosseGiocatore mosseGiocatore;

    public GiocatoreRemoto() throws RemoteException {
        super();
    }

    public void setPartita(Partita partita){
        this.partita=partita;
    }

    public Partita getPartita(){
        return partita;
    }

    public void setGiocatore(Giocatore giocatore){
        this.giocatore=giocatore;
    }

    public Giocatore getGiocatore(){
        return giocatore;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMosse(MosseGiocatore mosse){
        this.mosseGiocatore=mosse;
    }

    public MosseGiocatore getMosse() {
        return mosseGiocatore;
    }

    //metodi della partita


}
