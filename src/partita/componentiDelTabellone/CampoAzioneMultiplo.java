package partita.componentiDelTabellone;

import partita.eccezioniPartita.ForzaInsufficienteException;

import java.util.ArrayList;

/**
 * Created by Pietro on 15/05/2017.
 */
public class CampoAzioneMultiplo {

    private ArrayList<Familiare> familiari;
    private int costo;

    public CampoAzioneMultiplo(int costo){
        this.costo=costo;
        familiari=new ArrayList<Familiare>();
    }

    public ArrayList<Familiare> getFamiliari() {
        return familiari;
    }

    public int getCosto() {
        return costo;
    }

    public void aggiungiFamiliare(Familiare f) throws ForzaInsufficienteException {
        verificaDisponibilitàForza(f.getForza());
        familiari.add(f);
    }

    public void svuotaCampoAzione(){
        familiari.clear();
    }

    private void verificaDisponibilitàForza(int forza) throws ForzaInsufficienteException {
        if(!(forza>=costo)) throw new ForzaInsufficienteException();
    }
}
