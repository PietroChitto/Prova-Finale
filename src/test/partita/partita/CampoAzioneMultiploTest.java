package test.partita.partita;

import org.junit.Test;
import partita.componentiDelTabellone.CampoAzioneMultiplo;
import partita.componentiDelTabellone.Familiare;
import partita.eccezioniPartita.ForzaInsufficienteException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Pietro on 22/05/2017.
 */
public class CampoAzioneMultiploTest {
    @Test
    public void getFamiliari() throws Exception {
    }

    @Test
    public void getCosto() throws Exception {
    }

    @Test
    public void aggiungiFamiliare() throws ForzaInsufficienteException {
        CampoAzioneMultiplo cam= new CampoAzioneMultiplo(1);
        Familiare f=new Familiare(false, 0, 3, "nero", true, null);
        cam.aggiungiFamiliare(f);
        assertEquals(cam.getFamiliari().size(), 1);
    }

    @Test
    public void svuotaCampoAzione() throws Exception {
        CampoAzioneMultiplo cam= new CampoAzioneMultiplo(1);
        Familiare f=new Familiare(false, 0, 3, "nero", true, null);
        cam.aggiungiFamiliare(f);
        Familiare f1=new Familiare(false, 0, 3, "nero", true, null);
        cam.aggiungiFamiliare(f1);
        Familiare f2=new Familiare(false, 0, 3, "nero", true, null);
        cam.aggiungiFamiliare(f2);
        assertEquals(cam.getFamiliari().size(), 3);
        cam.svuotaCampoAzione();
        assertEquals(cam.getFamiliari().size(),0);
    }

}