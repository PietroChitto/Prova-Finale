package server;

import partita.carteDaGioco.*;
import partita.componentiDelTabellone.Familiare;
import partita.componentiDelTabellone.Giocatore;
import partita.eccezioniPartita.*;
import server.rmiServer.InterfaciaRemotaRMI;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Pietro on 23/05/2017.
 */

public class MosseGiocatore implements InterfaciaRemotaRMI {
    Familiare familiareSelezionato;
    GiocatoreRemoto giocatore;

    public MosseGiocatore(GiocatoreRemoto giocatore){
            this.giocatore=giocatore;
    }

    @Override
    public void selezionaFamiliare(String colore, int idGiocatore) throws RemoteException, TurnoException, DadiNonTiratiException {
        System.out.println("seleziona familiare "+idGiocatore+"-"+giocatore.getGiocatore().getId());
        giocatore.getPartita().mioTurno(giocatore.getGiocatore());
        giocatore.getPartita().dadiTirati();

        for (Familiare f:giocatore.getGiocatore().getFamiliari()) {
            if(f.isDisponibile() && f.getColoreDado().equals(colore)){
                familiareSelezionato=f;
                System.out.println("selezionato il familiare "+f.getColoreDado()+" del giocatore "+f.getGiocatore().getId());
            }
        }
        //avvisa giocatore

    }

    @Override
    public void deselezionaFamiliare() throws RemoteException, TurnoException {
        giocatore.getPartita().mioTurno(giocatore.getGiocatore());
        familiareSelezionato=null;
    }

