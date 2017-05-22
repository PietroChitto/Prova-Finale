package partita.componentiDelTabellone;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Pietro on 22/05/2017.
 */
public class MercatoTest {
    @Test
    public void controlloCampoAzioneOccupato() throws Exception {
    }

    @Test
    public void arrivaGiocatore() throws Exception {
        Mercato m=new Mercato(false);
        Giocatore g = new Giocatore(1);
        g.setMonete(6);
        g.setPuntiVittoria(8);
        g.setPuntiFede(9);
        g.setPuntiMilitari(6);
        g.setServitori(7);
        g.setLegna(2);
        g.setPietra(5);
        Familiare f= new Familiare(false,1,4,"nero", true,g);
        m.arrivaGiocatore(f,1);
        assertEquals(11,g.getMonete());
        m.arrivaGiocatore(f,2);
        assertEquals(12,g.getServitori());
        m.arrivaGiocatore(f,3);
        assertEquals(13,g.getMonete());
        assertEquals(9,g.getPuntiMilitari());
    }

}