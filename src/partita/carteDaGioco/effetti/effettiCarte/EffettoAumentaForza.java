package partita.carteDaGioco.effetti.effettiCarte;

import partita.carteDaGioco.CartaEdificio;
import partita.carteDaGioco.CartaSviluppo;
import partita.carteDaGioco.CartaTerritorio;
import partita.carteDaGioco.effetti.Effetto;
import partita.componentiDelTabellone.*;
import partita.eccezioniPartita.RisorseInsufficientiException;
/**
 * aumenta la forza di un familiare che fa un azione su una certa torre o zona indicata dalla carta
 * Questo effetto opera al contrario, diminuisce il costo di ogni campo azione della zona indicata dalla carta
 * per questo turno
 */

/**
 * numero zona tabellone azione
 * 0: torre 0
 * 1: torre 1
 * 2: torre 2
 * 3: torre 3
 * 4: zona produzione
 * 5: zona raccolto
 */

/**
 * Created by Pietro on 19/05/2017.
 */
public class EffettoAumentaForza implements Effetto {

    @Override
    public void attivaEffetto(String s, Familiare f, int codiceZona) {
        int forza=(int) s.charAt(0)-48;
        int [] zone=new int[6];

        for (int i=0; i<6; i++){
            zone[i]=(int) s.charAt((i+1)*2) -48;
        }

        if(zone[0]==1 && codiceZona==0){
            f.setForza(f.getForza()+forza);
        }
        if(zone[1]==1 && codiceZona==1){
            f.setForza(f.getForza()+forza);
        }
        if(zone[2]==1 && codiceZona==2){
            f.setForza(f.getForza()+forza);
        }
        if(zone[3]==1 && codiceZona==3){
            f.setForza(f.getForza()+forza);
        }
        if(zone[4]==1 && codiceZona==4){
            f.setForza(f.getForza()+forza);
        }
        if(zone[5]==1 && codiceZona==5){
            f.setForza(f.getForza()+forza);
        }

    }
}
