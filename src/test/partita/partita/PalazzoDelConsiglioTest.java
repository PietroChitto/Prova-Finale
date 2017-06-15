package test.partita.partita;

import org.junit.Test;
import partita.componentiDelTabellone.Giocatore;

import java.util.ArrayList;

/**
 * Created by Pietro on 05/06/2017.
 */
public class PalazzoDelConsiglioTest {
    @Test
    public void arrivaGiocatore() throws Exception {
    }

    @Test
    public void calcoaTurnoSuccessivo() throws Exception {
        Giocatore g0=new Giocatore(0);
        Giocatore g1=new Giocatore(1);
        Giocatore g2=new Giocatore(2);
        Giocatore g3=new Giocatore(3);

        ArrayList<Giocatore> ordineTurnoCorrente = new ArrayList<>();
        ordineTurnoCorrente.add(g0);
        ordineTurnoCorrente.add(g1);
        ordineTurnoCorrente.add(g2);
        ordineTurnoCorrente.add(g3);

        ArrayList<Giocatore> ordineArrivo = new ArrayList<>();
        ordineArrivo.add(g2);
        ordineArrivo.add(g1);

        ArrayList<Giocatore> nuovoTurno=new ArrayList<>();

        nuovoTurno.addAll(ordineArrivo);
        ordineTurnoCorrente.removeAll(ordineArrivo);
        nuovoTurno.addAll(ordineTurnoCorrente);
        ordineArrivo.clear();

        System.out.println("il nuovo turno è: ");
        for (Giocatore g: nuovoTurno){
            System.out.println("giocatore "+ g.getId());
        }
    }

}