package server;

import Client.InterfacciaClient;
import partita.Partita;
import partita.componentiDelTabellone.Giocatore;
import server.rmiServer.InterfaciaRemotaRMI;

/**
 * Created by Pietro on 16/05/2017.
 */
public abstract class GiocatoreRemoto implements InterfaciaRemotaRMI, InterfacciaClient{


    private transient Giocatore giocatore;
    private String username;
    private transient Partita partita;

    public GiocatoreRemoto(){
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

    //metodi della partita


}
