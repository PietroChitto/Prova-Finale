package partita.carteDaGioco.effetti;
/**
 * questa interfaccia verrà implementata da tutti i tipi di classi effetto
 * il metodo attiva effetto prende in ingresso il codice dell'effetto e si occupa di applicare il metodo corrispondente
 *
 */

import partita.componentiDelTabellone.Familiare;
import partita.componentiDelTabellone.Giocatore;
import partita.componentiDelTabellone.Tabellone;
import partita.eccezioniPartita.RisorseInsufficientiException;

/**
 * Created by Pietro on 18/05/2017.
 */
public interface Effetto {
    public void attivaEffetto(String s, Giocatore giocatore, Tabellone tab) throws RisorseInsufficientiException;

}


