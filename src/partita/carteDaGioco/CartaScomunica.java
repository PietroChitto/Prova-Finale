package partita.carteDaGioco;

import partita.componentiDelTabellone.Giocatore;

/**
 * Created by Pietro on 11/05/2017.
 */
public class CartaScomunica {

    private int id;
    private String effetto;
    private int periodo;


    public CartaScomunica (int id, String effetto, int periodo){

        this.periodo=periodo;
        this.id=id;
        this.effetto=effetto;

    }

    public int getPeriodo(){
        return periodo;
    }

    public void attivaEffettoScomunica(Giocatore giocatore){



    }

}
