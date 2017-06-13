package partita.carteDaGioco.effettiScomunica;
/**
 * sono gli effetti che, quando il giocatore acquisice risorse, diminuiscono il valore dell'incremento
 */

import partita.carteDaGioco.effetti.Effetto;
import partita.componentiDelTabellone.Familiare;
import partita.eccezioniPartita.RisorseInsufficientiException;

/**
 * Created by Pietro on 06/06/2017.
 */
public class EffettoDiminuisciIncremento implements Effetto {
    /**
     *
     * @param s deve essere il codice dell'effetto incrementa e il codice della carta scomunica: 0P0L0S0M0E0F0V#0P0L0S0M0E0F0V
     * @param f
     * @param codiceZona
     * @throws RisorseInsufficientiException
     */
    @Override
    public void attivaEffetto(String s, Familiare f, int codiceZona) throws RisorseInsufficientiException {
        if(s.length()==29) {
            System.out.println("attivato l'effetto Diminuisci incremento al giocatore "+f.getGiocatore().getId()+" a causa della scomunica");
            int[] risorseIniziali = new int[7];
            int[] risorseTolte = new int[7];

        /*
        nel ciclo prendo le risorse che devo scambiare e le salvo nell'array
        non ci ricorderemo il perchè
         */
            for (int i = 0; i < 14; i += 2) {
                risorseIniziali[i / 2] = (int) s.charAt(i) - 48;
                risorseTolte[i / 2] = (int) s.charAt(i + 15) - 48;
            }

            /**
             * ciclo i due array, se entarambi sono maggiori di zero nella stassa posizione, tolgo le risorse (che saranno sempre di un unità)
             */
            for (int i = 0; i < 7; i++) {
                if (risorseIniziali[i] > 0 && risorseTolte[i] > 0) {
                    f.getGiocatore().decrementaRisorse(risorseTolte);
                }
            }
        }
        else {
            System.out.println("effetto scomunica non attivato");
            System.out.println( s+" lunghezza "+s.length());
        }
    }
}
