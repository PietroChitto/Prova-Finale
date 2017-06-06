package partita.carteDaGioco;

import partita.carteDaGioco.effetti.Effetto;
import partita.componentiDelTabellone.Familiare;
import partita.componentiDelTabellone.Giocatore;
import partita.componentiDelTabellone.Tabellone;
import partita.eccezioniPartita.ForzaInsufficienteException;
import partita.eccezioniPartita.RisorseInsufficientiException;

/**
 * Created by william on 10/05/2017.
 */
public class CartaEdificio extends CartaSviluppo {

    private String codiceEffP;
    private String codiceEffR;
    private CostoCarta costo;
    private Effetto effettoRapido;
    private Effetto effettoPermanente;
    private int costoAttivazioneEffettoPermanente;

    public CartaEdificio(CostoCarta costo, int costoAttivazioneEffettoPermanente, int periodo, String nome, boolean effettoRapido, boolean effettoPermanente, String codiceEffP, String codiceEffR, Effetto effettoR, Effetto effettoP){

        super(periodo, nome, effettoRapido, effettoPermanente, "edificio");
        this.costo=costo;
        this.costoAttivazioneEffettoPermanente=costoAttivazioneEffettoPermanente;
        this.codiceEffP = codiceEffP;
        this.codiceEffR = codiceEffR;
        this.effettoRapido=effettoR;
        this.effettoPermanente=effettoP;
    }

    /**
     * controlla se la carta ha un effetto rapido e si occupa di chiamare il metodo che implementa l'effetto
     * @param f: familiare del giocatore possessore della carta
     */
    public void attivaEffettoRapido(Familiare f) throws RisorseInsufficientiException {
        if(super.getEffettoRapido()){
            effettoRapido.attivaEffetto(codiceEffR, f, 0);
        }
    }

    /**
     * controlla se la carta ha un effetto permanente e si occupa dell'attivazione del metodo che implementa l'effetto
     * @param f: familiare del giocatore possessore della carta
     */
    public void attivaEffettoPermanente(Familiare f) throws ForzaInsufficienteException {
        if(super.getEffettoPermanente()){
            verificaDisponibilitàForza(f.getForza());
            //cerca metodo permanente
        }
    }

    /**
     * verifica se il familiare che attiva le carte edificio ha la forza necessaria per attivare l'effetto
     * @param forza: forza del familiare che vuole attivare l'effetto
     * @return true se la forza è maggiore o uguale; false altrimenti
     */
    private void verificaDisponibilitàForza(int forza) throws ForzaInsufficienteException{
        if(!(forza>=costoAttivazioneEffettoPermanente)) throw new ForzaInsufficienteException();
    }

    public void confrontaCosto(Giocatore g) throws RisorseInsufficientiException{
        if(!(costo.getCostoLegna()<=g.getLegna() && costo.getCostoMonete()<=g.getMonete()
                && costo.getCostoPietra()<=g.getPietra() && costo.getCostoServitori()<=g.getServitori()
                && costo.getCostoPuntiMilitari()<=g.getPuntiMilitari()) ) {
            throw new RisorseInsufficientiException();
        }

    }

    public int getCostoAttivazioneEffettoPermanente() {
        return costoAttivazioneEffettoPermanente;
    }

    public void setCostoAttivazioneEffettoPermanente(int costoAttivazioneEffettoPermanente) {
        this.costoAttivazioneEffettoPermanente = costoAttivazioneEffettoPermanente;
    }

    public CostoCarta getCosto(){
        return costo;
    }

}
