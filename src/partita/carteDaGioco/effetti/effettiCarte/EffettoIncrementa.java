package partita.carteDaGioco.effetti.effettiCarte;
/**
 * effetti che si occupano di incrementare le risorse del giocatore
 */

import partita.carteDaGioco.effetti.Effetto;
import partita.componentiDelTabellone.Familiare;
import partita.componentiDelTabellone.Giocatore;
import partita.componentiDelTabellone.Tabellone;

/**
 * Created by Pietro on 18/05/2017.
 */
public class EffettoIncrementa implements Effetto {

    /**
     *
     * @param s  del tipo  0P0L0S0M0E0F0V
     *           P->pietra
     *           L->legno
     *           S->servitori
     *           M->Monete
     *           E->punti militari
     *           F->fede
     *           V->punti vittoria
     * @param f  familiare che attiva l'effetto e al quale verranno incrementate le risorse
     */
    @Override
    public void attivaEffetto(String s, Familiare f, int codiceZona) {
        int[] incrementi=new int[7];
        for (int i=0; i<14; i+=2){
            incrementi[i/2]=(int) s.charAt(i)-48;
        }

        f.getGiocatore().incrementaRisorse(incrementi);
        System.out.println("incrementate le risorse del giocatore "+f.getGiocatore().getId()+" a causa dell'effetto");
    }


}
