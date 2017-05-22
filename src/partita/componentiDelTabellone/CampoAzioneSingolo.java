package partita.componentiDelTabellone;

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
    private String effetto;

    public CampoAzioneSingolo(int costo, boolean haEffetto, String effetto){
        this.costo=costo;
        this.haEffetto=haEffetto;
        this.effetto=effetto;
        this.occupato=false;
    }

    public void setFamiliare(Familiare familiare) throws ZonaOccupataExcepion, ForzaInsufficienteException {
        verificaDisponibilitàForza(familiare.getForza());
        if(this.familiare==null)
            this.familiare = familiare;
        else
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
        return effetto;
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