    @Override
    public void spostaFamiliarePiano(int numeroTorre, int numeroPiano) throws RemoteException, FamiliareNonSelezionatoExcepion, TurnoException, ForzaInsufficienteException, ZonaOccupataExcepion, RisorseInsufficientiException {
        System.out.println("piano ricevuto: "+numeroPiano);
        //se non va a buon fine lancia un eccezione
        giocatore.getPartita().mioTurno(giocatore.getGiocatore());
        //se non va a buon fine lancia un eccezione
        if(familiareSelezionato==null){
            throw new FamiliareNonSelezionatoExcepion();
        }
        /*
        devo controllare se c'è già un mio familiare sulla torre
        devo controllare se la torre è occupata da un altro giocatore per pagare le monete
         */
        //se non va a buon fine lancia un' eccezione
        System.out.println("cerco di prendere la carta");
        prendiCarta(numeroTorre,numeroPiano);
        System.out.println("carta presa");

        giocatore.getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).getPiano(numeroPiano).getCampoAzione().setFamiliare(familiareSelezionato);
        //avviso i giocatori della mossa
        for (GiocatoreRemoto g: giocatore.getPartita().getGiocatori()){
            g.spostatoFamiliarePiano(numeroTorre,numeroPiano,familiareSelezionato.getColoreDado(), familiareSelezionato.getIdGiocatore());
        }
        giocatore.risorseIncrementate(giocatore.getGiocatore().getPietra(), giocatore.getGiocatore().getLegna(), giocatore.getGiocatore().getServitori(),giocatore.getGiocatore().getMonete(), giocatore.getGiocatore().getPuntiMilitari(), giocatore.getGiocatore().getPuntiFede(), giocatore.getGiocatore().getPuntiVittoria());
        familiareSelezionato.setDisponibile(false);
        deselezionaFamiliare();
        giocatore.getPartita().passaMossa(giocatore);//ripristina il tabellone e fa i controlli di turno e periodo
        /*
        manca la clausola che ha risorse a sufficienza per mettere li il familiare
         */
    }

    @Override
    public void spostaFamiliareMercato(int zonaMercato) throws RemoteException {
        try {
            giocatore.getPartita().mioTurno(giocatore.getGiocatore());
            giocatore.getPartita().getCampoDaGioco().getTabellone().getMercato().arrivaGiocatore(familiareSelezionato, zonaMercato);
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
            giocatore.getPartita().mioTurno(giocatore.getGiocatore());
            giocatore.getPartita().getCampoDaGioco().getTabellone().getPalazzoDelConsiglio().arrivaGiocatore(giocatore.getGiocatore());
        } catch (TurnoException e) {
            //avvisa che non è il mio turno
            e.printStackTrace();
        }
    }

    @Override
    public void spostaFamiliareZonaProduzione(int zona) throws RemoteException {
        try {
            giocatore.getPartita().mioTurno(giocatore.getGiocatore());
            if(zona==1)
                giocatore.getPartita().getCampoDaGioco().getTabellone().getZonaProduzione().getCampoAzioneSinogolo().setFamiliare(familiareSelezionato);
            else
                giocatore.getPartita().getCampoDaGioco().getTabellone().getZonaProduzione().getCampoAzioneMultiplo().aggiungiFamiliare(familiareSelezionato);
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
           giocatore.getPartita().mioTurno(giocatore.getGiocatore());
            if(zona==1)
                giocatore.getPartita().getCampoDaGioco().getTabellone().getZonaRaccolto().getCampoAzioneSinogolo().setFamiliare(familiareSelezionato);
            else
                giocatore.getPartita().getCampoDaGioco().getTabellone().getZonaRaccolto().getCampoAzioneMultiplo().aggiungiFamiliare(familiareSelezionato);
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
    public void tiraIDadi() throws IOException {
        giocatore.getPartita().setValoreDadoArancio((int) ((Math.random() * 100) % 6) + 1);
        giocatore.getPartita().setValoreDadoNero((int) ((Math.random() * 100) % 6) + 1);
        giocatore.getPartita().setValoreDadoBianco((int) ((Math.random() * 100) % 6) + 1);
        Familiare[] fam;
        for (GiocatoreRemoto g: giocatore.getPartita().getGiocatori()){
            fam = g.getGiocatore().getFamiliari();
            fam[0].setForza(g.getPartita().getValoreDadoArancio());
            fam[1].setForza(g.getPartita().getValoreDadoBianco());
            fam[2].setForza(g.getPartita().getValoreDadoNero());
            fam[3].setForza(0);

        }

            for (GiocatoreRemoto g: giocatore.getPartita().getGiocatori()){
                g.dadiTirati(giocatore.getPartita().getValoreDadoArancio(), giocatore.getPartita().getValoreDadoNero(), giocatore.getPartita().getValoreDadoBianco());
            }
            giocatore.getPartita().setDadiTirati(true);
    }

    @Override
    public void scegliScomunica(boolean appoggiaChiesa) throws RemoteException {
        if (appoggiaChiesa) {
            giocatore.getGiocatore().setPuntiFede(0);
        } else {
            giocatore.getPartita().getCampoDaGioco().getTabellone().getVaticano().scomunica(giocatore.getGiocatore(), giocatore.getPartita().getPeriodo());
        }
    }

    @Override
    public void aumentaForzaFamiliare(String coloreDado, int id) throws RemoteException, TurnoException, DadiNonTiratiException {
        giocatore.getPartita().mioTurno(giocatore.getGiocatore());
        giocatore.getPartita().dadiTirati();
        Familiare[] familiari=giocatore.getGiocatore().getFamiliari();
        for(int i=0; i<4; i++){
            if(familiari[i].isDisponibile() && familiari[i].getColoreDado().equals(coloreDado) && giocatore.getGiocatore().getServitori()>0){
                familiari[i].setForza(familiari[i].getForza()+1);
                giocatore.getGiocatore().setServitori(giocatore.getGiocatore().getServitori()-1);
                giocatore.forzaAumentata(familiari[i].getColoreDado(), familiari[i].getForza());
                giocatore.risorseIncrementate(giocatore.getGiocatore().getPietra(), giocatore.getGiocatore().getLegna(), giocatore.getGiocatore().getServitori(),giocatore.getGiocatore().getMonete(), giocatore.getGiocatore().getPuntiMilitari(), giocatore.getGiocatore().getPuntiFede(), giocatore.getGiocatore().getPuntiVittoria());

            }
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
        if(numeroTorre==0) {
            CartaTerritorio cartaTerritorio = null;
            cartaTerritorio=(CartaTerritorio) giocatore.getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).getPiano(numeroPiano).getCartaSviluppo();
            giocatore.getGiocatore().aggiungiTerritorio(cartaTerritorio);
            giocatore.getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).getPiano(numeroPiano).setCartaSviluppo(null);
        }
        if(numeroTorre==2) {
            CartaEdificio cartaEdificio = null;
            int[] arrayCosti = new int[7];
            cartaEdificio=(CartaEdificio)giocatore.getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).getPiano(numeroPiano).getCartaSviluppo();
            System.out.println(cartaEdificio.getNome());
            cartaEdificio.confrontaCosto(giocatore.getGiocatore());
            arrayCosti=cartaEdificio.getCosto().toArray();
            giocatore.getGiocatore().decrementaRisorse(arrayCosti);
            giocatore.getGiocatore().aggiungiEdificio(cartaEdificio);
            giocatore.getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).getPiano(numeroPiano).setCartaSviluppo(null);
        }
        if(numeroTorre==3) {
            CartaImpresa cartaImpresa = null;
            int[] arrayCosti = new int[7];
            cartaImpresa=(CartaImpresa) giocatore.getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).getPiano(numeroPiano).getCartaSviluppo();
            System.out.println(cartaImpresa.getNome());
            cartaImpresa.confrontaCosto(giocatore.getGiocatore());
            arrayCosti=cartaImpresa.getCosto1().toArray();
            giocatore.getGiocatore().decrementaRisorse(arrayCosti);
            giocatore.getGiocatore().aggiungiImpresa(cartaImpresa);
            giocatore.getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).getPiano(numeroPiano).setCartaSviluppo(null);
        }

        if(numeroTorre==1) {
            CartaPersonaggio cartaPersonaggio = null;
            int[] arrayCosti = new int[7];
            cartaPersonaggio=(CartaPersonaggio) giocatore.getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).getPiano(numeroPiano).getCartaSviluppo();
            System.out.println(cartaPersonaggio.getNome());
            cartaPersonaggio.confrontaCosto(giocatore.getGiocatore());
            arrayCosti=cartaPersonaggio.getCosto().toArray();
            giocatore.getGiocatore().decrementaRisorse(arrayCosti);
            giocatore.getGiocatore().aggiungiPersonaggio(cartaPersonaggio);
            giocatore.getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).getPiano(numeroPiano).setCartaSviluppo(null);
        }

    }

}
