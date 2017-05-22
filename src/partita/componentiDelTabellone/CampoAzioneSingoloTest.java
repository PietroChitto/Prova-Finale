package partita.componentiDelTabellone;

import org.junit.Test;
import partita.eccezioniPartita.ForzaInsufficienteException;
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
    public void getFamiliare() throws Exception {
    }

    @Test
    public void isHaEffetto() throws Exception {
    }

    @Test
    public void isOccupato() throws Exception {
    }

    @Test
    public void getCosto() throws Exception {
    }

    @Test
    public void getEffetto() throws Exception {
    }

    @Test
    public void setOccupato() throws Exception {
    }

    @Test
    public void setCosto() throws Exception {
    }

    @Test
    public void svuotaCampoAzione() throws Exception {
        Familiare f =new Familiare(false,0,5,"nero",true,null);
        CampoAzioneSingolo cas=new CampoAzioneSingolo(3,false,"");
        cas.setFamiliare(f);
        assertEquals(cas.getFamiliare(),f);
        //assertTrue(cas.isOccupato());
        cas.svuotaCampoAzione();
        assertFalse(cas.isOccupato());
        assertEquals(null,cas.getFamiliare());

    }

}