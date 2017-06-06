package partita.carteDaGioco.effetti.effettiCarte;
/**
 * effetto che permette la conversione di risorese in altre risorse
 */

import partita.carteDaGioco.effetti.Effetto;
import partita.componentiDelTabellone.Familiare;
import partita.componentiDelTabellone.Giocatore;
import partita.componentiDelTabellone.Tabellone;
import partita.eccezioniPartita.RisorseInsufficientiException;

/**
 * Created by Pietro on 18/05/2017.
 */
public class EffettoScambia implements Effetto {
    @Override
    public void attivaEffetto(String s, Familiare f, int codiceZona){
        int[] risorseIniziali=new int[7];
        int[] risorseFinali=new int[7];

        /*
        nel ciclo prendo le risorse che devo scambiare e le salvo nell'array
        non ci ricorderemo il perchè
         */
        for(int i=0; i<14; i+=2){
            risorseIniziali[i/2]=(int) s.charAt(i)-48;
            risorseFinali[i/2]=(int) s.charAt(i+15)-48;
        }
        try {
            //controllo se ho le risorse necessarie
            controlloRisorseSufficienti(risorseIniziali,f.getGiocatore());
            f.getGiocatore().incrementaRisorse(risorseFinali);
            f.getGiocatore().decrementaRisorse(risorseIniziali);

        } catch (RisorseInsufficientiException e) {
            System.out.println("");
        }

    }


    private void controlloRisorseSufficienti(int[] risorse, Giocatore g) throws RisorseInsufficientiException{
        if(!(g.getPietra()>=risorse[0] && g.getLegna()>=risorse[1] && g.getServitori()>=risorse[2]
            && g.getMonete()>=risorse[3] && g.getPuntiMilitari()>=risorse[4] && g.getPuntiFede()>=risorse[5]
            && g.getPuntiVittoria()>=risorse[6]))
                throw new RisorseInsufficientiException();
    }


}
