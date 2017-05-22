package partita.carteDaGioco.effetti;
/**
 * questo effeto aumenta delle risorse precise per ogni tipo di carta precisa posseduta dal gicatore
 */

import partita.componentiDelTabellone.Familiare;
import partita.componentiDelTabellone.Giocatore;
import partita.componentiDelTabellone.Tabellone;
import partita.eccezioniPartita.RisorseInsufficientiException;

/**
 * Created by Pietro on 18/05/2017.
 */
public class EffettoAumenta implements Effetto{


    @Override
    public void attivaEffetto(String s, Giocatore giocatore, Tabellone tab) throws RisorseInsufficientiException {
        int[] incr =new int[2];

        for(int i=0; i<2; i++){
            incr[i]=(int)s.charAt(i*2);
        }

        char tipo = s.charAt(5);

        switch (tipo){

            case 'T': giocatore.setMonete(giocatore.getMonete()+(incr[0]* giocatore.getCarteTerritorio().size()));
                        giocatore.setPuntiVittoria(giocatore.getPuntiVittoria()+(incr[1]* giocatore.getCarteTerritorio().size()));
                        break;
            case 'E': giocatore.setMonete(giocatore.getMonete()+(incr[0]* giocatore.getCarteEdificio().size()));
                        giocatore.setPuntiVittoria(giocatore.getPuntiVittoria()+(incr[1]* giocatore.getCarteEdificio().size()));
                        break;
            case 'P': giocatore.setMonete(giocatore.getMonete()+(incr[0]* giocatore.getCartePersonaggio().size()));
                        giocatore.setPuntiVittoria(giocatore.getPuntiVittoria()+(incr[1]* giocatore.getCartePersonaggio().size()));
                        break;
            case 'I': giocatore.setMonete(giocatore.getMonete()+(incr[0]* giocatore.getCarteImpresa().size()));
                        giocatore.setPuntiVittoria(giocatore.getPuntiVittoria()+(incr[1]* giocatore.getCarteImpresa().size()));
                        break;
        }


    }
}
