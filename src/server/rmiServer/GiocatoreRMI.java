package server.rmiServer;

import Client.InterfacciaClient;
import partita.carteDaGioco.CartaEdificio;
import partita.carteDaGioco.CartaImpresa;
import partita.carteDaGioco.CartaPersonaggio;
import partita.carteDaGioco.CartaTerritorio;
import partita.componentiDelTabellone.Familiare;
import partita.componentiDelTabellone.Giocatore;
import partita.eccezioniPartita.ForzaInsufficienteException;
import partita.eccezioniPartita.RisorseInsufficientiException;
import partita.eccezioniPartita.TurnoException;
import partita.eccezioniPartita.ZonaOccupataExcepion;
import server.GiocatoreRemoto;
import server.MosseGiocatore;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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
        mosseGiocatore.selezionaFamiliare(colore,idGiocatore);
    }

    @Override
    public void deselezionaFamiliare() throws RemoteException {
        mosseGiocatore.deselezionaFamiliare();
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
    public void iniziaPartita(int mioId, ArrayList<String> carte, ArrayList<String> giocatori) throws RemoteException {
        controllerClient.iniziaPartita(mioId, carte, giocatori);
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
}
