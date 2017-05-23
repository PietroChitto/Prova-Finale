package server.rmiServer;

import Client.InterfacciaClient;
import partita.carteDaGioco.CartaEdificio;
import partita.carteDaGioco.CartaImpresa;
import partita.carteDaGioco.CartaPersonaggio;
import partita.carteDaGioco.CartaTerritorio;
import partita.componentiDelTabellone.Familiare;
import partita.eccezioniPartita.ForzaInsufficienteException;
import partita.eccezioniPartita.RisorseInsufficientiException;
import partita.eccezioniPartita.TurnoException;
import partita.eccezioniPartita.ZonaOccupataExcepion;
import server.GiocatoreRemoto;
import server.MosseGiocatore;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Created by Pietro on 16/05/2017.
 */
public class GiocatoreRMI extends GiocatoreRemoto implements Serializable{
    private InterfacciaClient controllerClient;
    private transient MosseGiocatore mosseGiocatore;

    GiocatoreRMI (){
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


    }

    @Override
    public synchronized void scegliScomunica(boolean appoggiaChiesa) throws RemoteException {
        mosseGiocatore.scegliScomunica(appoggiaChiesa);
    }

    @Override
    public void iniziaPartita(int mioId) throws RemoteException {
        controllerClient.iniziaPartita(mioId);
    }

    @Override
    public void SpostatoFamiliarePiano(int numeroTorre, int numeroPiano, String coloreDado, int idGiocatore) throws RemoteException {

    }
}
