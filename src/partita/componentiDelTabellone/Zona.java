package partita.componentiDelTabellone;

import partita.carteDaGioco.CartaEdificio;
import partita.carteDaGioco.CartaTerritorio;
import partita.eccezioniPartita.ForzaInsufficienteException;
import partita.eccezioniPartita.ZonaOccupataExcepion;

/**
 * Created by Pietro on 10/05/2017.
 */
public class Zona {

    //indica se è la zona di produzione o di raccolto
    private boolean produzione;

    //indica se ci sono almeno 3 giocatori per coprire certe zone
    private boolean treGiocatori;

    private CampoAzioneSingolo campoAzioneSinogolo;

    private CampoAzioneMultiplo campoAzioneMultiplo;

    public Zona(boolean produzione, boolean treGiocatori){
        this.produzione=produzione;
        this.treGiocatori=treGiocatori;
        creaCampiAzione();
    }

    private void creaCampiAzione(){
        if(treGiocatori){
            campoAzioneMultiplo=new CampoAzioneMultiplo(1);
        }
        if(produzione){
            campoAzioneSinogolo=new CampoAzioneSingolo(1,true,"0P0L0S0M0E0F0V");
        }
        else{
            campoAzioneSinogolo=new CampoAzioneSingolo(1,true,"0P0L0S0M0E0F0V");
        }
    }

    public void svuotaZona(){
        campoAzioneSinogolo.svuotaCampoAzione();
        campoAzioneMultiplo.svuotaCampoAzione();
    }

    public CampoAzioneMultiplo getCampoAzioneMultiplo() {
        return campoAzioneMultiplo;
    }

    public CampoAzioneSingolo getCampoAzioneSinogolo() {
        return campoAzioneSinogolo;
    }

    public void arrivaGiocatore(Familiare f) throws ForzaInsufficienteException{
        try {
            campoAzioneSinogolo.setFamiliare(f);
        } catch (ZonaOccupataExcepion zonaOccupataExcepion) {
            campoAzioneMultiplo.aggiungiFamiliare(f);
            //nel campo azione multiplo c'è un malus di -3 sulla forza del familiare
            f.setForza(f.getForza()-3);
        }

        if(produzione==false) {
            for (CartaTerritorio c : f.getGiocatore().getCarteTerritorio()) {
                if(f.getForza()>c.getCostoAttivazioneEffettoPermanente())
                    c.attivaEffettoPermanente(f);
            }
        }
        else{
            for (CartaEdificio c : f.getGiocatore().getCarteEdificio()) {
                if(f.getForza()>c.getCostoAttivazioneEffettoPermanente())
                    c.attivaEffettoPermanente(f);
            }
        }

    }
}
