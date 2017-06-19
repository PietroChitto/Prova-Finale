package server;

import partita.carteDaGioco.*;
import partita.componentiDelTabellone.Familiare;
import partita.componentiDelTabellone.Giocatore;
import partita.eccezioniPartita.*;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * Created by Pietro on 23/05/2017.
 */

public class MosseGiocatore{
    Familiare familiareSelezionato;
    GiocatoreRemoto giocatore;

    public MosseGiocatore(GiocatoreRemoto giocatore){
            this.giocatore=giocatore;
    }

    public Familiare getFamiliareSelezionato(){
        return familiareSelezionato;
    }

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


    public void deselezionaFamiliare() throws RemoteException, TurnoException {
        giocatore.getPartita().mioTurno(giocatore.getGiocatore());
        familiareSelezionato=null;
        System.out.println("familiare deselezionato");
    }


    public void spostaFamiliarePiano(int numeroTorre, int numeroPiano) throws RemoteException, FamiliareNonSelezionatoExcepion, TurnoException, ForzaInsufficienteException, ZonaOccupataExcepion, RisorseInsufficientiException, TorreOccupataException {
        //stampa di prova
        /*System.out.println("piano Ricevuto: " +numeroPiano);

        //controllo se è il mio turno, se non va a buon fine lancia un eccezione
        giocatore.getPartita().mioTurno(giocatore.getGiocatore());
        System.out.println("mio turno ok");
        //controllo se ho selezionato un familiare, se non va a buon fine lancia un eccezione
        if(familiareSelezionato==null){
            System.out.println("familiare non selezionato");
            throw new FamiliareNonSelezionatoExcepion();
        }
        System.out.println("familiare selezionato ok");

        //devo controllare se c'è già un mio familiare sulla torre, se sto muovendo il neutro non devo fare questo controllo
        if(!(familiareSelezionato.getColoreDado().equals("neutro")))
            giocatore.getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).controllaFamiliare(giocatore.getGiocatore().getId());

        //devo controllare se la torre è occupata da un altro giocatore per pagare le monete
        if(giocatore.getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).isOccupata()){
            System.out.println("la torre è occupata, paga monete");
            giocatore.getGiocatore().setMonete(giocatore.getGiocatore().getMonete()-3);
        }

        //devo attivare gli effetti delle carte personaggio per eventuali aumenti di forza
        attivaEffettiPersonaggi(numeroTorre);

        //attivo le scomuniche per eventuali diminuzioni di forza
        attivaScomuniche(numeroTorre,"");

        //controlloRisorseCarta(numeroTorre,numeroPiano);


        //prendo la carta dalla torre
        prendiCarta(numeroTorre,numeroPiano);

        //metto il familiare nel piano, se è già occupato lancia un eccezione ZonaOccupata
        giocatore.getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).getPiano(numeroPiano).getCampoAzione().setFamiliare(familiareSelezionato);

        //prendo la carta dalla torre
        prendiCarta(numeroTorre,numeroPiano);


        ArrayList<Integer> punti=new ArrayList<>();
        //avviso i giocatori della mossa, passando i punteggi dei giocatori
        for (GiocatoreRemoto g: giocatore.getPartita().getGiocatori()){
            g.spostatoFamiliarePiano(numeroTorre,numeroPiano,familiareSelezionato.getColoreDado(), familiareSelezionato.getIdGiocatore());
            punti.add(giocatore.getGiocatore().getPuntiVittoria());
            punti.add(giocatore.getGiocatore().getPuntiFede());
            punti.add(giocatore.getGiocatore().getPuntiMilitari());
            g.puntiGiocatore(familiareSelezionato.getIdGiocatore(), punti);
        }

        //avviso il giocatore dell'incremento delle risorse
        giocatore.risorseIncrementate(giocatore.getGiocatore().getPietra(), giocatore.getGiocatore().getLegna(), giocatore.getGiocatore().getServitori(),giocatore.getGiocatore().getMonete(), giocatore.getGiocatore().getPuntiMilitari(), giocatore.getGiocatore().getPuntiFede(), giocatore.getGiocatore().getPuntiVittoria());

        //deseleziono il familiare
        familiareSelezionato.setDisponibile(false);
        deselezionaFamiliare();
        System.out.println("provoPassaMossa");
        //passo al prossimo giocatore
        giocatore.getPartita().passaMossa(giocatore);



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

        //attivo le scomuniche per eventuali diminuzioni di forza
        attivaScomuniche(numeroTorre,"");

        //prendo la carta dalla torre, se non va a buon fine lancia un' eccezione RisorseInsufficienti
        prendiCarta(numeroTorre,numeroPiano);

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
        giocatore.getPartita().passaMossa(giocatore);*/
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
        /*if(giocatore.getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).isOccupata()){
            System.out.println("la torre è occupata, paga monete");
            giocatore.getGiocatore().setMonete(giocatore.getGiocatore().getMonete()-3);
        }*/

        //devo attivare gli effetti delle carte personaggio per eventuali aumenti di forza
        attivaEffettiPersonaggi(numeroTorre);

        //attivo le scomuniche per eventuali diminuzioni di forza
        attivaScomuniche(numeroTorre,"");

        if(giocatore.getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).getPiano(numeroPiano).getCampoAzione().getCosto() > familiareSelezionato.getForza()){
            throw new ForzaInsufficienteException();
        }

