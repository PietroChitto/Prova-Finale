package server.rmiServer;

import partita.eccezioniPartita.*;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Pietro on 16/05/2017.
 */
public interface InterfaciaRemotaRMI extends Remote{


    void selezionaFamiliare(String colore, int idGiocatore) throws RemoteException, TurnoException, DadiNonTiratiException;
    void deselezionaFamiliare() throws RemoteException, TurnoException;
    void spostaFamiliarePiano(int numeroTorre, int numeroPiano) throws IOException, FamiliareNonSelezionatoExcepion, TurnoException, ForzaInsufficienteException, ZonaOccupataExcepion, RisorseInsufficientiException, TorreOccupataException;
    void spostaFamiliareMercato(int zonaMercato) throws RemoteException, TurnoException, ForzaInsufficienteException, ZonaOccupataExcepion;
    void spostaFamiliarePalazzoDelConsiglio() throws RemoteException, ForzaInsufficienteException, TurnoException, ZonaOccupataExcepion;
    void spostaFamiliareZonaProduzione(int zona) throws RemoteException, ForzaInsufficienteException, TurnoException, ZonaOccupataExcepion;
    void spostaFamiliareZonaRaccolto(int zona) throws RemoteException, TurnoException, ForzaInsufficienteException, ZonaOccupataExcepion;
    void tiraIDadi() throws IOException;
    void sceltaScomunica(boolean appoggiaChiesa) throws RemoteException;
    void aumentaForzaFamiliare(String coloreDado, int id) throws RemoteException, TurnoException, DadiNonTiratiException;
    void saltaMossa(int id) throws RemoteException, TurnoException, DadiNonTiratiException;
    void sceltaPergamena(int scelta) throws RemoteException;

}


