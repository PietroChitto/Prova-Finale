package partita.carteDaGioco;

import org.junit.Test;
import partita.componentiDelTabellone.Giocatore;
import partita.eccezioniPartita.RisorseInsufficientiException;

import static org.junit.Assert.*;

/**
 * Created by Pietro on 31/05/2017.
 */
public class CartaEdificioTest {
    @Test
    public void confrontaCosto() throws Exception {
        Giocatore g =new Giocatore(3);
        g.setPietra(10);
        g.setMonete(20);
        g.setServitori(5);
        g.setLegna(10);
        g.setPuntiMilitari(20);

        CostoCarta costo=new CostoCarta(1,1,1,1,1);
        CartaSviluppo c=new CartaEdificio(costo,1,1,"",true,true,"","",null,null);
        if(!(costo.getCostoLegna()<=g.getLegna() && costo.getCostoMonete()<=g.getMonete()
                && costo.getCostoPietra()<=g.getPietra() && costo.getCostoServitori()<=g.getServitori()
                && costo.getCostoPuntiMilitari()<=g.getPuntiMilitari())){
            throw new RisorseInsufficientiException();

        }
    }

}