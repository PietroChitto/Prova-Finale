package Client;

import partita.componentiDelTabellone.Giocatore;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Pietro on 16/05/2017.
 */
 public interface InterfacciaClient extends Remote{
      public void iniziaPartita(int mioId, ArrayList<String> carte, ArrayList<String> giocatori) throws RemoteException;
      public void spostatoFamiliarePiano(int numeroTorre, int numeroPiano, String coloreDado, int idGiocatore) throws RemoteException;
      //public void spostaFamiliareMercato(int zonaMercato) throws RemoteException;
      //public void spostaFamiliarePalazzoDelConsiglio() throws RemoteException;
      //public void spostaFamiliareZonaProduzione(int zona) throws RemoteException;
      //public void spostaFamiliareZonaRaccolto(int zona) throws RemoteException;
      //public void tiraIDadi() throws RemoteException;
      //public void scegliScomunica(boolean appoggiaChiesa) throws RemoteException;
}
