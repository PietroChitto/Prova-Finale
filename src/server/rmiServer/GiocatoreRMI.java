package server.rmiServer;

import Client.InterfacciaClient;
import partita.eccezioniPartita.DadiNonTiratiException;
import partita.eccezioniPartita.TurnoException;
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
        mosseGiocatore.spostaFamiliarePiano(numeroTorre, numeroPiano);
    }

    @Override
    public void spostaFamiliareMercato(int zonaMercato) throws RemoteException {
        mosseGiocatore.spostaFamiliareMercato(zonaMercato);
    }

    @Override
    public void spostaFamiliarePalazzoDelConsiglio() throws RemoteException {
        mosseGiocatore.spostaFamiliarePalazzoDelConsiglio();
    }

    @Override
    public void spostaFamiliareZonaProduzione(int zona) throws RemoteException {
        mosseGiocatore.spostaFamiliareZonaProduzione(zona);
    }

    @Override
    public void spostaFamiliareZonaRaccolto(int zona) throws RemoteException {
        mosseGiocatore.spostaFamiliareZonaRaccolto(zona);
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
//-----------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void iniziaPartita(int mioId, ArrayList<String> carte, ArrayList<String> giocatori, int[] risorse) throws RemoteException {
        controllerClient.iniziaPartita(mioId, carte, giocatori, risorse);
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
}
