package partita.componentiDelTabellone;

import partita.carteDaGioco.effetti.Effetto;
import partita.carteDaGioco.effetti.EffettoIncrementa;
import partita.eccezioniPartita.ForzaInsufficienteException;
import partita.eccezioniPartita.ZonaOccupataExcepion;

/**
 * Created by Pietro on 12/05/2017.
 */
public class CampoAzioneSingolo {
    private int costo;
    private boolean haEffetto;
    private Familiare familiare;
    private boolean occupato;
    private String codEff;
    private EffettoIncrementa effetto;

    public CampoAzioneSingolo(int costo, boolean haEffetto, String codEff){
        this.costo=costo;
        this.haEffetto=haEffetto;
        this.codEff=codEff;
        this.occupato=false;
        effetto=new EffettoIncrementa();
    }

    public void setFamiliare(Familiare familiare) throws ZonaOccupataExcepion, ForzaInsufficienteException {
        verificaDisponibilitàForza(familiare.getForza());
        if(this.familiare==null) {
            this.familiare = familiare;
            effetto.attivaEffetto(codEff, familiare.getGiocatore(), null);
            this.occupato=true;
        }else
            throw new ZonaOccupataExcepion();
    }

    public Familiare getFamiliare() {
        return familiare;
    }

    public boolean isHaEffetto() {
        return haEffetto;
    }

    public boolean isOccupato() {
        return occupato;
    }

    public int getCosto() {
        return costo;
    }

    public String getEffetto() {
        return codEff;
    }

    public void setOccupato(boolean occupato) {
        this.occupato = occupato;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public void svuotaCampoAzione(){
        familiare=null;
    }

    private void verificaDisponibilitàForza(int forza) throws ForzaInsufficienteException {
        if(!(forza>=costo)) throw new ForzaInsufficienteException();
    }
}
