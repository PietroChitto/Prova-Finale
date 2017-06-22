package test.partita.partita;

import org.junit.Test;
import partita.componentiDelTabellone.CampoAzioneSingolo;
import partita.componentiDelTabellone.Familiare;
import partita.eccezioniPartita.ZonaOccupataExcepion;

import static org.junit.Assert.*;

/**
 * Created by Pietro on 22/05/2017.
 */
public class CampoAzioneSingoloTest {
    @Test(expected = ZonaOccupataExcepion.class)
    public void setFamiliare() throws Exception {
        Familiare f =new Familiare(false,0,5,"nero",true,null);
        CampoAzioneSingolo cas=new CampoAzioneSingolo(3,false,"");
        cas.setFamiliare(f);
        assertTrue(cas.isOccupato());
        Familiare f1=new Familiare(false,0,5,"nero",true,null);
        cas.setFamiliare(f1);
    }



    @Test
    public void svuotaCampoAzione() throws Exception {
        Familiare f =new Familiare(false,0,5,"nero",true,null);
        CampoAzioneSingolo cas=new CampoAzioneSingolo(3,false,"");
        cas.setFamiliare(f);
        assertEquals(cas.getFamiliare(),f);
        cas.svuotaCampoAzione();
        assertFalse(cas.isOccupato());
        assertEquals(null,cas.getFamiliare());

    }

}