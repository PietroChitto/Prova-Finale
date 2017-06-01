package partita.carteDaGioco;

import partita.carteDaGioco.effetti.Effetto;
import partita.componentiDelTabellone.Familiare;
import partita.componentiDelTabellone.Giocatore;
import partita.eccezioniPartita.ForzaInsufficienteException;
import partita.eccezioniPartita.RisorseInsufficientiException;

/**
 * Created by william on 10/05/2017.
 */
public class CartaTerritorio extends CartaSviluppo{

    private String codiceEffR;
    private String codiceEffP;
    private Effetto effettoPermanente;
    private Effetto effettoRapido;
    private int costoAttivazioneEffettoPermanente;

    public CartaTerritorio(int periodo, String nome, boolean effettoRapido, boolean effettoPermanente, String codiceEffR, String codiceEffP, Effetto effettoP, Effetto effettoR, int costoAttivazioneEffettoPermanente){

        super(periodo, nome, effettoRapido, effettoPermanente, "territorio");
        this.codiceEffR = codiceEffR;
        this.codiceEffP = codiceEffP;
        this.effettoPermanente=effettoP;
        this.effettoRapido=effettoR;
        this.costoAttivazioneEffettoPermanente=costoAttivazioneEffettoPermanente;

    }

    /**
     * controlla se la carta ha un effetto rapido e si occupa di chiamare il metodo che implementa l'effetto
     * @param g: giocatore possessore della carta
     */
    public void attivaEffettoRapido(Giocatore g){
        if(super.getEffettoRapido()){
            try {
                effettoRapido.attivaEffetto(codiceEffR, g,null);
            } catch (RisorseInsufficientiException e) {
                e.printStackTrace();
            }
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
            try {
                effettoPermanente.attivaEffetto(codiceEffP,f.getGiocatore(),null);
            } catch (RisorseInsufficientiException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * verifica se il familiare che attiva le carte edificio ha la forza necessaria per attivare l'effetto
     * @param forza: forza del familiare che vuole attivare l'effetto
     * @return true se la forza è maggiore o uguale; false altrimenti
     */
    public void verificaDisponibilitàForza(int forza) throws ForzaInsufficienteException{
        if(!(forza>=costoAttivazioneEffettoPermanente)) throw new ForzaInsufficienteException();
    }

    public void setCostoAttivazioneEffettoPermanente(int costoAttivazioneEffettoPermanente) {
        this.costoAttivazioneEffettoPermanente = costoAttivazioneEffettoPermanente;
    }

    public int getCostoAttivazioneEffettoPermanente() {
        return costoAttivazioneEffettoPermanente;
    }
}
