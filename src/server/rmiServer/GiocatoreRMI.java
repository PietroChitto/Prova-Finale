package server.rmiServer;

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

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Created by Pietro on 16/05/2017.
 */
public class GiocatoreRMI extends GiocatoreRemoto implements Serializable{
    private Familiare familiareSelezionato;
    GiocatoreRMI (){
        familiareSelezionato=null;
    }

    @Override
    public void selezionaFamiliare(String colore, int idGiocatore) throws RemoteException {
        try {
            getPartita().mioTurno(this.getGiocatore());
            for (Familiare f:this.getGiocatore().getFamiliari()) {
                if(f.isDisponibile() && f.getColoreDado().equals(colore)){
                    familiareSelezionato=f;
                }
            }
            //avvisa giocatore
        } catch (TurnoException e) {
            e.printStackTrace();
            //avvisa il giocatore che non è il suo turno
        }
    }

    @Override
    public void deselezionaFamiliare() throws RemoteException {
        familiareSelezionato=null;
    }

    @Override
    public void spostaFamiliarePiano(int numeroTorre, int numeroPiano) throws RemoteException {
        try {
            //se non va a buon fine lancia un eccezione
            getPartita().mioTurno(this.getGiocatore());
            //se non va a buon fine lancia un eccezione
            /*
            devo controllare se c'è già un mio familiare sulla torre
            devo controllare se la torre è occupata da un altro giocatore per pagare le monete
             */
            getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).getPiano(numeroPiano).getCampoAzione().setFamiliare(familiareSelezionato);
            //se non va a buon fine lancia un eccezione
            prendiCarta(numeroTorre,numeroPiano);
            getPartita().passaMossa(this);//ripristina il tabellone e fa i controlli di turno e periodo
        } catch (TurnoException e) {
            //avvisa che non è il suo turno
            e.printStackTrace();
        } catch (ZonaOccupataExcepion zonaOccupataExcepion) {
            //avvisa che quella zona è già occupata
            zonaOccupataExcepion.printStackTrace();
        } catch (ForzaInsufficienteException e) {
            //avvisa il giocatore che il suo familiare non ha forza sufficiente
            e.printStackTrace();
        } catch (RisorseInsufficientiException e) {
            //avvisa che la carta in quel piano ha un costo superiore alle sue possibilità
            e.printStackTrace();
        }
        /*
        manca la clausola che ha risorse a sufficienza per mettere li il familiare
         */
    }

    @Override
    public void spostaFamiliareMercato(int zonaMercato) throws RemoteException {
        try {
            getPartita().mioTurno(this.getGiocatore());
            getPartita().getCampoDaGioco().getTabellone().getMercato().arrivaGiocatore(familiareSelezionato, zonaMercato);
        } catch (ZonaOccupataExcepion zonaOccupataExcepion) {
            //avvisa che la zona è già occupata
            zonaOccupataExcepion.printStackTrace();
        } catch (TurnoException e) {
            //avvisa che non è il mio turno
            e.printStackTrace();
        } catch (ForzaInsufficienteException e) {


            e.printStackTrace();
        }
    }

    @Override
    public void spostaFamiliarePalazzoDelConsiglio() throws RemoteException {
        try {
            getPartita().mioTurno(this.getGiocatore());
            getPartita().getCampoDaGioco().getTabellone().getPalazzoDelConsiglio().arrivaGiocatore(this.getGiocatore());
        } catch (TurnoException e) {
            //avvisa che non è il mio turno
            e.printStackTrace();
        }
    }

    @Override
    public void spostaFamiliareZonaProduzione(int zona) throws RemoteException {
        try {
            getPartita().mioTurno(this.getGiocatore());
            if(zona==1)
                getPartita().getCampoDaGioco().getTabellone().getZonaProduzione().getCampoAzioneSinogolo().setFamiliare(familiareSelezionato);
            else
                getPartita().getCampoDaGioco().getTabellone().getZonaProduzione().getCampoAzioneMultiplo().aggiungiFamiliare(familiareSelezionato);
        } catch (TurnoException e) {
            e.printStackTrace();
        } catch (ZonaOccupataExcepion zonaOccupataExcepion) {
            zonaOccupataExcepion.printStackTrace();
        } catch (ForzaInsufficienteException e) {
            //il familiare nn ha la forza sufficiente per accedeere alla zona produzione
            e.printStackTrace();
        }
    }

    @Override
    public void spostaFamiliareZonaRaccolto(int zona) throws RemoteException {
        try {
            getPartita().mioTurno(this.getGiocatore());
            if(zona==1)
                getPartita().getCampoDaGioco().getTabellone().getZonaRaccolto().getCampoAzioneSinogolo().setFamiliare(familiareSelezionato);
            else
                getPartita().getCampoDaGioco().getTabellone().getZonaRaccolto().getCampoAzioneMultiplo().aggiungiFamiliare(familiareSelezionato);
        } catch (TurnoException e) {
            e.printStackTrace();
        } catch (ZonaOccupataExcepion zonaOccupataExcepion) {
            zonaOccupataExcepion.printStackTrace();
        } catch (ForzaInsufficienteException e) {
            //il familiare nn ha la forza sufficiente per accedeere alla zona produzione
            e.printStackTrace();
        }
    }

    @Override
    public void tiraIDadi() throws RemoteException {


    }

    @Override
    public synchronized void scegliScomunica(boolean appoggiaChiesa) throws RemoteException {
        if (appoggiaChiesa) {
            this.getGiocatore().setPuntiFede(0);
        } else {
            getPartita().getCampoDaGioco().getTabellone().getVaticano().scomunica(this.getGiocatore(), getPartita().getPeriodo());
        }
    }


    /**
     * prende la carta che c'è nel piano x della torre y, la aggiunge nella lista del giocatore e la rimuove dal piano
     * se il giocatore non ha risorse a sufficienza viene propagata l'eccezione
     * @param numeroTorre
     * @param numeroPiano
     * @throws RisorseInsufficientiException
     */
    private void prendiCarta(int numeroTorre, int numeroPiano) throws RisorseInsufficientiException {
        if(numeroTorre==1) {
            CartaTerritorio cartaTerritorio = null;
            cartaTerritorio=(CartaTerritorio) getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).getPiano(numeroPiano).getCartaSviluppo();
            this.getGiocatore().aggiungiTerritorio(cartaTerritorio);
            getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).getPiano(numeroPiano).setCartaSviluppo(null);
        }
        if(numeroTorre==2) {
            CartaEdificio cartaEdificio = null;
            cartaEdificio=(CartaEdificio)getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).getPiano(numeroPiano).getCartaSviluppo();
            cartaEdificio.confrontaCosto(this.getGiocatore());
            this.getGiocatore().aggiungiEdificio(cartaEdificio);
            getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).getPiano(numeroPiano).setCartaSviluppo(null);
        }
        if(numeroTorre==4) {
            CartaImpresa cartaImpresa = null;
            cartaImpresa=(CartaImpresa) getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).getPiano(numeroPiano).getCartaSviluppo();
            cartaImpresa.confrontaCosto(this.getGiocatore());
            this.getGiocatore().aggiungiImpresa(cartaImpresa);
            getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).getPiano(numeroPiano).setCartaSviluppo(null);
        }

        if(numeroTorre==3) {
            CartaPersonaggio cartaPersonaggio = null;
            cartaPersonaggio=(CartaPersonaggio) getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).getPiano(numeroPiano).getCartaSviluppo();
            cartaPersonaggio.confrontaCosto(this.getGiocatore());
            this.getGiocatore().aggiungiPersonaggio(cartaPersonaggio);
            getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).getPiano(numeroPiano).setCartaSviluppo(null);
        }

    }
}
