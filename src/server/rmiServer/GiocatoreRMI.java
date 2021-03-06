package server.rmiServer;

import Client.InterfacciaClient;
import partita.componentiDelTabellone.Familiare;
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
    //private transient MosseGiocatore mosseGiocatore;

    public GiocatoreRMI() throws RemoteException {
        super();
        super.setMosse(new MosseGiocatore(this));
    }

    void setControllerClientRMI(InterfacciaClient controllerClientRMI){
        this.controllerClient=controllerClientRMI;
    }

    @Override
    public void selezionaFamiliare(String colore, int idGiocatore) throws RemoteException {
        if (controllerClient != null) {
            try {
                getMosse().selezionaFamiliare(colore, idGiocatore);
                controllerClient.messaggio("Familiare selezionato");
            } catch (TurnoException e) {
                controllerClient.messaggio("Non è il tuo turno");
            } catch (DadiNonTiratiException e) {
                controllerClient.messaggio("Tira prima i dadi");
            }
        }
    }

    @Override
    public void deselezionaFamiliare() throws RemoteException {
        try {
            getMosse().deselezionaFamiliare();
            if(controllerClient!=null)
            controllerClient.messaggio("Familiare deselezionato");
        } catch (TurnoException e) {
            controllerClient.messaggio("");
        }
    }

    @Override
    public void spostaFamiliarePiano(int numeroTorre, int numeroPiano) throws RemoteException {
            try {
                getMosse().spostaFamiliarePiano(numeroTorre, numeroPiano);
            } catch (FamiliareNonSelezionatoExcepion familiareNonSelezionatoExcepion) {
                if(controllerClient!=null)
                controllerClient.messaggio("familiare non selezionato");
            } catch (TurnoException e) {
                if(controllerClient!=null)
                controllerClient.messaggio("Non è il tuo turno");
            } catch (ForzaInsufficienteException e) {
                //se la torre era occupata il giocatore aveva speso 3 monete, siccome la mossa non è andata a buon fine le restituisco
                if(controllerClient!=null)
                controllerClient.messaggio("Il familiare non ha forza sufficiente");
            } catch (ZonaOccupataExcepion zonaOccupataExcepion) {
                //se la torre era occupata il giocatore aveva speso 3 monete, siccome la mossa non è andata a buon fine le restituisco
                if(getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).isOccupata())
                    getGiocatore().setMonete(getGiocatore().getMonete()+3);
                if(controllerClient!=null)
                controllerClient.messaggio("la zona è già occupata");
            } catch (RisorseInsufficientiException e) {
                if(controllerClient!=null)
                controllerClient.messaggio("non hai risorse sufficienti");
            } catch (TorreOccupataException e) {
                if(controllerClient!=null)
                controllerClient.messaggio("Hai già piazzato un familiare sulla torre");
            }
            finally {
                if(getMosse().getFamiliareSelezionato()!=null) {
                    //per eventuali decrementi di forza ripristino la forza del familiare al valore del dado
                    Familiare f = getMosse().getFamiliareSelezionato();
                    if (f.getColoreDado() == "nero") {
                        f.setForza(getPartita().getValoreDadoNero());
                    }
                    else if(f.getColoreDado()=="bianco"){
                        f.setForza(getPartita().getValoreDadoBianco());
                    }
                    else if (f.getColoreDado()=="arancio"){
                        f.setForza(getPartita().getValoreDadoArancio());
                    }
                    else if(f.getColoreDado()=="neutro"){
                        f.setForza(0);
                    }
                }
            }

    }

    @Override
    public void spostaFamiliareMercato(int zonaMercato) throws RemoteException {
        if(controllerClient!=null) {
            try {
                getMosse().spostaFamiliareMercato(zonaMercato);
            } catch (TurnoException e) {
                controllerClient.messaggio("Non è il tuo turno");
            } catch (ForzaInsufficienteException e) {
                controllerClient.messaggio("Forza Insufficiente");
            } catch (ZonaOccupataExcepion zonaOccupataExcepion) {
                controllerClient.messaggio("La zona è già occupata");
            } catch (DadiNonTiratiException e) {
                controllerClient.messaggio("Tira i dadi");
            } catch (FamiliareNonSelezionatoExcepion familiareNonSelezionatoExcepion) {
                controllerClient.messaggio("familiare non selezionato");
            }
        }
    }

    @Override
    public void spostaFamiliarePalazzoDelConsiglio() throws RemoteException {
        try {
            getMosse().spostaFamiliarePalazzoDelConsiglio();
        } catch (ForzaInsufficienteException e) {
            controllerClient.messaggio("Forza Insufficiente");
        } catch (TurnoException e) {
            controllerClient.messaggio("Non è il tuo turno");
        } catch (ZonaOccupataExcepion zonaOccupataExcepion) {
            controllerClient.messaggio("");
        } catch (DadiNonTiratiException e) {
            controllerClient.messaggio("tira i dadi");
        } catch (FamiliareNonSelezionatoExcepion familiareNonSelezionatoExcepion) {
            controllerClient.messaggio("familiare non selezionato");
        }
    }

    @Override
    public void spostaFamiliareZonaProduzione(int zona) throws RemoteException {
        try {
            getMosse().spostaFamiliareZonaProduzione(zona);
        } catch (ForzaInsufficienteException e) {
            controllerClient.messaggio("forza Insufficiente");
        } catch (TurnoException e) {
            controllerClient.messaggio("Non è il tuo turno");
        } catch (ZonaOccupataExcepion zonaOccupataExcepion) {
            controllerClient.messaggio("La zona è già occupata");
        } catch (DadiNonTiratiException e) {
            controllerClient.messaggio("Tira i dadi");
        } catch (FamiliareNonSelezionatoExcepion familiareNonSelezionatoExcepion) {
            controllerClient.messaggio("familiare non selezionato");
        }
        finally {
            if(getMosse().getFamiliareSelezionato()!=null) {
                //per eventuali decrementi di forza ripristino la forza del familiare al valore del dado
                Familiare f = getMosse().getFamiliareSelezionato();
                if (f.getColoreDado() == "nero") {
                    f.setForza(getPartita().getValoreDadoNero());
                }
                else if(f.getColoreDado()=="bianco"){
                    f.setForza(getPartita().getValoreDadoBianco());
                }
                else if (f.getColoreDado()=="arancio"){
                    f.setForza(getPartita().getValoreDadoArancio());
                }
                else if(f.getColoreDado()=="neutro"){
                    f.setForza(0);
                }
            }
        }
    }

    @Override
    public void spostaFamiliareZonaRaccolto(int zona) throws RemoteException {
        try {
            getMosse().spostaFamiliareZonaRaccolto(zona);
        } catch (TurnoException e) {
            controllerClient.messaggio("Non è il tuo turno");
        } catch (ForzaInsufficienteException e) {
            controllerClient.messaggio("forza Insufficiente");
        } catch (ZonaOccupataExcepion zonaOccupataExcepion) {
            controllerClient.messaggio("Zona già Occupata");
        } catch (FamiliareNonSelezionatoExcepion familiareNonSelezionatoExcepion) {
            controllerClient.messaggio("familiare non selezionato");
        } catch (DadiNonTiratiException e) {
            controllerClient.messaggio("Tira i dadi");
        }
        finally {
            if(getMosse().getFamiliareSelezionato()!=null) {
                //per eventuali decrementi di forza ripristino la forza del familiare al valore del dado
                Familiare f = getMosse().getFamiliareSelezionato();
                if (f.getColoreDado() == "nero") {
                    f.setForza(getPartita().getValoreDadoNero());
                }
                else if(f.getColoreDado()=="bianco"){
                    f.setForza(getPartita().getValoreDadoBianco());
                }
                else if (f.getColoreDado()=="arancio"){
                    f.setForza(getPartita().getValoreDadoArancio());
                }
                else if(f.getColoreDado()=="neutro"){
                    f.setForza(0);
                }
            }
        }
    }

    @Override
    public void tiraIDadi() throws RemoteException {
        if(getGiocatore().getId()==getPartita().getOrdineTurno().get(0).getId())
            try {
                getMosse().tiraIDadi();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    public synchronized void sceltaScomunica(boolean appoggiaChiesa) throws RemoteException {
        getMosse().sceltaScomunica(appoggiaChiesa);
    }

    @Override
    public void aumentaForzaFamiliare(String coloreDado, int id) throws RemoteException {
        try {
            getMosse().aumentaForzaFamiliare(coloreDado, id);
        } catch (TurnoException e) {
            controllerClient.messaggio("Non è il tuo turno");
        } catch (DadiNonTiratiException e) {
            controllerClient.messaggio("Dadi non tirati");
        }
    }

    @Override
    public void saltaMossa(int id) throws RemoteException {
        try {
            getMosse().saltaMossa(id);
        } catch (TurnoException e) {
            controllerClient.messaggio("non è il tuo turno");
        } catch (DadiNonTiratiException e) {
            controllerClient.messaggio("dadi non tirati");
        }
    }

    @Override
    public void sceltaPergamena(int scelta) throws RemoteException {
        getMosse().sceltaPergamena(scelta);
    }

    @Override
    public void esci(int mioId) throws RemoteException {
        if(getMosse()!=null)
            getMosse().esci(mioId);
        else
            System.out.println("mosseGiocatore=null");
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void iniziaPartita(int mioId, ArrayList<String> carte, ArrayList<String> giocatori, int[] risorse, ArrayList<String> scomuniche) throws RemoteException {
        if(giocatori.size()==0){
            System.out.println("lista giocatori vuota");
        }
        for (String s: giocatori){
            System.out.println("lista giocatori non vuota");
            System.out.println("(RMI)"+s);
        }
        if(controllerClient!=null)
        controllerClient.iniziaPartita(mioId, carte, giocatori, risorse, scomuniche);
    }

    @Override
    public void spostatoFamiliarePiano(int numeroTorre, int numeroPiano, String coloreDado, int idGiocatore) throws RemoteException {
        if(controllerClient!=null)
        controllerClient.spostatoFamiliarePiano(numeroTorre,numeroPiano,coloreDado,idGiocatore);
    }

    @Override
    public void dadiTirati(int ar, int ne, int bi) throws RemoteException {
        try {
            if(controllerClient!=null)
            controllerClient.dadiTirati(ar, ne, bi);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void messaggio(String s) throws RemoteException {
        if(controllerClient!=null)
        controllerClient.messaggio(s);
    }

    @Override
    public void forzaAumentata(String colore, int forza) throws RemoteException {
        controllerClient.forzaAumentata(colore, forza);
    }

    @Override
    public void risorseIncrementate(int pietra, int legna, int servitori, int monete, int puntiMilitari, int puntiFede, int puntiVittoria) throws RemoteException {
        if(controllerClient!=null)
        controllerClient.risorseIncrementate(pietra, legna, servitori, monete, puntiMilitari, puntiFede, puntiVittoria);
    }

    @Override
    public void spostatoFamiliareMercato(int zonaMercato, String coloreDado, int id) throws RemoteException {
        if(controllerClient!=null)
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
        if(controllerClient!=null) {
            try {
                controllerClient.avvisoInizioTurno(nomiCarte);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void scegliPergamena() throws RemoteException {
        controllerClient.scegliPergamena();
    }

    @Override
    public void scegliScomunica() throws RemoteException {
        controllerClient.scegliScomunica();
    }

    @Override
    public void giocatoreScomunicato(int id, int periodo) throws RemoteException {
        if(controllerClient!=null)
        controllerClient.giocatoreScomunicato(id, periodo);
    }

    @Override
    public void finePartita(ArrayList<Integer> classificaId, ArrayList<String> username, ArrayList<Integer> punti) throws RemoteException {
        if(controllerClient!=null)
        controllerClient.finePartita(classificaId,username,punti);
    }

    @Override
    public void puntiGiocatore(int id, ArrayList<Integer> punteggi) throws RemoteException {
        if(controllerClient!=null)
            controllerClient.puntiGiocatore(id,punteggi);
    }
}
