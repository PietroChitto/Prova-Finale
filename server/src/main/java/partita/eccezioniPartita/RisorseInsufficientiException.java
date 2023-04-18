package partita.eccezioniPartita;
/**
 * questa eccezione viene lanciata se il giocatore prova a fare una mossa su una torre ma non ha abbastanza risorse per comprare la carta
 */

/**
 * Created by Pietro on 18/05/2017.
 */
public class RisorseInsufficientiException extends Exception {
    public RisorseInsufficientiException(){super();}
    public RisorseInsufficientiException(String s){super(s);}
}
