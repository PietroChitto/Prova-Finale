package server.rmiServer;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Pietro on 16/05/2017.
 */
public interface InterfaciaServer extends Remote{


    void selezionaFamiliare(String colore, int idGiocatore) throws RemoteException;
    void deselezionaFamiliare() throws RemoteException;
    void spostaFamiliarePiano(int numeroTorre, int numeroPiano) throws IOException;
    void spostaFamiliareMercato(int zonaMercato) throws RemoteException;
    void spostaFamiliarePalazzoDelConsiglio() throws RemoteException;
    void spostaFamiliareZonaProduzione(int zona) throws RemoteException;
    void spostaFamiliareZonaRaccolto(int zona) throws RemoteException;
    void tiraIDadi() throws RemoteException;
    void sceltaScomunica(boolean appoggiaChiesa) throws RemoteException;
    void aumentaForzaFamiliare(String coloreDado, int id) throws RemoteException;
    void saltaMossa(int id) throws RemoteException;
    void sceltaPergamena(int scelta) throws RemoteException;
    void esci(int mioId) throws RemoteException;
}


