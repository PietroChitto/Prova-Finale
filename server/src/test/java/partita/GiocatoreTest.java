package test.java.partita;

import org.junit.Test;
import partita.componentiDelTabellone.Giocatore;

import static org.junit.Assert.assertEquals;

/**
 * Created by Pietro on 22/05/2017.
 */
public class GiocatoreTest {
    @Test
    public void incrementaRisorse() throws Exception {
        Giocatore g = new Giocatore(1);
        g.setMonete(2);
        g.setPuntiVittoria(4);
        g.setPuntiFede(3);
        g.setPuntiMilitari(6);
        g.setServitori(2);
        g.setLegna(2);
        g.setPietra(5);
        int[] incRis=new int[7];
        for(int i=0; i<7;i++){
            incRis[i]=i;
        }
        g.incrementaRisorse(incRis);
        assertEquals(5,g.getPietra());
        assertEquals(3,g.getLegna());
        assertEquals(4,g.getServitori());
        assertEquals(5,g.getMonete());
        assertEquals(10,g.getPuntiMilitari());
        assertEquals(8,g.getPuntiFede());
        assertEquals(10,g.getPuntiVittoria());

    }

    @Test
    public void decrementaRisorse() throws Exception {
        Giocatore g = new Giocatore(1);
        g.setMonete(6);
        g.setPuntiVittoria(8);
        g.setPuntiFede(9);
        g.setPuntiMilitari(6);
        g.setServitori(7);
        g.setLegna(2);
        g.setPietra(5);
        int[] incRis=new int[7];
        for(int i=0; i<7;i++){
            incRis[i]=i;
        }
        g.decrementaRisorse(incRis);
        assertEquals(5,g.getPietra());
        assertEquals(1,g.getLegna());
        assertEquals(5,g.getServitori());
        assertEquals(3,g.getMonete());
        assertEquals(2,g.getPuntiMilitari());
        assertEquals(4,g.getPuntiFede());
        assertEquals(2,g.getPuntiVittoria());
    }



}