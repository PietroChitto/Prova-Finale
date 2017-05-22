package partita.eccezioniPartita;
/**
 * questa eccezione viene lanciata se un giocatore prova a posizionare un suo familiare in una zona occupata
 */

/**
 * Created by Pietro on 18/05/2017.
 */
public class ZonaOccupataExcepion extends Exception {
    public ZonaOccupataExcepion(){super();}
    public ZonaOccupataExcepion(String s){super(s);}
}
