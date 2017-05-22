package partita.componentiDelTabellone;

import partita.carteDaGioco.CartaSviluppo;

/**
 * Created by Pietro on 10/05/2017.
 */
public class Piano {
    //ogni torre incrementa un tipo di risorse
    private CampoAzioneSingolo campoAzione;
    private String effettoPiano;
    private CartaSviluppo carta;
    private boolean cartaAssente;
    private int numeroPiano;

    public Piano(int costoMinimo, boolean effetto,String effettoPiano, int numeroPiano){
        campoAzione=new CampoAzioneSingolo(costoMinimo,effetto,effettoPiano);
        this.numeroPiano=numeroPiano;
        cartaAssente=true;
    }

    public void setCartaSviluppo(CartaSviluppo carta){
        this.carta=carta;
        cartaAssente=false;
    }

    public CartaSviluppo getCartaSviluppo(){
        return carta;
    }

    public int getNumeroPiano() {
        return numeroPiano;
    }

    public boolean isCartaAssente() {
        return cartaAssente;
    }

    public void togliCarta(){

        setCartaSviluppo(null);
        cartaAssente=true;
    }

    public CampoAzioneSingolo getCampoAzione() {
        return campoAzione;
    }

    public boolean isOccupato(){
        return campoAzione.isOccupato();
    }
}
