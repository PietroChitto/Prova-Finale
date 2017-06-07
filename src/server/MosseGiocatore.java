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


    //manca l'attivazione degli effetti delle carte
    @Override
    public void spostaFamiliarePiano(int numeroTorre, int numeroPiano) throws RemoteException, FamiliareNonSelezionatoExcepion, TurnoException, ForzaInsufficienteException, ZonaOccupataExcepion, RisorseInsufficientiException, TorreOccupataException {
        //stampa di prova
        System.out.println("piano Ricevuto: " +numeroPiano);

        //controllo se è il mio turno, se non va a buon fine lancia un eccezione
        giocatore.getPartita().mioTurno(giocatore.getGiocatore());
        System.out.println("mio turno ok");
        //controllo se ho selezionato un familiare, se non va a buon fine lancia un eccezione
        if(familiareSelezionato==null){
            throw new FamiliareNonSelezionatoExcepion();
        }
        System.out.println("familiare selezionato ok");

        //devo controllare se c'è già un mio familiare sulla torre, se sto muovendo il neutro non devo fare questo controllo
        if(!(familiareSelezionato.getColoreDado().equals("neutro")))
            giocatore.getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).controllaFamiliare(giocatore.getGiocatore().getId());

        System.out.println("torre "+numeroTorre+" occupata "+giocatore.getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).isOccupata());
        //devo controllare se la torre è occupata da un altro giocatore per pagare le monete
         if(giocatore.getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).isOccupata()){
            System.out.println("la torre è occupata, paga monete");
             giocatore.getGiocatore().setMonete(giocatore.getGiocatore().getMonete()-3);
         }
        //prendo la carta dalla torre, se non va a buon fine lancia un' eccezione RisorseInsufficienti
        prendiCarta(numeroTorre,numeroPiano);

        //devo attivare gli effetti delle carte personaggio per eventuali aumenti di forza
        attivaEffettiPersonaggi(numeroTorre);

        //metto il familiare nel piano, se è già occupato lancia un eccezione ZonaOccupata
        giocatore.getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).getPiano(numeroPiano).getCampoAzione().setFamiliare(familiareSelezionato);

        //avviso i giocatori della mossa
        for (GiocatoreRemoto g: giocatore.getPartita().getGiocatori()){
            g.spostatoFamiliarePiano(numeroTorre,numeroPiano,familiareSelezionato.getColoreDado(), familiareSelezionato.getIdGiocatore());
        }

        //avviso il giocatore dell'incremento delle risorse
        giocatore.risorseIncrementate(giocatore.getGiocatore().getPietra(), giocatore.getGiocatore().getLegna(), giocatore.getGiocatore().getServitori(),giocatore.getGiocatore().getMonete(), giocatore.getGiocatore().getPuntiMilitari(), giocatore.getGiocatore().getPuntiFede(), giocatore.getGiocatore().getPuntiVittoria());

        //deseleziono il familiare
        familiareSelezionato.setDisponibile(false);
        deselezionaFamiliare();
        System.out.println("provoPassaMossa");
        //passo al prossimo giocatore
        giocatore.getPartita().passaMossa(giocatore);

    }

    private void attivaEffettiPersonaggi(int zona) {
        for(CartaPersonaggio c: giocatore.getGiocatore().getCartePersonaggio()){
            c.attivaEffettoPermanente(familiareSelezionato, zona);
        }
    }

    @Override
    public void spostaFamiliareMercato(int zonaMercato) throws RemoteException, TurnoException, ForzaInsufficienteException, ZonaOccupataExcepion {
        //controllo se è il mio turno
        giocatore.getPartita().mioTurno(giocatore.getGiocatore());

        giocatore.risorseIncrementate(giocatore.getGiocatore().getPietra(), giocatore.getGiocatore().getLegna(), giocatore.getGiocatore().getServitori(),giocatore.getGiocatore().getMonete(), giocatore.getGiocatore().getPuntiMilitari(), giocatore.getGiocatore().getPuntiFede(), giocatore.getGiocatore().getPuntiVittoria());

        /*
        *metto il familiare nel mercato,  se la zona è già occupata o non ho forza sufficiente solleva eccezioni, se va bene attiva
        * l'effetto del campo azione
        */
        giocatore.getPartita().getCampoDaGioco().getTabellone().getMercato().arrivaGiocatore(familiareSelezionato, zonaMercato);

        //avviso i giocatori della mossa
        for (GiocatoreRemoto g: giocatore.getPartita().getGiocatori()){
            g.spostatoFamiliareMercato(zonaMercato, familiareSelezionato.getColoreDado(), familiareSelezionato.getGiocatore().getId());
        }

        //avviso il giocatore dell'incremento delle risorse
        giocatore.risorseIncrementate(giocatore.getGiocatore().getPietra(), giocatore.getGiocatore().getLegna(), giocatore.getGiocatore().getServitori(),giocatore.getGiocatore().getMonete(), giocatore.getGiocatore().getPuntiMilitari(), giocatore.getGiocatore().getPuntiFede(), giocatore.getGiocatore().getPuntiVittoria());

        //deseleziono il familiare
        familiareSelezionato.setDisponibile(false);
        deselezionaFamiliare();

        //passo al prossimo giocatore
        giocatore.getPartita().passaMossa(giocatore);
    }

    @Override
    public void spostaFamiliarePalazzoDelConsiglio() throws RemoteException, ForzaInsufficienteException, TurnoException, ZonaOccupataExcepion {
        //controllo turno, se non è il mio turno manda una TurnoException
        giocatore.getPartita().mioTurno(giocatore.getGiocatore());

        //metto il familiare nel palazzo del consiglio, se non ho almeno forza 1 manda una ForzaInsufficienteException
        giocatore.getPartita().getCampoDaGioco().getTabellone().getPalazzoDelConsiglio().arrivaGiocatore(familiareSelezionato);

        //avviso i giocatori della mossa
        for (GiocatoreRemoto g: giocatore.getPartita().getGiocatori()){
            g.spostatoFamiliarePalazzoDelConsiglio(familiareSelezionato.getColoreDado(), familiareSelezionato.getGiocatore().getId());
        }

        //avviso il giocatore dell'incremento delle risorse
        giocatore.risorseIncrementate(giocatore.getGiocatore().getPietra(), giocatore.getGiocatore().getLegna(), giocatore.getGiocatore().getServitori(),giocatore.getGiocatore().getMonete(), giocatore.getGiocatore().getPuntiMilitari(), giocatore.getGiocatore().getPuntiFede(), giocatore.getGiocatore().getPuntiVittoria());

        //deseleziono il familiare
        familiareSelezionato.setDisponibile(false);
        deselezionaFamiliare();

        //passo al prossimo giocatore
        giocatore.getPartita().passaMossa(giocatore);

    }

    @Override
    public void spostaFamiliareZonaProduzione(int zona) throws RemoteException, ForzaInsufficienteException, TurnoException, ZonaOccupataExcepion {
        giocatore.getPartita().mioTurno(giocatore.getGiocatore());

        //devo attivare gli effetti delle carte personaggio per eventuali aumenti di forza
        attivaEffettiPersonaggi(4);

        //metto il familiare nella zona produzionee attivo gli effetti
        giocatore.getPartita().getCampoDaGioco().getTabellone().getZonaProduzione().arrivaGiocatore(familiareSelezionato);

        //avviso i giocatori della mossa
        for (GiocatoreRemoto g: giocatore.getPartita().getGiocatori()){
            g.spostatoFamiliareZonaProduzione(familiareSelezionato.getColoreDado(), familiareSelezionato.getGiocatore().getId(), zona);
        }

        //avviso il giocatore dell'incremento delle risorse
        giocatore.risorseIncrementate(giocatore.getGiocatore().getPietra(), giocatore.getGiocatore().getLegna(), giocatore.getGiocatore().getServitori(),giocatore.getGiocatore().getMonete(), giocatore.getGiocatore().getPuntiMilitari(), giocatore.getGiocatore().getPuntiFede(), giocatore.getGiocatore().getPuntiVittoria());

        //deseleziono il familiare
        familiareSelezionato.setDisponibile(false);
        deselezionaFamiliare();

        //passo al prossimo giocatore
        giocatore.getPartita().passaMossa(giocatore);
    }

    @Override
    public void spostaFamiliareZonaRaccolto(int zona) throws RemoteException, TurnoException, ForzaInsufficienteException, ZonaOccupataExcepion {
        giocatore.getPartita().mioTurno(giocatore.getGiocatore());

        //devo attivare gli effetti delle carte personaggio per eventuali aumenti di forza
        attivaEffettiPersonaggi(5);

        giocatore.getPartita().getCampoDaGioco().getTabellone().getZonaRaccolto().arrivaGiocatore(familiareSelezionato);

        //avviso i giocatori della mossa
        for (GiocatoreRemoto g: giocatore.getPartita().getGiocatori()){
            g.spostatoFamiliareZonaRaccolto(familiareSelezionato.getColoreDado(), familiareSelezionato.getGiocatore().getId(), zona);
        }

        //avviso il giocatore dell'incremento delle risorse
        giocatore.risorseIncrementate(giocatore.getGiocatore().getPietra(), giocatore.getGiocatore().getLegna(), giocatore.getGiocatore().getServitori(),giocatore.getGiocatore().getMonete(), giocatore.getGiocatore().getPuntiMilitari(), giocatore.getGiocatore().getPuntiFede(), giocatore.getGiocatore().getPuntiVittoria());

        //deseleziono il familiare
        familiareSelezionato.setDisponibile(false);
        deselezionaFamiliare();

        //passo al prossimo giocatore
        giocatore.getPartita().passaMossa(giocatore);
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

    @Override
    public void saltaMossa(int id) throws TurnoException, DadiNonTiratiException {
        giocatore.getPartita().mioTurno(giocatore.getGiocatore());
        giocatore.getPartita().dadiTirati();
        try {
            giocatore.getPartita().passaMossa(giocatore);
            giocatore.mossaSaltata(id);
        } catch (ZonaOccupataExcepion zonaOccupataExcepion) {
            zonaOccupataExcepion.printStackTrace();
        } catch (ForzaInsufficienteException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
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
            cartaTerritorio.attivaEffettoRapido(familiareSelezionato);
            System.out.println("codice Effetto: ");
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
            cartaEdificio.attivaEffettoRapido(familiareSelezionato);
            System.out.println("codice Effetto: ");
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
            System.out.println("codice Effetto: ");
            cartaImpresa.attivaEffettoRapido(familiareSelezionato);
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
            System.out.println("codice Effetto: "+cartaPersonaggio.getCodEffP());
            cartaPersonaggio.attivaEffettoRapido(familiareSelezionato);
        }

    }

}
