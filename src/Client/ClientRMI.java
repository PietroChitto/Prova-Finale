package Client;

import Client.GUI.ControllerGioco;
import javafx.application.Platform;
import partita.Partita;
import partita.eccezioniPartita.*;
import server.ServerInterface;
import server.rmiServer.InterfaciaRemotaRMI;

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
    public void iniziaPartita(int mioId, ArrayList<String> carte, ArrayList<String> giocatori, int[] risorse, ArrayList<String> scomuniche) throws RemoteException {
        System.out.println("sono dentro");
        this.id=mioId;
        partitaIncorso=true;
        System.out.println("sono "+nickname+" con id "+ id+" e sono RMI");
        HashMap<Integer,String> mappaGiocatori=new HashMap<>();
        System.out.println("creo mappa");
        for(int i = 0; i< Partita.N_GIOCATORI; i++){
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
    public void spostatoFamiliareMercato(int zonaMercato, String coloreDado, int id) throws RemoteException {
        Platform.runLater(()->{
            try {
                controllerGioco.spostatoFamiliareMercato(zonaMercato,coloreDado,id);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void spostatoFamiliarePalazzoDelConsiglio(String coloreDado, int id) throws RemoteException {
        Platform.runLater(()->{
            try {
                controllerGioco.spostatoFamiliarePalazzoDelConsiglio(coloreDado, id);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void spostatoFamiliareZonaProduzione(String coloreDado, int id, int zona) throws RemoteException {
        Platform.runLater(()->{
            try {
                controllerGioco.spostatoFamiliareZonaProduzione(coloreDado, id, zona);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void spostatoFamiliareZonaRaccolto(String coloreDado, int id, int zona) throws RemoteException {
        Platform.runLater(()->{
            try {
                controllerGioco.spostatoFamiliareZonaRaccolto(coloreDado, id, zona);
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
    public void giocatoreScomunicato(int id) throws RemoteException {
        Platform.runLater(()->{
            try {
                controllerGioco.giocatoreScomunicato(id);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void selezionaFamiliare(String colore, int idGiocatore) throws RemoteException {
        try {
            metodiPartita.selezionaFamiliare(colore, idGiocatore);
        } catch (TurnoException e) {
            e.printStackTrace();
        } catch (DadiNonTiratiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deselezionaFamiliare() throws RemoteException, TurnoException {
        metodiPartita.deselezionaFamiliare();
    }

    @Override
    public void spostaFamiliarePiano(int numeroTorre, int numeroPiano) throws RemoteException {
        try {
            metodiPartita.spostaFamiliarePiano(numeroTorre, numeroPiano);
        } catch (FamiliareNonSelezionatoExcepion familiareNonSelezionatoExcepion) {
            familiareNonSelezionatoExcepion.printStackTrace();
        } catch (TurnoException e) {
            e.printStackTrace();
        } catch (ForzaInsufficienteException e) {
            e.printStackTrace();
        } catch (ZonaOccupataExcepion zonaOccupataExcepion) {
            zonaOccupataExcepion.printStackTrace();
        } catch (RisorseInsufficientiException e) {
            e.printStackTrace();
        } catch (TorreOccupataException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void spostaFamiliareMercato(int zonaMercato) throws RemoteException {
        try {
            metodiPartita.spostaFamiliareMercato(zonaMercato);
        } catch (TurnoException e) {
            e.printStackTrace();
        } catch (ForzaInsufficienteException e) {
            e.printStackTrace();
        } catch (ZonaOccupataExcepion zonaOccupataExcepion) {
            zonaOccupataExcepion.printStackTrace();
        }
    }

    @Override
    public void spostaFamiliarePalazzoDelConsiglio() throws RemoteException {
        try {
            metodiPartita.spostaFamiliarePalazzoDelConsiglio();
        } catch (ForzaInsufficienteException e) {
            e.printStackTrace();
        } catch (TurnoException e) {
            e.printStackTrace();
        } catch (ZonaOccupataExcepion zonaOccupataExcepion) {
            zonaOccupataExcepion.printStackTrace();
        }
    }

    @Override
    public void spostaFamiliareZonaProduzione(int zona) throws RemoteException {
        try {
            metodiPartita.spostaFamiliareZonaProduzione(zona);
        } catch (ForzaInsufficienteException e) {

        } catch (TurnoException e) {

        } catch (ZonaOccupataExcepion zonaOccupataExcepion) {
            zonaOccupataExcepion.printStackTrace();
        }
    }

    @Override
    public void spostaFamiliareZonaRaccolto(int zona) throws RemoteException {
        try {
            metodiPartita.spostaFamiliareZonaRaccolto(zona);
        } catch (TurnoException e) {
            e.printStackTrace();
        } catch (ForzaInsufficienteException e) {
            e.printStackTrace();
        } catch (ZonaOccupataExcepion zonaOccupataExcepion) {
            zonaOccupataExcepion.printStackTrace();
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

    }

    @Override
    public void aumentaForzaFamiliare(String coloreDado, int id) throws RemoteException {
        try {
            metodiPartita.aumentaForzaFamiliare(coloreDado,id);
        } catch (TurnoException e) {
            e.printStackTrace();
        } catch (DadiNonTiratiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saltaMossa(int id) {
        try {
            metodiPartita.saltaMossa(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (TurnoException e) {
            e.printStackTrace();
        } catch (DadiNonTiratiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sceltaPergamena(int scelta) throws RemoteException {
        metodiPartita.sceltaPergamena(scelta);
    }
}
