package server.rmiServer;

import Client.InterfacciaClient;
import partita.eccezioniPartita.*;
import server.GiocatoreRemoto;
import server.MosseGiocatore;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Pietro on 16/05/2017.
 */
public class GiocatoreRMI extends GiocatoreRemoto{
    private InterfacciaClient controllerClient;
    private transient MosseGiocatore mosseGiocatore;

    GiocatoreRMI () throws RemoteException {
        super();
        mosseGiocatore=new MosseGiocatore(this);
    }

    public void setControllerClientRMI(InterfacciaClient controllerClientRMI){
        this.controllerClient=controllerClientRMI;
    }

    public InterfacciaClient getControllerClientRMI() {
        return controllerClient;
    }

    @Override
    public void selezionaFamiliare(String colore, int idGiocatore) throws RemoteException {
        try {
            mosseGiocatore.selezionaFamiliare(colore,idGiocatore);
            controllerClient.messaggio("Familiare selezionato");
        } catch (TurnoException e) {
            controllerClient.messaggio("Non è il tuo turno");
        } catch (DadiNonTiratiException e) {
            controllerClient.messaggio("Tira prima i dadi");
        }
    }

    @Override
    public void deselezionaFamiliare() throws RemoteException {
        try {
            mosseGiocatore.deselezionaFamiliare();
            controllerClient.messaggio("Familiare deselezionato");
        } catch (TurnoException e) {
            controllerClient.messaggio("");
        }
    }

    @Override
    public void spostaFamiliarePiano(int numeroTorre, int numeroPiano) throws RemoteException {
        try {
            mosseGiocatore.spostaFamiliarePiano(numeroTorre, numeroPiano);
        } catch (FamiliareNonSelezionatoExcepion familiareNonSelezionatoExcepion) {
            controllerClient.messaggio("familiare non selezionato");
        } catch (TurnoException e) {
            controllerClient.messaggio("non è il tuoturno");
        } catch (ForzaInsufficienteException e) {
            controllerClient.messaggio("Il familiare non ha forza sufficiente");
        } catch (ZonaOccupataExcepion zonaOccupataExcepion) {
            controllerClient.messaggio("la zona è già occupata");
        } catch (RisorseInsufficientiException e) {
            controllerClient.messaggio("non hai risorse sufficienti per prendere la carta");
        } catch (TorreOccupataException e) {
            controllerClient.messaggio("Hai già piazzato un familiare sulla torre");
        }
    }

    @Override
    public void spostaFamiliareMercato(int zonaMercato) throws RemoteException {
        try {
            mosseGiocatore.spostaFamiliareMercato(zonaMercato);
        } catch (TurnoException e) {
            controllerClient.messaggio("Non è il tuo turno");
        } catch (ForzaInsufficienteException e) {
            controllerClient.messaggio("Forza Insufficiente");
        } catch (ZonaOccupataExcepion zonaOccupataExcepion) {
            controllerClient.messaggio("La zona è già occupata");
        }
    }

    @Override
    public void spostaFamiliarePalazzoDelConsiglio() throws RemoteException {
        try {
            mosseGiocatore.spostaFamiliarePalazzoDelConsiglio();
        } catch (ForzaInsufficienteException e) {
            controllerClient.messaggio("Forza Insufficiente");
        } catch (TurnoException e) {
            controllerClient.messaggio("Non è il tuo turno");
        } catch (ZonaOccupataExcepion zonaOccupataExcepion) {
            controllerClient.messaggio("");
        }
    }

    @Override
    public void spostaFamiliareZonaProduzione(int zona) throws RemoteException {
        try {
            mosseGiocatore.spostaFamiliareZonaProduzione(zona);
        } catch (ForzaInsufficienteException e) {
            controllerClient.messaggio("forza Insufficiente");
        } catch (TurnoException e) {
            controllerClient.messaggio("Non è il tuo turno");
        } catch (ZonaOccupataExcepion zonaOccupataExcepion) {
            zonaOccupataExcepion.printStackTrace();
        }
    }