        //prendo la carta dalla torre, se non va a buon fine lancia un' eccezione RisorseInsufficienti
        prendiCarta(numeroTorre,numeroPiano);

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


    private void attivaScomuniche(int zona, String codiceAggiuntivo) {
            System.out.println("ATTIVO SCOMUNICHE ");
            System.out.println("codice aggiuntivo: "+codiceAggiuntivo);
            System.out.println("zona: "+zona);
            System.out.println("familiare "+familiareSelezionato.getColoreDado()+" del giocatore "+familiareSelezionato.getGiocatore().getId());
            for(CartaScomunica c: giocatore.getGiocatore().getScomuniche()){
                if(c!=null) {
                    c.attivaEffettoScomunica(familiareSelezionato, codiceAggiuntivo, zona);
                }
                else {
                    System.out.println("carta scomunica = null");
                }
            }
    }

    private void attivaEffettiPersonaggi(int zona) {

            for (CartaPersonaggio c : giocatore.getGiocatore().getCartePersonaggio()) {
                c.attivaEffettoPermanente(familiareSelezionato, zona);
            }

    }


    public void spostaFamiliareMercato(int zonaMercato) throws RemoteException, TurnoException, ForzaInsufficienteException, ZonaOccupataExcepion, DadiNonTiratiException, FamiliareNonSelezionatoExcepion {
        //controllo se è il mio turno
        giocatore.getPartita().mioTurno(giocatore.getGiocatore());
        giocatore.getPartita().dadiTirati();

        if(familiareSelezionato==null){
            System.out.println("familiare non selezionato");
            throw new FamiliareNonSelezionatoExcepion();
        }

        giocatore.risorseIncrementate(giocatore.getGiocatore().getPietra(), giocatore.getGiocatore().getLegna(), giocatore.getGiocatore().getServitori(),giocatore.getGiocatore().getMonete(), giocatore.getGiocatore().getPuntiMilitari(), giocatore.getGiocatore().getPuntiFede(), giocatore.getGiocatore().getPuntiVittoria());

        /*
        *metto il familiare nel mercato,  se la zona è già occupata o non ho forza sufficiente solleva eccezioni, se va bene attiva
        * l'effetto del campo azione
        */
        giocatore.getPartita().getCampoDaGioco().getTabellone().getMercato().arrivaGiocatore(familiareSelezionato, zonaMercato);

        if(zonaMercato==3){
            //manda la scelta pergamena
            giocatore.scegliPergamena();
            giocatore.scegliPergamena();
        }

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


    public void spostaFamiliarePalazzoDelConsiglio() throws RemoteException, ForzaInsufficienteException, TurnoException, ZonaOccupataExcepion, DadiNonTiratiException, FamiliareNonSelezionatoExcepion {
        //controllo turno, se non è il mio turno manda una TurnoException
        giocatore.getPartita().mioTurno(giocatore.getGiocatore());
        giocatore.getPartita().dadiTirati();
        if(familiareSelezionato==null){
            System.out.println("familiare non selezionato");
            throw new FamiliareNonSelezionatoExcepion();
        }

        //metto il familiare nel palazzo del consiglio, se non ho almeno forza 1 manda una ForzaInsufficienteException
        giocatore.getPartita().getCampoDaGioco().getTabellone().getPalazzoDelConsiglio().arrivaGiocatore(familiareSelezionato);

        //avviso i giocatori della mossa
        for (GiocatoreRemoto g: giocatore.getPartita().getGiocatori()){
            g.spostatoFamiliarePalazzoDelConsiglio(familiareSelezionato.getColoreDado(), familiareSelezionato.getGiocatore().getId());
        }

        //faccio scegliere al giocatore il privilegio del consiglio
        giocatore.scegliPergamena();

        //avviso il giocatore dell'incremento delle risorse
        giocatore.risorseIncrementate(giocatore.getGiocatore().getPietra(), giocatore.getGiocatore().getLegna(), giocatore.getGiocatore().getServitori(),giocatore.getGiocatore().getMonete(), giocatore.getGiocatore().getPuntiMilitari(), giocatore.getGiocatore().getPuntiFede(), giocatore.getGiocatore().getPuntiVittoria());

        //deseleziono il familiare
        familiareSelezionato.setDisponibile(false);
        deselezionaFamiliare();

        //passo al prossimo giocatore
        giocatore.getPartita().passaMossa(giocatore);

    }


    public void spostaFamiliareZonaProduzione(int zona) throws RemoteException, ForzaInsufficienteException, TurnoException, ZonaOccupataExcepion, DadiNonTiratiException, FamiliareNonSelezionatoExcepion {
        giocatore.getPartita().mioTurno(giocatore.getGiocatore());
        giocatore.getPartita().dadiTirati();
        if(familiareSelezionato==null){
            System.out.println("familiare non selezionato");
            throw new FamiliareNonSelezionatoExcepion();
        }

        //devo attivare gli effetti delle carte personaggio per eventuali aumenti di forza
        attivaEffettiPersonaggi(4);

        //attivo le scomuniche per eventuali diminuzioni di forza
        attivaScomuniche(4,"");

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


    public void spostaFamiliareZonaRaccolto(int zona) throws RemoteException, TurnoException, ForzaInsufficienteException, ZonaOccupataExcepion, FamiliareNonSelezionatoExcepion, DadiNonTiratiException {
        giocatore.getPartita().mioTurno(giocatore.getGiocatore());
        giocatore.getPartita().dadiTirati();
        if(familiareSelezionato==null){
            System.out.println("familiare non selezionato");
            throw new FamiliareNonSelezionatoExcepion();
        }

        //devo attivare gli effetti delle carte personaggio per eventuali aumenti di forza
        attivaEffettiPersonaggi(5);

        //attivo le scomuniche per eventuali diminuzioni di forza
        attivaScomuniche(5,"");

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


    public synchronized void sceltaScomunica(boolean appoggiaChiesa) throws RemoteException {
        if (appoggiaChiesa) {
            giocatore.getGiocatore().setPuntiVittoria(giocatore.getGiocatore().getPuntiVittoria()+giocatore.getGiocatore().getPuntiFede());
            giocatore.getGiocatore().setPuntiFede(0);
        } else {
            giocatore.getPartita().getCampoDaGioco().getTabellone().getVaticano().scomunica(giocatore.getGiocatore(), giocatore.getPartita().getPeriodo());
            for(GiocatoreRemoto g: giocatore.getPartita().getGiocatori()){
                g.giocatoreScomunicato(giocatore.getGiocatore().getId(), giocatore.getPartita().getPeriodo()-1);
            }
        }
        //avviso il giocatore dell'incremento delle risorse
        giocatore.risorseIncrementate(giocatore.getGiocatore().getPietra(), giocatore.getGiocatore().getLegna(), giocatore.getGiocatore().getServitori(),giocatore.getGiocatore().getMonete(), giocatore.getGiocatore().getPuntiMilitari(), giocatore.getGiocatore().getPuntiFede(), giocatore.getGiocatore().getPuntiVittoria());

    }


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


    public void saltaMossa(int id) throws TurnoException, DadiNonTiratiException {
        giocatore.getPartita().mioTurno(giocatore.getGiocatore());
        giocatore.getPartita().dadiTirati();
        try {
            giocatore.getPartita().passaMossa(giocatore);
            giocatore.mossaSaltata(id);
            familiareSelezionato=null;
        } catch (ZonaOccupataExcepion zonaOccupataExcepion) {
            zonaOccupataExcepion.printStackTrace();
        } catch (ForzaInsufficienteException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public synchronized void sceltaPergamena(int scelta) throws RemoteException {
        Giocatore giocatoreGioco=giocatore.getGiocatore();
        switch (scelta){
            case 0: giocatoreGioco.setLegna(giocatoreGioco.getLegna()+1);
                    giocatoreGioco.setPietra(giocatoreGioco.getPietra()+1);
                    break;
            case 1: giocatoreGioco.setServitori(giocatoreGioco.getServitori()+2);
                    break;
            case 2: giocatoreGioco.setMonete(giocatoreGioco.getMonete()+2);
                    break;
            case 3: giocatoreGioco.setPuntiMilitari(giocatoreGioco.getPuntiMilitari()+2);
                    break;
            case 4: giocatoreGioco.setPuntiFede(giocatoreGioco.getPuntiFede()+1);
                    break;
        }

        giocatore.risorseIncrementate(giocatoreGioco.getPietra(),giocatoreGioco.getLegna(),giocatoreGioco.getServitori(),giocatoreGioco.getMonete(),giocatoreGioco.getPuntiMilitari(),giocatoreGioco.getPuntiFede(),giocatoreGioco.getPuntiVittoria());
    }

    private void controlloRisorseCarta(int numeroTorre, int numeroPiano) throws RisorseInsufficientiException {
        if(numeroTorre!=0){
            switch (numeroTorre){
                case 1:CartaPersonaggio carta;
                    carta=(CartaPersonaggio) giocatore.getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).getPiano(numeroPiano).getCartaSviluppo();
                    if(carta!=null)
                    carta.confrontaCosto(giocatore.getGiocatore());
                    break;
                case 2:CartaEdificio carta1;
                        carta1=(CartaEdificio)giocatore.getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).getPiano(numeroPiano).getCartaSviluppo();
                        if(carta1!=null)
                        carta1.confrontaCosto(giocatore.getGiocatore());
                        break;
                case 3:CartaImpresa carta2;
                    carta2=(CartaImpresa)giocatore.getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).getPiano(numeroPiano).getCartaSviluppo();
                    if(carta2!=null)
                    carta2.confrontaCosto(giocatore.getGiocatore());
                    break;
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
            cartaTerritorio.attivaEffettoRapido(familiareSelezionato);
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
            cartaPersonaggio.attivaEffettoRapido(familiareSelezionato);
        }

    }

    public void esci(int id) {
        GiocatoreRemoto gioc=null;
        System.out.println("il giocatore "+id+" ha abbandonato");
        System.out.println("ci sono: "+giocatore.getPartita().getGiocatori().size()+" giocatori");

        //rimuovo il giocatore con l'id
        for(GiocatoreRemoto g: giocatore.getPartita().getGiocatori()){
            if(g.getGiocatore().getId()==id) {
                gioc=g;
                System.out.println("elimino il giocatore " + gioc.getGiocatore().getId() + " dalla lista");
            }
        }

        giocatore.getPartita().getGiocatori().remove(gioc);


        System.out.println("ci sono: "+giocatore.getPartita().getGiocatori().size()+" giocatori");
        //avviso i giocatori rimanenti
        for(GiocatoreRemoto g: giocatore.getPartita().getGiocatori()){

            try {
                System.out.println("avviso il giocatore " + g.getGiocatore().getId());
                g.finePartita(null, null, null);
                System.out.println("avvisato il giocatore " + g.getGiocatore().getId());

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}

/*package server;

import partita.carteDaGioco.*;
import partita.componentiDelTabellone.Familiare;
import partita.componentiDelTabellone.Giocatore;
import partita.eccezioniPartita.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Pietro on 23/05/2017.
 */

/*public class MosseGiocatore{
    Familiare familiareSelezionato;
    GiocatoreRemoto giocatore;

    public MosseGiocatore(GiocatoreRemoto giocatore){
        this.giocatore=giocatore;
    }


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


    public void deselezionaFamiliare() throws RemoteException, TurnoException {
        giocatore.getPartita().mioTurno(giocatore.getGiocatore());
        familiareSelezionato=null;
    }


    //manca l'attivazione degli effetti delle carte

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

        //devo attivare gli effetti delle carte personaggio per eventuali aumenti di forza
        attivaEffettiPersonaggi(numeroTorre);

        //attivo le scomuniche per eventuali diminuzioni di forza
        attivaScomuniche(numeroTorre,"");

        //prendo la carta dalla torre, se non va a buon fine lancia un' eccezione RisorseInsufficienti
        prendiCarta(numeroTorre,numeroPiano);

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

    private void attivaScomuniche(int zona, String codiceAggiuntivo) {
        System.out.println("ATTIVO SCOMUNICHE ");
        System.out.println("codice aggiuntivo: "+codiceAggiuntivo);
        System.out.println("zona: "+zona);
        System.out.println("familiare "+familiareSelezionato.getColoreDado()+" del giocatore "+familiareSelezionato.getGiocatore().getId());
        for(CartaScomunica c: giocatore.getGiocatore().getScomuniche()){
            if(c!=null) {
                c.attivaEffettoScomunica(familiareSelezionato, codiceAggiuntivo, zona);
            }
            else {
                System.out.println("carta scomunica = null");
            }
        }
    }

    private void attivaEffettiPersonaggi(int zona) {

        for (CartaPersonaggio c : giocatore.getGiocatore().getCartePersonaggio()) {
            c.attivaEffettoPermanente(familiareSelezionato, zona);
        }

    }


    public void spostaFamiliareMercato(int zonaMercato) throws RemoteException, TurnoException, ForzaInsufficienteException, ZonaOccupataExcepion {
        //controllo se è il mio turno
        giocatore.getPartita().mioTurno(giocatore.getGiocatore());

        giocatore.risorseIncrementate(giocatore.getGiocatore().getPietra(), giocatore.getGiocatore().getLegna(), giocatore.getGiocatore().getServitori(),giocatore.getGiocatore().getMonete(), giocatore.getGiocatore().getPuntiMilitari(), giocatore.getGiocatore().getPuntiFede(), giocatore.getGiocatore().getPuntiVittoria());

        /*
        *metto il familiare nel mercato,  se la zona è già occupata o non ho forza sufficiente solleva eccezioni, se va bene attiva
        * l'effetto del campo azione
        */
        /*giocatore.getPartita().getCampoDaGioco().getTabellone().getMercato().arrivaGiocatore(familiareSelezionato, zonaMercato);

        if(zonaMercato==3){
            //manda la scelta pergamena
            giocatore.scegliPergamena();
            giocatore.scegliPergamena();
        }

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


    public void spostaFamiliarePalazzoDelConsiglio() throws RemoteException, ForzaInsufficienteException, TurnoException, ZonaOccupataExcepion {
        //controllo turno, se non è il mio turno manda una TurnoException
        giocatore.getPartita().mioTurno(giocatore.getGiocatore());

        //metto il familiare nel palazzo del consiglio, se non ho almeno forza 1 manda una ForzaInsufficienteException
        giocatore.getPartita().getCampoDaGioco().getTabellone().getPalazzoDelConsiglio().arrivaGiocatore(familiareSelezionato);

        //avviso i giocatori della mossa
        for (GiocatoreRemoto g: giocatore.getPartita().getGiocatori()){
            g.spostatoFamiliarePalazzoDelConsiglio(familiareSelezionato.getColoreDado(), familiareSelezionato.getGiocatore().getId());
        }

        //faccio scegliere al giocatore il privilegio del consiglio
        giocatore.scegliPergamena();

        //avviso il giocatore dell'incremento delle risorse
        giocatore.risorseIncrementate(giocatore.getGiocatore().getPietra(), giocatore.getGiocatore().getLegna(), giocatore.getGiocatore().getServitori(),giocatore.getGiocatore().getMonete(), giocatore.getGiocatore().getPuntiMilitari(), giocatore.getGiocatore().getPuntiFede(), giocatore.getGiocatore().getPuntiVittoria());

        //deseleziono il familiare
        familiareSelezionato.setDisponibile(false);
        deselezionaFamiliare();

        //passo al prossimo giocatore
        giocatore.getPartita().passaMossa(giocatore);

    }


    public void spostaFamiliareZonaProduzione(int zona) throws RemoteException, ForzaInsufficienteException, TurnoException, ZonaOccupataExcepion {
        giocatore.getPartita().mioTurno(giocatore.getGiocatore());

        //devo attivare gli effetti delle carte personaggio per eventuali aumenti di forza
        attivaEffettiPersonaggi(4);

        //attivo le scomuniche per eventuali diminuzioni di forza
        attivaScomuniche(4,"");

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


    public void spostaFamiliareZonaRaccolto(int zona) throws RemoteException, TurnoException, ForzaInsufficienteException, ZonaOccupataExcepion {
        giocatore.getPartita().mioTurno(giocatore.getGiocatore());

        //devo attivare gli effetti delle carte personaggio per eventuali aumenti di forza
        attivaEffettiPersonaggi(5);

        //attivo le scomuniche per eventuali diminuzioni di forza
        attivaScomuniche(5,"");

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


    public synchronized void sceltaScomunica(boolean appoggiaChiesa) throws RemoteException {
        if (appoggiaChiesa) {
            giocatore.getGiocatore().setPuntiVittoria(giocatore.getGiocatore().getPuntiVittoria()+giocatore.getGiocatore().getPuntiFede());
            giocatore.getGiocatore().setPuntiFede(0);
        } else {
            giocatore.getPartita().getCampoDaGioco().getTabellone().getVaticano().scomunica(giocatore.getGiocatore(), giocatore.getPartita().getPeriodo());
            for(GiocatoreRemoto g: giocatore.getPartita().getGiocatori()){
                g.giocatoreScomunicato(giocatore.getGiocatore().getId(), giocatore.getPartita().getPeriodo()-1);
            }
        }
        //avviso il giocatore dell'incremento delle risorse
        giocatore.risorseIncrementate(giocatore.getGiocatore().getPietra(), giocatore.getGiocatore().getLegna(), giocatore.getGiocatore().getServitori(),giocatore.getGiocatore().getMonete(), giocatore.getGiocatore().getPuntiMilitari(), giocatore.getGiocatore().getPuntiFede(), giocatore.getGiocatore().getPuntiVittoria());

    }


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

    public synchronized void sceltaPergamena(int scelta) throws RemoteException {
        Giocatore giocatoreGioco=giocatore.getGiocatore();
        switch (scelta){
            case 0: giocatoreGioco.setLegna(giocatoreGioco.getLegna()+1);
                giocatoreGioco.setPietra(giocatoreGioco.getPietra()+1);
                break;
            case 1: giocatoreGioco.setServitori(giocatoreGioco.getServitori()+2);
                break;
            case 2: giocatoreGioco.setMonete(giocatoreGioco.getMonete()+2);
                break;
            case 3: giocatoreGioco.setPuntiMilitari(giocatoreGioco.getPuntiMilitari()+2);
                break;
            case 4: giocatoreGioco.setPuntiFede(giocatoreGioco.getPuntiFede()+1);
                break;
        }

        giocatore.risorseIncrementate(giocatoreGioco.getPietra(),giocatoreGioco.getLegna(),giocatoreGioco.getServitori(),giocatoreGioco.getMonete(),giocatoreGioco.getPuntiMilitari(),giocatoreGioco.getPuntiFede(),giocatoreGioco.getPuntiVittoria());
    }

    /**
     * prende la carta che c'è nel piano x della torre y, la aggiunge nella lista del giocatore e la rimuove dal piano
     * se il giocatore non ha risorse a sufficienza viene propagata l'eccezione
     * @param numeroTorre
     * @param numeroPiano
     * @throws RisorseInsufficientiException
     */
    /*private void prendiCarta(int numeroTorre, int numeroPiano) throws RisorseInsufficientiException {
        if(numeroTorre==0) {
            CartaTerritorio cartaTerritorio = null;
            cartaTerritorio=(CartaTerritorio) giocatore.getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).getPiano(numeroPiano).getCartaSviluppo();
            giocatore.getGiocatore().aggiungiTerritorio(cartaTerritorio);
            giocatore.getPartita().getCampoDaGioco().getTabellone().getTorre(numeroTorre).getPiano(numeroPiano).setCartaSviluppo(null);
            cartaTerritorio.attivaEffettoRapido(familiareSelezionato);
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
            cartaPersonaggio.attivaEffettoRapido(familiareSelezionato);
        }

    }

    public void esci(int id) {
        GiocatoreRemoto gioc=null;
        System.out.println("il giocatore "+id+" ha abbandonato");
        System.out.println("ci sono: "+giocatore.getPartita().getGiocatori().size()+" giocatori");

        //rimuovo il giocatore con l'id
        for(GiocatoreRemoto g: giocatore.getPartita().getGiocatori()){
            if(g.getGiocatore().getId()==id) {
                gioc=g;
                System.out.println("elimino il giocatore " + gioc.getGiocatore().getId() + " dalla lista");
            }
        }

        giocatore.getPartita().getGiocatori().remove(gioc);


        System.out.println("ci sono: "+giocatore.getPartita().getGiocatori().size()+" giocatori");
        //avviso i giocatori rimanenti
        for(GiocatoreRemoto g: giocatore.getPartita().getGiocatori()){

            try {
                System.out.println("avviso il giocatore " + g.getGiocatore().getId());
                g.finePartita(null, null, null);
                System.out.println("avvisato il giocatore " + g.getGiocatore().getId());

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

}
*/
