package partita.carteDaGioco.effettiScomunica;

import partita.carteDaGioco.effetti.Effetto;
import partita.componentiDelTabellone.Familiare;
import partita.componentiDelTabellone.Giocatore;
import partita.eccezioniPartita.RisorseInsufficientiException;

/**
 * Created by Pietro on 06/06/2017.
 */
public class EffettoDiminuisciPuntiVittoria implements Effetto {

    /**
     *
     * @param s  es: 1T1P1I1E5V
     * @param f
     * @param codiceZona
     * @throws RisorseInsufficientiException
     */
    @Override
    public void attivaEffetto(String s, Familiare f, int codiceZona) throws RisorseInsufficientiException {
        if(s.length()==10) {
            System.out.println("attivata scomunica diminuisci pt vittoria al giocatore "+f.getGiocatore().getId());
            int[] penalità = new int[5];
            Giocatore g = f.getGiocatore();

            for (int i = 0; i < 5; i++) {
                penalità[i] = s.charAt(i * 2) - 48;
            }

            if (penalità[0] > 0) {
                g.setPuntiVittoria(g.getPuntiVittoria() - g.getCarteTerritorio().size());
            }
            if (penalità[1] > 0) {
                g.setPuntiVittoria(g.getPuntiVittoria() - g.getCartePersonaggio().size());
            }
            if (penalità[2] > 0) {
                g.setPuntiVittoria(g.getPuntiVittoria() - g.getCarteImpresa().size());
            }
            if (penalità[3] > 0) {
                g.setPuntiVittoria(g.getPuntiVittoria() - g.getPuntiMilitari());
            }
            if (penalità[4] > 0) {
                g.setPuntiVittoria(g.getPuntiVittoria() - g.getPuntiVittoria() % 5);
            }
        }
    }
}
