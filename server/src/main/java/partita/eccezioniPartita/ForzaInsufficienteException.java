package partita.eccezioniPartita;
/**
 * questa eccezione viene lanciata se il giocatore prova a fare una mossa ma il familiare non ha la forza sufficiente per farla
 */

/**
 * Created by Pietro on 18/05/2017.
 */
public class ForzaInsufficienteException extends Exception {
    public ForzaInsufficienteException(){super();}
    public ForzaInsufficienteException(String s){super(s);}
}
