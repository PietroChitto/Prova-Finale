package test.partita.partita;

import org.junit.Test;
import partita.componentiDelTabellone.Familiare;
import partita.componentiDelTabellone.Giocatore;
import partita.componentiDelTabellone.PalazzoDelConsiglio;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by Pietro on 05/06/2017.
 */
public class PalazzoDelConsiglioTest {
    @Test
    public void arrivaGiocatore() throws Exception {
    }

    @Test
    public void calcoaTurnoSuccessivo() throws Exception {
        PalazzoDelConsiglio palazzoDelConsiglio=new PalazzoDelConsiglio(2);
        Giocatore g0=new Giocatore(0);
        Giocatore g1=new Giocatore(1);
        Giocatore g2=new Giocatore(2);
        Giocatore g3=new Giocatore(3);

        ArrayList<Giocatore> ordineTurnoCorrente = new ArrayList<>();
        ordineTurnoCorrente.add(g0);
        ordineTurnoCorrente.add(g1);
        ordineTurnoCorrente.add(g2);
        ordineTurnoCorrente.add(g3);

        palazzoDelConsiglio.arrivaGiocatore(new Familiare(false,0,2,"",true,g0));
        palazzoDelConsiglio.arrivaGiocatore(new Familiare(false,2,2,"",true,g2));
        palazzoDelConsiglio.arrivaGiocatore(new Familiare(false,3,2,"",true,g3));
        palazzoDelConsiglio.arrivaGiocatore(new Familiare(false,3,2,"",true,g3));
        palazzoDelConsiglio.arrivaGiocatore(new Familiare(false,1,2,"",true,g1));


        ArrayList<Giocatore> nuovoTurno;

        nuovoTurno=palazzoDelConsiglio.calcoaTurnoSuccessivo(ordineTurnoCorrente);

        assertEquals(g0,nuovoTurno.get(0));
        assertEquals(g2,nuovoTurno.get(1));
        assertEquals(g3,nuovoTurno.get(2));
        assertEquals(g1,nuovoTurno.get(3));

        assertEquals(4,nuovoTurno.size());

        for (Giocatore g: nuovoTurno){
            System.out.println(g.getId());
        }
    }

}