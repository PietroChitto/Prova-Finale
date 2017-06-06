package partita.carteDaGioco.effettiScomunica;

import partita.carteDaGioco.effetti.Effetto;
import partita.componentiDelTabellone.Familiare;
import partita.eccezioniPartita.RisorseInsufficientiException;

/**
 * Created by Pietro on 06/06/2017.
 *
 * diminuisce la forza di un famigliare quando fa un azione in una certa Zona
 */
public class EffettoDiminuisciForza implements Effetto{

    @Override
    public void attivaEffetto(String s, Familiare f, int codiceZona) throws RisorseInsufficientiException {
        int forza=(int) s.charAt(0)-48;
        int [] zone=new int[6];

        for (int i=0; i<6; i++){
            zone[i]=(int) s.charAt((i+1)*2) -48;
        }

        if(zone[0]==1 && codiceZona==0){
            f.setForza(f.getForza()-forza);
        }
        if(zone[1]==1 && codiceZona==1){
            f.setForza(f.getForza()-forza);
        }
        if(zone[2]==1 && codiceZona==2){
            f.setForza(f.getForza()-forza);
        }
        if(zone[3]==1 && codiceZona==3){
            f.setForza(f.getForza()-forza);
        }
        if(zone[4]==1 && codiceZona==4){
            f.setForza(f.getForza()-forza);
        }
        if(zone[5]==1 && codiceZona==5){
            f.setForza(f.getForza()-forza);
        }
    }
}
