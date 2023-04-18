package Client;

import Client.GUI.ControllerGioco;
import javafx.application.Platform;
import server.ServerInterface;
import server.rmiServer.InterfaciaServer;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Pietro on 16/05/2017.
 */
public class ClientRMI extends UnicastRemoteObject implements InterfacciaClient, InterfaciaServer {
    private String nickname;
    private int id;
    private boolean partitaIncorso;
    private InterfaciaServer metodiPartita;
    private ControllerGioco controllerGioco;

    public ClientRMI(String text, ControllerGioco controllerGioco, int numeroGiocatori) throws RemoteException, NotBoundException {
        nickname=text;
        this.controllerGioco=controllerGioco;
        this.controllerGioco.setClientGenerico(this);
        partitaIncorso=false;
        Registry registry = LocateRegistry.getRegistry(8008);
        System.out.println("Registry caricato");
        ServerInterface inizio = (ServerInterface) registry.lookup("InterfaciaServer");
        System.out.println("Oggetto scaricato");
        metodiPartita= inizio.partecipaAPartita(nickname, this, numeroGiocatori);
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
    public void iniziaPartita(int mioId, ArrayList<String> carte, ArrayList<String> giocatori, int[] risorse, ArrayList<String> scomuniche) throws RemoteException {
        System.out.println("sono dentro");
        this.id=mioId;
        partitaIncorso=true;
        System.out.println("sono "+nickname+" con id "+ id+" e sono RMI");
        HashMap<Integer,String> mappaGiocatori=new HashMap<>();
        System.out.println("creo mappa");
        for(int i = 0; i< giocatori.size(); i++){
            mappaGiocatori.put(i,giocatori.get(i));
        }
        System.out.println("inizializzo partita");

        Platform.runLater(()->{
            controllerGioco.inizializza(mappaGiocatori, carte,mioId, risorse, scomuniche);
        });
    }

    @Override
    public void spostatoFamiliarePiano(int numeroTorre, int numeroPiano, String coloreDado, int idGiocatore) throws RemoteException {
        Platform.runLater(()-> {
            try {
                controllerGioco.spostatoFamiliarePiano(numeroTorre,numeroPiano,coloreDado,idGiocatore);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
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
    public void messaggio(String s) throws RemoteException {
        Platform.runLater(()->{
            try {
                controllerGioco.messaggio(s);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void forzaAumentata(String colore, int forza) throws RemoteException {
        Platform.runLater(()-> {
            try {
                controllerGioco.forzaAumentata(colore, forza);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void risorseIncrementate(int pietra, int legna, int servitori, int monete, int puntiMilitari, int puntiFede, int puntiVittoria) throws RemoteException {
        Platform.runLater(()-> {
            try {
                controllerGioco.risorseIncrementate(pietra, legna, servitori, monete, puntiMilitari, puntiFede, puntiVittoria);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void spostatoFamiliareMercato(int zonaMercato, String coloreDado, int idGiocatore) throws RemoteException {
        Platform.runLater(()->{
            try {
                controllerGioco.spostatoFamiliareMercato(zonaMercato,coloreDado,idGiocatore);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void spostatoFamiliarePalazzoDelConsiglio(String coloreDado, int idGiocatore) throws RemoteException {
        Platform.runLater(()->{
            try {
                controllerGioco.spostatoFamiliarePalazzoDelConsiglio(coloreDado, idGiocatore);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void spostatoFamiliareZonaProduzione(String coloreDado, int idGiocatore, int zona) throws RemoteException {
        Platform.runLater(()->{
            try {
                controllerGioco.spostatoFamiliareZonaProduzione(coloreDado, idGiocatore, zona);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void spostatoFamiliareZonaRaccolto(String coloreDado, int idGiocatore, int zona) throws RemoteException {
        Platform.runLater(()->{
            try {
                controllerGioco.spostatoFamiliareZonaRaccolto(coloreDado, idGiocatore, zona);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void mossaSaltata(int id) {

    }

    @Override
    public void avvisoInizioTurno(ArrayList<String> nomiCarte) {
        Platform.runLater(()->{
            controllerGioco.nuovoTurno(nomiCarte);
        });
    }

    @Override
    public void scegliPergamena() throws RemoteException {
        Platform.runLater(()->{
            try {
                controllerGioco.scegliPergamena();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void scegliScomunica() throws RemoteException {
        Platform.runLater(()->{
            try {
                controllerGioco.scegliScomunica();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void giocatoreScomunicato(int idGiocatore, int periodo) throws RemoteException {
        Platform.runLater(()->{
            try {
                controllerGioco.giocatoreScomunicato(idGiocatore, periodo);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void finePartita(ArrayList<Integer> classificaId,ArrayList<String> username, ArrayList<Integer> punti) throws RemoteException {
        Platform.runLater(()->{
            try {
                controllerGioco.finePartita(classificaId,username,punti);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void puntiGiocatore(int id, ArrayList<Integer> punteggi) throws RemoteException {
        Platform.runLater(()->{
            try {
                controllerGioco.puntiGiocatore(id,punteggi);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void selezionaFamiliare(String colore, int idGiocatore){

        try {
            metodiPartita.selezionaFamiliare(colore, idGiocatore);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deselezionaFamiliare(){
        try {
            metodiPartita.deselezionaFamiliare();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void spostaFamiliarePiano(int numeroTorre, int numeroPiano){
        try {
            metodiPartita.spostaFamiliarePiano(numeroTorre, numeroPiano);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void spostaFamiliareMercato(int zonaMercato){
        try {
            metodiPartita.spostaFamiliareMercato(zonaMercato);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void spostaFamiliarePalazzoDelConsiglio(){
        try {
            metodiPartita.spostaFamiliarePalazzoDelConsiglio();
        }catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void spostaFamiliareZonaProduzione(int zona){
        try {
            metodiPartita.spostaFamiliareZonaProduzione(zona);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void spostaFamiliareZonaRaccolto(int zona){
        try {
            metodiPartita.spostaFamiliareZonaRaccolto(zona);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
    public void sceltaScomunica(boolean appoggiaChiesa) throws RemoteException {
        metodiPartita.sceltaScomunica(appoggiaChiesa);
    }

    @Override
    public void aumentaForzaFamiliare(String coloreDado, int id){
        try {
            metodiPartita.aumentaForzaFamiliare(coloreDado,id);
        }catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saltaMossa(int id) {
        try {
            metodiPartita.saltaMossa(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sceltaPergamena(int scelta) throws RemoteException {
        metodiPartita.sceltaPergamena(scelta);
    }

    @Override
    public void esci(int mioId) throws RemoteException {
        metodiPartita.esci(mioId);
    }
}
