package server.rmiServer;

import partita.componentiDelTabellone.Familiare;
import partita.eccezioniPartita.*;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Pietro on 16/05/2017.
 */
public interface InterfaciaRemotaRMI extends Remote{


    public void selezionaFamiliare(String colore, int idGiocatore) throws RemoteException, TurnoException, DadiNonTiratiException;
    public void deselezionaFamiliare() throws RemoteException, TurnoException;
    public void spostaFamiliarePiano(int numeroTorre, int numeroPiano) throws RemoteException, FamiliareNonSelezionatoExcepion, TurnoException, ForzaInsufficienteException, ZonaOccupataExcepion, RisorseInsufficientiException;
    public void spostaFamiliareMercato(int zonaMercato) throws RemoteException;
    public void spostaFamiliarePalazzoDelConsiglio() throws RemoteException;
    public void spostaFamiliareZonaProduzione(int zona) throws RemoteException;
    public void spostaFamiliareZonaRaccolto(int zona) throws RemoteException;
    public void tiraIDadi() throws IOException;
    public void scegliScomunica(boolean appoggiaChiesa) throws RemoteException;
    public void aumentaForzaFamiliare(String coloreDado, int id) throws RemoteException, TurnoException, DadiNonTiratiException;

}


