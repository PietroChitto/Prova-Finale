package test.partita.partita;

import org.junit.Test;
import partita.carteDaGioco.CostoCarta;
import partita.componentiDelTabellone.Familiare;
import partita.componentiDelTabellone.Giocatore;
import partita.componentiDelTabellone.Mercato;

import static org.junit.Assert.assertEquals;

/**
 * Created by Pietro on 22/05/2017.
 */
public class MercatoTest {
    @Test
    public void controlloCampoAzioneOccupato() throws Exception {
        String costoDaCifrare="2P0L0S0M0E0F0V";
        int[] costi=new int[5];
        for(int i=0; i<5;i++){
            costi[i]=costoDaCifrare.charAt(i*2)-48;
            System.out.println("valore intero"+costi[i]);
        }
        CostoCarta costo= new CostoCarta(costi[0], costi[1], costi[2], costi[3], costi[4]);
        System.out.println("pietra: "+costo.getCostoPietra()+"\nlegna :"+costo.getCostoLegna()+"\nservi: "+costo.getCostoServitori()+"\nmonete: "+costo.getCostoMonete()+"\nmilitari: "+costo.getCostoPuntiMilitari());
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