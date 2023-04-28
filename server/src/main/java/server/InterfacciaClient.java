package server;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Pietro on 16/05/2017.
 */
 public interface InterfacciaClient extends Remote{
      void iniziaPartita(int mioId, ArrayList<String> carte, ArrayList<String> giocatori, int[] risorse, ArrayList<String> scomuniche) throws RemoteException;
      void spostatoFamiliarePiano(int numeroTorre, int numeroPiano, String coloreDado, int idGiocatore) throws RemoteException;
      void dadiTirati(int ar, int ne, int bi) throws IOException;
      void messaggio(String s)throws RemoteException;
      void forzaAumentata(String colore, int forza) throws RemoteException;
      void risorseIncrementate(int pietra, int legna, int servitori, int monete, int puntiMilitari, int puntiFede, int puntiVittoria) throws RemoteException;
      void spostatoFamiliareMercato(int zonaMercato, String coloreDado, int id) throws RemoteException;
      void spostatoFamiliarePalazzoDelConsiglio(String coloreDado, int id) throws RemoteException;
      void spostatoFamiliareZonaProduzione(String coloreDado, int id, int zona) throws RemoteException;
      void spostatoFamiliareZonaRaccolto(String coloreDado, int id, int zona) throws RemoteException;
      void mossaSaltata(int id) throws RemoteException;
      void avvisoInizioTurno(ArrayList<String> nomiCarte) throws RemoteException;
      void scegliPergamena() throws RemoteException;
      void scegliScomunica() throws RemoteException;
      void giocatoreScomunicato(int id, int periodo) throws RemoteException;
      void finePartita(ArrayList<Integer> classificaId, ArrayList<String> classificaUsername, ArrayList<Integer> classificaPunti) throws RemoteException;
      void puntiGiocatore(int id, ArrayList<Integer> punteggi) throws RemoteException;
}
