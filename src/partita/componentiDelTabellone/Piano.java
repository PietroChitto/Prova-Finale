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
    private int numeroTorre;

    public Piano(int costoMinimo, boolean effetto, int numero, int numeroTorre){
        this.numeroTorre=numeroTorre;
        this.numeroPiano=numero;
        creaEffettoPiano();
        campoAzione=new CampoAzioneSingolo(costoMinimo,effetto,effettoPiano);
        cartaAssente=true;
    }

    private void creaEffettoPiano() {
        switch (numeroPiano){
            case 0: effettoPiano="0P0L0S0M0E0F0V";
                break;
            case 1: effettoPiano="0P0L0S0M0E0F0V";
                break;
            case 2: guardaTorre(1);
                break;
            case 3: guardaTorre(2);
                break;
        }
    }

    /**
     * guarda in che torre è il piano e crea il codice effetto giusto
     * @param incremento
     */
    private void guardaTorre(int incremento) {
        switch (numeroTorre){
            case 0:effettoPiano= "0P"+incremento+"L0S0M0E0F0V";
                break;
            case 1:effettoPiano=incremento+"P0L0S0M0E0F0V";
                break;
            case 2:effettoPiano="0P0L0S0M"+incremento+"E0F0V";
                break;
            case 3:effettoPiano="0P0L0S"+incremento+"M0E0F0V";
                break;
        }
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

    public void togliFamigliare(){
        this.campoAzione.svuotaCampoAzione();
    }

    public CampoAzioneSingolo getCampoAzione() {
        return campoAzione;
    }

    public boolean isOccupato(){
        return campoAzione.isOccupato();
    }
}
