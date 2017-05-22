package partita.carteDaGioco;

import partita.carteDaGioco.effetti.Effetto;
import partita.componentiDelTabellone.Giocatore;
import partita.eccezioniPartita.RisorseInsufficientiException;

/**
 * Created by william on 10/05/2017.
 */
public class CartaImpresa extends CartaSviluppo {

    private String codiceEffR;
    private String codiceEffP;
    private Effetto effettoRapido;
    private Effetto effettoPermanente;
    private CostoCarta costoCarta1;//le carte impresa possono avere due costi tra cui scegliere
    private CostoCarta costoCarta2;
    private boolean doppioCosto;

    public CartaImpresa(CostoCarta costo1, CostoCarta costo2, boolean doppioCosto, int periodo, String nome, boolean effettoRapido, String codiceEffR, String codiceEffP, Effetto effettoR, Effetto effettoP){

        super(periodo, nome, effettoRapido, false, "Impresa");
        this.codiceEffR = codiceEffR;
        this.codiceEffP = codiceEffP;
        this.effettoRapido=effettoR;
        this.effettoPermanente=effettoP;
        this.costoCarta1=costo1;
        this.costoCarta2=costo2;
        this.doppioCosto=doppioCosto;

    }

    public CostoCarta getCosto1(){
        return costoCarta1;
    }

    public CostoCarta getCosto2(){
        return costoCarta2;
    }


    /**
     * controlla se la carta ha un effetto rapido e si occupa di chiamare il metodo che implementa l'effetto
     * @param g: giocatore possessore della carta
     */
    public void attivaEffettoRapido(Giocatore g){
        if(super.getEffettoRapido()){
            //cerca meteodo rapido
        }
    }

    public void attivaEffettoPermanente(Giocatore g){
        if(super.getEffettoPermanente()){
            //cerca meteodo rapido
        }
    }

    public void confrontaCosto(Giocatore g) throws RisorseInsufficientiException {
        if(!(costoCarta1.getCostoLegna()<=g.getLegna() && costoCarta1.getCostoMonete()<=g.getMonete()
                && costoCarta1.getCostoServitori()<=g.getServitori()
                && costoCarta1.getCostoPuntiMilitari()<=g.getPuntiMilitari()) )
            throw new RisorseInsufficientiException();

    }

}