    @Override
    public void spostaFamiliareZonaRaccolto(int zona) throws RemoteException {
        try {
            mosseGiocatore.spostaFamiliareZonaRaccolto(zona);
        } catch (TurnoException e) {
            controllerClient.messaggio("Non è il tuo turno");
        } catch (ForzaInsufficienteException e) {
            controllerClient.messaggio("forza Insufficiente");
        } catch (ZonaOccupataExcepion zonaOccupataExcepion) {

        }
    }

    @Override
    public void tiraIDadi() throws RemoteException {
        if(getGiocatore().getId()==getPartita().getOrdineTurno().get(0).getId())
            try {
                mosseGiocatore.tiraIDadi();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    public synchronized void scegliScomunica(boolean appoggiaChiesa) throws RemoteException {
        mosseGiocatore.scegliScomunica(appoggiaChiesa);
    }

    @Override
    public void aumentaForzaFamiliare(String coloreDado, int id) throws RemoteException {
        try {
            mosseGiocatore.aumentaForzaFamiliare(coloreDado, id);
        } catch (TurnoException e) {
            controllerClient.messaggio("Non è il tuo turno");
        } catch (DadiNonTiratiException e) {
            controllerClient.messaggio("Dadi non tirati");
        }
    }

    @Override
    public void saltaMossa(int id) throws RemoteException {
        try {
            mosseGiocatore.saltaMossa(id);
        } catch (TurnoException e) {
            controllerClient.messaggio("non è il tuo turno");
        } catch (DadiNonTiratiException e) {
            controllerClient.messaggio("dadi non tirati");
        }
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void iniziaPartita(int mioId, ArrayList<String> carte, ArrayList<String> giocatori, int[] risorse, ArrayList<String> scomuniche) throws RemoteException {
        controllerClient.iniziaPartita(mioId, carte, giocatori, risorse, scomuniche);
    }

    @Override
    public void spostatoFamiliarePiano(int numeroTorre, int numeroPiano, String coloreDado, int idGiocatore) throws RemoteException {
        controllerClient.spostatoFamiliarePiano(numeroTorre,numeroPiano,coloreDado,idGiocatore);
    }

    @Override
    public void dadiTirati(int ar, int ne, int bi) throws RemoteException {
        try {
            controllerClient.dadiTirati(ar, ne, bi);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void messaggio(String s) throws RemoteException {

    }

    @Override
    public void forzaAumentata(String colore, int forza) throws RemoteException {
        controllerClient.forzaAumentata(colore, forza);
    }

    @Override
    public void risorseIncrementate(int pietra, int legna, int servitori, int monete, int puntiMilitari, int puntiFede, int puntiVittoria) throws RemoteException {
        controllerClient.risorseIncrementate(pietra, legna, servitori, monete, puntiMilitari, puntiFede, puntiVittoria);
    }

    @Override
    public void spostatoFamiliareMercato(int zonaMercato, String coloreDado, int id) throws RemoteException {
        controllerClient.spostatoFamiliareMercato(zonaMercato,coloreDado,id);
    }

    @Override
    public void spostatoFamiliarePalazzoDelConsiglio(String coloreDado, int id) throws RemoteException {
        controllerClient.spostatoFamiliarePalazzoDelConsiglio(coloreDado, id);
    }

    @Override
    public void spostatoFamiliareZonaProduzione(String coloreDado, int id, int zona) throws RemoteException {
        controllerClient.spostatoFamiliareZonaProduzione(coloreDado,id,zona);
    }

    @Override
    public void spostatoFamiliareZonaRaccolto(String coloreDado, int id, int zona) throws RemoteException {
        controllerClient.spostatoFamiliareZonaRaccolto(coloreDado,id,zona);
    }

    @Override
    public void mossaSaltata(int id) {
        try {
            controllerClient.messaggio("mossa Saltata");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void avvisoInizioTurno(ArrayList<String> nomiCarte) {
        try {
            controllerClient.avvisoInizioTurno(nomiCarte);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
