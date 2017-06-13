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
    public void spostaFamiliarePiano(int numeroTorre, int numeroPiano) throws IOException, FamiliareNonSelezionatoExcepion, TurnoException, ForzaInsufficienteException, ZonaOccupataExcepion, RisorseInsufficientiException, TorreOccupataException;
    public void spostaFamiliareMercato(int zonaMercato) throws RemoteException, TurnoException, ForzaInsufficienteException, ZonaOccupataExcepion;
    public void spostaFamiliarePalazzoDelConsiglio() throws RemoteException, ForzaInsufficienteException, TurnoException, ZonaOccupataExcepion;
    public void spostaFamiliareZonaProduzione(int zona) throws RemoteException, ForzaInsufficienteException, TurnoException, ZonaOccupataExcepion;
    public void spostaFamiliareZonaRaccolto(int zona) throws RemoteException, TurnoException, ForzaInsufficienteException, ZonaOccupataExcepion;
    public void tiraIDadi() throws IOException;
    public void sceltaScomunica(boolean appoggiaChiesa) throws RemoteException;
    public void aumentaForzaFamiliare(String coloreDado, int id) throws RemoteException, TurnoException, DadiNonTiratiException;
    public void saltaMossa(int id) throws RemoteException, TurnoException, DadiNonTiratiException;
    public void sceltaPergamena(int scelta) throws RemoteException;

}


