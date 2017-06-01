package partita.carteDaGioco.effetti;

import partita.carteDaGioco.CartaEdificio;
import partita.carteDaGioco.CartaSviluppo;
import partita.carteDaGioco.CartaTerritorio;
import partita.componentiDelTabellone.*;
import partita.eccezioniPartita.RisorseInsufficientiException;
/**
 * aumenta la forza di un familiare che fa un azione su una certa torre o zona indicata dalla carta
 * Questo effetto opera al contrario, diminuisce il costo di ogni campo azione della zona indicata dalla carta
 * per questo turno
 */

/**
 * Created by Pietro on 19/05/2017.
 */
public class EffettoAumentaForza implements Effetto {

    @Override
    public void attivaEffetto(String s, Giocatore giocatore, Tabellone tab) throws RisorseInsufficientiException {

            int forza=(int) s.charAt(0)-48;
            int [] zone=new int[6];

            for (int i=0; i<6; i++){
                zone[i]=(int) s.charAt((i+1)*2) -48;
            }

            if(zone[0]==1){
                for (Piano p:tab.getTorre(1).getPiani()){
                    p.getCampoAzione().setCosto(p.getCampoAzione().getCosto()-forza);
                }
            }
            if(zone[1]==1){
                for (Piano p:tab.getTorre(2).getPiani()){
                    p.getCampoAzione().setCosto(p.getCampoAzione().getCosto()-forza);
                }
            }
            if(zone[2]==1){
                for (Piano p:tab.getTorre(3).getPiani()){
                    p.getCampoAzione().setCosto(p.getCampoAzione().getCosto()-forza);
                }
            }
            if(zone[3]==1){
                for (Piano p:tab.getTorre(4).getPiani()){
                    p.getCampoAzione().setCosto(p.getCampoAzione().getCosto()-forza);
                }

            }
            if(zone[4]==1){
                for (CartaEdificio cs: giocatore.getCarteEdificio()){
                    cs.setCostoAttivazioneEffettoPermanente(cs.getCostoAttivazioneEffettoPermanente()-forza);
                }
            }if(zone[5]==1){
                for (CartaTerritorio cs: giocatore.getCarteTerritorio()){
                    cs.setCostoAttivazioneEffettoPermanente(cs.getCostoAttivazioneEffettoPermanente()-forza);
                }
            }


    }
}
