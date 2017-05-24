package partita.componentiDelTabellone;

import partita.carteDaGioco.CartaScomunica;

/**
 * Created by Pietro on 10/05/2017.
 */
public class Vaticano {
    private CartaScomunica[] carteScomunica;

    public Vaticano(){

        carteScomunica=new CartaScomunica[3];
    }

    public void setCarteScomunica(CartaScomunica periodo1, CartaScomunica periodo2, CartaScomunica periodo3){
        carteScomunica[0]=periodo1;
        carteScomunica[1]=periodo2;
        carteScomunica[2]=periodo3;
    }

    public CartaScomunica[] getCarteScomunica() {
        return carteScomunica;
    }

    public synchronized boolean controlloPuntiFede(Giocatore g, int periodo){
        /*
            controllo se i punti fede del giocatore sono minori del periodo incrementati di due, punti necessari per poter sceglere di non
            essere scomunicati

            1 periodo --> 3 punti
            2 periodo --> 4 punti
            3 periodo --> 5 punti
         */
        if(g.getPuntiFede()<periodo+2 ){
            scomunica(g,periodo);
            return false;
        }
        return true;
    }

    public synchronized void scomunica(Giocatore g,int periodo){

        if(periodo==1)
            g.aggiungiScomunica(carteScomunica[0]);
        if(periodo==2)
            g.aggiungiScomunica(carteScomunica[1]);
        if(periodo==3)
            g.aggiungiScomunica(carteScomunica[2]);

    }


}
