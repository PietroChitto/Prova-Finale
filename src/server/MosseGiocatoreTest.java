package server;

import org.junit.Before;
import org.junit.Test;
import partita.Partita;
import partita.componentiDelTabellone.CampoAzioneSingolo;
import partita.componentiDelTabellone.Familiare;
import partita.componentiDelTabellone.Giocatore;
import partita.eccezioniPartita.*;
import server.rmiServer.GiocatoreRMI;
import server.socketServer.GiocatoreSocket;

import java.net.Socket;

import static org.junit.Assert.assertEquals;

/**
 * Created by Pietro on 15/06/2017.
 */
public class MosseGiocatoreTest {
    private final int N_GIOCATORI=2;
    private Partita partita;
    private GiocatoreRemoto giocatore1;
    private GiocatoreRemoto giocatore2;


    @Before
    public void inizializzaPartita() throws Exception{
        partita=new Partita(N_GIOCATORI);
        giocatore1=new GiocatoreRMI();
        giocatore2=new GiocatoreSocket(new Socket());


        giocatore1.setGiocatore(new Giocatore(0));
        giocatore2.setGiocatore(new Giocatore(1));

        giocatore1.setUsername("giocatore1");
        giocatore2.setUsername("giocatore2");

        partita.addGiocatore(giocatore1);
        partita.addGiocatore(giocatore2);

        giocatore1.setPartita(partita);
        giocatore2.setPartita(partita);

        partita.iniziaPartita();
    }

    @Test
    public void selezionaFamiliare() throws Exception {
        giocatore1.getMosse().tiraIDadi();
        giocatore1.getMosse().selezionaFamiliare("nero",giocatore1.getGiocatore().getId());
        Familiare f=giocatore1.getMosse().getFamiliareSelezionato();
        assertEquals("nero",f.getColoreDado());
        assertEquals(giocatore1.getGiocatore().getId(),f.getGiocatore().getId());
    }

    @Test(expected = DadiNonTiratiException.class)
    public void selezionaFamiliare1() throws Exception {
        giocatore1.getMosse().selezionaFamiliare("nero",giocatore1.getGiocatore().getId());
        Familiare f=giocatore1.getMosse().getFamiliareSelezionato();
        assertEquals("nero",f.getColoreDado());
        assertEquals(giocatore1.getGiocatore().getId(),f.getGiocatore().getId());
    }

    @Test(expected = TurnoException.class)
    public void selezionaFamiliare3() throws Exception {
        giocatore1.getMosse().tiraIDadi();
        giocatore2.getMosse().selezionaFamiliare("nero",giocatore1.getGiocatore().getId());
        Familiare f=giocatore2.getMosse().getFamiliareSelezionato();
        assertEquals("nero",f.getColoreDado());
        assertEquals(giocatore1.getGiocatore().getId(),f.getGiocatore().getId());
    }

    @Test
    public void deselezionaFamiliare() throws Exception {
        giocatore1.getMosse().tiraIDadi();
        giocatore1.getMosse().selezionaFamiliare("nero",giocatore1.getGiocatore().getId());
        giocatore1.deselezionaFamiliare();
        Familiare f=giocatore1.getMosse().getFamiliareSelezionato();
        assertEquals(null,f);
    }

    @Test
    public void spostaFamiliarePiano() throws Exception {
        giocatore1.getMosse().tiraIDadi();
        giocatore1.getMosse().selezionaFamiliare("nero",giocatore1.getGiocatore().getId());
        int[] risorse={10,10,10,10,10,10,10};
        giocatore1.getGiocatore().incrementaRisorse(risorse);
        Familiare f=giocatore1.getMosse().getFamiliareSelezionato();
        f.setForza(5);
        giocatore1.spostaFamiliarePiano(1,0);
        CampoAzioneSingolo cam=partita.getCampoDaGioco().getTabellone().getTorre(1).getPiano(0).getCampoAzione();

        assertEquals(cam.getFamiliare(),f);
    }

    @Test(expected = RisorseInsufficientiException.class)
    public void spostaFamiliarePiano1() throws Exception {
        giocatore1.getMosse().tiraIDadi();
        giocatore1.getMosse().selezionaFamiliare("nero",giocatore1.getGiocatore().getId());
        int[] risorse={10,10,10,10,10,10,10};
        giocatore1.getGiocatore().decrementaRisorse(risorse);
        Familiare f=giocatore1.getMosse().getFamiliareSelezionato();
        f.setForza(5);
        giocatore1.getMosse().spostaFamiliarePiano(2,1);
        CampoAzioneSingolo cam=partita.getCampoDaGioco().getTabellone().getTorre(1).getPiano(0).getCampoAzione();

        assertEquals(cam.getFamiliare(),null);
    }

    @Test(expected = ForzaInsufficienteException.class)
    public void spostaFamiliarePiano2() throws Exception {
        giocatore1.getMosse().tiraIDadi();
        giocatore1.getMosse().selezionaFamiliare("nero",giocatore1.getGiocatore().getId());
        int[] risorse={10,10,10,10,10,10,10};
        giocatore1.getGiocatore().incrementaRisorse(risorse);
        Familiare f=giocatore1.getMosse().getFamiliareSelezionato();
        f.setForza(0);
        giocatore1.getMosse().spostaFamiliarePiano(2,1);
        CampoAzioneSingolo cam=partita.getCampoDaGioco().getTabellone().getTorre(1).getPiano(0).getCampoAzione();

        assertEquals(cam.getFamiliare(),null);
    }

    @Test(expected = ZonaOccupataExcepion.class)
    public void spostaFamiliarePiano3() throws Exception {
        giocatore1.getMosse().tiraIDadi();
        giocatore1.getMosse().selezionaFamiliare("nero",giocatore1.getGiocatore().getId());
        int[] risorse={10,10,10,10,10,10,10};
        giocatore1.getGiocatore().incrementaRisorse(risorse);
        Familiare f=giocatore1.getMosse().getFamiliareSelezionato();
        f.setForza(5);
        giocatore1.getMosse().spostaFamiliarePiano(2,1);

        giocatore2.getMosse().selezionaFamiliare("nero", giocatore2.getGiocatore().getId());
        giocatore2.getGiocatore().incrementaRisorse(risorse);
        f=giocatore2.getMosse().getFamiliareSelezionato();
        f.setForza(5);
        giocatore2.getMosse().spostaFamiliarePiano(2,1);
    }

    @Test(expected = FamiliareNonSelezionatoExcepion.class)
    public void spostaFamiliarePiano4() throws Exception {
        assertEquals(null,giocatore1.getMosse().getFamiliareSelezionato());
        giocatore1.getMosse().tiraIDadi();
        assertEquals(null,giocatore1.getMosse().getFamiliareSelezionato());
        giocatore1.getMosse().spostaFamiliarePiano(1,0);
    }

    @Test
    public void spostaFamiliareMercato() throws Exception {

    }

    @Test
    public void spostaFamiliarePalazzoDelConsiglio() throws Exception {
    }

    @Test
    public void spostaFamiliareZonaProduzione() throws Exception {
    }

    @Test
    public void spostaFamiliareZonaRaccolto() throws Exception {
    }

    @Test
    public void tiraIDadi() throws Exception {
    }

    @Test
    public void sceltaScomunica() throws Exception {
    }

    @Test
    public void aumentaForzaFamiliare() throws Exception {
    }

    @Test
    public void saltaMossa() throws Exception {
    }

    @Test
    public void sceltaPergamena() throws Exception {
    }

}