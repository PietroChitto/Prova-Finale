package test.partita.partita;

import org.junit.Before;
import org.junit.Test;
import partita.Partita;
import partita.componentiDelTabellone.Giocatore;
import partita.eccezioniPartita.TurnoException;
import server.GiocatoreRemoto;
import server.rmiServer.GiocatoreRMI;
import server.socketServer.GiocatoreSocket;

import java.net.Socket;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Pietro on 15/06/2017.
 */
public class PartitaTest {
    private final int N_GIOCATORI=2;
    private Partita p;
    private GiocatoreRemoto giocatoreRMI;
    private GiocatoreRemoto giocatoreSocket;
    @Before
    public void setUp() throws Exception {
        p= new Partita(N_GIOCATORI);
        giocatoreRMI=new GiocatoreRMI();
        giocatoreRMI.setUsername("Giocatore1");
        giocatoreSocket=new GiocatoreSocket(new Socket());
        giocatoreSocket.setUsername("Giocatore2");
        Giocatore g1=new Giocatore(0);
        Giocatore g2=new Giocatore(1);
        giocatoreRMI.setGiocatore(g1);
        giocatoreSocket.setGiocatore(g2);
        p.addGiocatore(giocatoreRMI);
        p.addGiocatore(giocatoreSocket);
        giocatoreRMI.setPartita(p);
        giocatoreSocket.setPartita(p);
    }

    @Test
    public void getGiocatori() throws Exception {

        ArrayList<GiocatoreRemoto> giocatori;
        giocatori =p.getGiocatori();

        assertEquals("Giocatore1",giocatori.get(0).getUsername());
        assertEquals("Giocatore2",giocatori.get(1).getUsername());
    }

    @Test
    public void addGiocatore() throws Exception {

        assertEquals(2,p.getGiocatori().size());

        GiocatoreRemoto giocatore3=new GiocatoreRMI();
        p.addGiocatore(giocatore3);

        assertEquals(2,p.getGiocatori().size());
    }

    @Test
    public void iniziaPartita() throws Exception {
        p.iniziaPartita();
        assertFalse(p.getCampoDaGioco()==null);
        assertTrue(p.getCampoDaGioco().getCarteScomunica().size()==3);
        assertTrue(p.getCampoDaGioco().getMazzoEdificio().size()==20);
        assertTrue(p.getCampoDaGioco().getMazzoImprese().size()==20);
        assertTrue(p.getCampoDaGioco().getMazzoPersonaggi().size()==20);
        assertTrue(p.getCampoDaGioco().getMazzoTerritorio().size()==20);
        assertTrue(p.getCampoDaGioco().getTabellone()!=null);
    }

    @Test
    public void getCampoDaGioco() throws Exception {
    }

    @Test
    public void mioTurno() throws Exception {
        p.iniziaPartita();
        p.mioTurno(giocatoreRMI.getGiocatore());
    }

    @Test(expected = TurnoException.class)
    public void mioTurno2() throws Exception {
        p.iniziaPartita();
        p.mioTurno(giocatoreSocket.getGiocatore());
    }

    @Test
    public void getOrdineTurno() throws Exception {

    }

    @Test
    public void getPeriodo() throws Exception {

    }

    @Test
    public void getValoreDadoArancio() throws Exception {
        p.setValoreDadoArancio(4);
        assertEquals(4,p.getValoreDadoArancio());
    }

    @Test
    public void getValoreDadoBianco() throws Exception {
        p.setValoreDadoBianco(4);
        assertEquals(4,p.getValoreDadoBianco());
    }

    @Test
    public void getValoreDadoNero() throws Exception {
        p.setValoreDadoNero(4);
        assertEquals(4,p.getValoreDadoNero());
    }

    @Test
    public void setPeriodo() throws Exception {
    }

    /**
     * faccio evolvere la partita e guardo se avviene correttamente il cambio periodo
     * @throws Exception
     */
    @Test
    public void passaMossa() throws Exception {
        assertEquals(2,p.getGiocatori().size());
        p.iniziaPartita();

        assertEquals(1,p.getPeriodo());
        //per ogni giocatore faccio 8 mosse a periodo
        for(int i=0; i<(p.getGiocatori().size()/2)*8;i++){
            assertEquals(1,p.getPeriodo());
            p.passaMossa(giocatoreRMI);
            assertEquals(1,p.getPeriodo());
            p.passaMossa(giocatoreSocket);
        }

        assertEquals(2,p.getPeriodo());
        //per ogni giocatore faccio 8 mosse a periodo
        for(int i=0; i<(p.getGiocatori().size()/2)*8;i++){
            assertEquals(2,p.getPeriodo());
            p.passaMossa(giocatoreRMI);
            assertEquals(2,p.getPeriodo());
            p.passaMossa(giocatoreSocket);
        }

        assertEquals(3,p.getPeriodo());
        //per ogni giocatore faccio 8 mosse a periodo
        for(int i=0; i<(p.getGiocatori().size()/2)*8;i++){
            assertEquals(3,p.getPeriodo());
            p.passaMossa(giocatoreRMI);
            assertEquals(3,p.getPeriodo());
            p.passaMossa(giocatoreSocket);
        }
    }

    @Test
    public void setValoreDadoArancio() throws Exception {
    }

    @Test
    public void setValoreDadoBianco() throws Exception {
    }

    @Test
    public void setValoreDadoNero() throws Exception {
    }

    @Test
    public void setDadiTirati() throws Exception {
    }

    @Test
    public void dadiTirati() throws Exception {
    }

}