package Client;

import Client.GUI.ControllerGioco;
import javafx.application.Platform;
import partita.componentiDelTabellone.Giocatore;
import server.GiocatoreRemoto;
import server.ServerInterface;
import server.rmiServer.GiocatoreRMI;
import server.rmiServer.InterfaciaRemotaRMI;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Pietro on 16/05/2017.
 */
public class ClientRMI extends UnicastRemoteObject implements InterfacciaClient, InterfaciaRemotaRMI {
    private String nickname;
    private int id;
    boolean partitaIncorso;
    private InterfaciaRemotaRMI metodiPartita;
    private ControllerGioco controllerGioco;

    public ClientRMI(String text, ControllerGioco controllerGioco) throws RemoteException, NotBoundException {
        nickname=text;
        this.controllerGioco=controllerGioco;
        this.controllerGioco.setClientGenerico(this);
        partitaIncorso=false;
        Registry registry = LocateRegistry.getRegistry(8000);
        System.out.println("Registry caricato");
        ServerInterface inizio = (ServerInterface) registry.lookup("InterfaciaRemotaRMI");
        System.out.println("Oggetto scaricato");
        metodiPartita=(InterfaciaRemotaRMI) inizio.partecipaAPartita(nickname, this);
    }

    private void startClient() {
       /* try {
            metodiPartita.selezionaFamiliare("nero",this.id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }*/
        while(true){}
    }


    @Override
    public void iniziaPartita(int mioId, ArrayList<String> carte, ArrayList<String> giocatori) throws RemoteException {
        System.out.println("sono dentro");
        this.id=mioId;
        partitaIncorso=true;
        System.out.println("sono "+nickname+" con id "+ id+" e sono RMI");
        HashMap<Integer,String> mappaGiocatori=new HashMap<>();
        System.out.println("creo mappa");
        for(int i=0; i<4; i++){
            mappaGiocatori.put(i,giocatori.get(i));
        }
        System.out.println("inizializzo partita");

        Platform.runLater(()->{
            controllerGioco.inizializza(mappaGiocatori, carte,mioId);
        });
    }

    @Override
    public void spostatoFamiliarePiano(int numeroTorre, int numeroPiano, String coloreDado, int idGiocatore) throws RemoteException {

    }

    @Override
    public void dadiTirati(int ar, int ne, int bi) {
        Platform.runLater(()-> {
            try {
                controllerGioco.dadiTirati(ar, ne, bi);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void selezionaFamiliare(String colore, int idGiocatore) throws RemoteException {

    }

    @Override
    public void deselezionaFamiliare() throws RemoteException {

    }

    @Override
    public void spostaFamiliarePiano(int numeroTorre, int numeroPiano) throws RemoteException {

    }

    @Override
    public void spostaFamiliareMercato(int zonaMercato) throws RemoteException {

    }

    @Override
    public void spostaFamiliarePalazzoDelConsiglio() throws RemoteException {

    }

    @Override
    public void spostaFamiliareZonaProduzione(int zona) throws RemoteException {

    }

    @Override
    public void spostaFamiliareZonaRaccolto(int zona) throws RemoteException {

    }

    @Override
    public void tiraIDadi() throws RemoteException {
        try {
            metodiPartita.tiraIDadi();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void scegliScomunica(boolean appoggiaChiesa) throws RemoteException {

    }
}
