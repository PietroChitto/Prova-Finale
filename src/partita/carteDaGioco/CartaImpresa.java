package partita.carteDaGioco;

import partita.carteDaGioco.effetti.Effetto;
import partita.componentiDelTabellone.Familiare;
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
     * @param f: giocatore possessore della carta
     */
    public void attivaEffettoRapido(Familiare f){
        if(super.getEffettoRapido()){
            try {
                effettoRapido.attivaEffetto(codiceEffR, f, 0);
            } catch (RisorseInsufficientiException e) {
                e.printStackTrace();
            }
        }
    }

    public void attivaEffettoPermanente(Familiare f){
        if(super.getEffettoPermanente()){
            try {
                effettoPermanente.attivaEffetto(codiceEffP, f,0);
            } catch (RisorseInsufficientiException e) {
                e.printStackTrace();
            }
        }
    }

    public void confrontaCosto(Giocatore g) throws RisorseInsufficientiException {
        stampaSitua(g);
        if(!(costoCarta1.getCostoLegna()<=g.getLegna() && costoCarta1.getCostoMonete()<=g.getMonete()
                && costoCarta1.getCostoServitori()<=g.getServitori()
                && costoCarta1.getCostoPuntiMilitari()<=g.getPuntiMilitari()) )
            throw new RisorseInsufficientiException();

    }

    private void stampaSitua(Giocatore g) {
        System.out.println("p g: "+g.getPietra());
        System.out.println("p c: "+costoCarta1.getCostoPietra());
        System.out.println("l g: "+g.getLegna());
        System.out.println("l c: "+costoCarta1.getCostoLegna());
        System.out.println("s g: "+g.getServitori());
        System.out.println("s c: "+costoCarta1.getCostoServitori());
        System.out.println("m g: "+g.getMonete());
        System.out.println("m c: "+costoCarta1.getCostoMonete());
        System.out.println("mil g: "+g.getPuntiMilitari());
        System.out.println("mil c: "+costoCarta1.getCostoPuntiMilitari());
    }

}
