package partita.eccezioniPartita;
/**
 * questa eccezione viene lanciata un giocatore prova a fare una mossa ma non è il suo turno
 */

/**
 * Created by Pietro on 18/05/2017.
 */
public class TurnoException extends Exception {
    public TurnoException(){super();}
    public TurnoException(String s){super(s);}
}
