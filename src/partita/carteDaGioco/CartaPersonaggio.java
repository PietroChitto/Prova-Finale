package partita.carteDaGioco;

import partita.carteDaGioco.effetti.Effetto;
import partita.componentiDelTabellone.Familiare;
import partita.componentiDelTabellone.Giocatore;
import partita.eccezioniPartita.RisorseInsufficientiException;

/**
 * Created by william on 10/05/2017.
 */
public class CartaPersonaggio extends CartaSviluppo {

    private String codiceEffR;
    private String codiceEffP;
    private Effetto effettoPermanente;
    private Effetto effettoRapido;
    private CostoCarta costo;

    public CartaPersonaggio(CostoCarta costo, int periodo, String nome, boolean effettoRapido, boolean effettoPermanente, String codiceEffR, String codiceEffP, Effetto effettoR, Effetto effettoP){

        super(periodo,nome,effettoRapido,effettoPermanente,"personaggio");
        this.codiceEffR = codiceEffR;
        this.codiceEffP = codiceEffP;
        this.effettoPermanente=effettoP;
        this.effettoRapido=effettoR;
        this.costo=costo;

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

    /**
     * controlla se la carta ha un effetto permanente e si occupa dell'attivazione del metodo che implementa l'effetto
     * @param f: familiare del giocatore possessore della carta
     */
    public void attivaEffettoPermanente(Familiare f, int codiceZona){
        if(super.getEffettoPermanente()){
            try {
                effettoPermanente.attivaEffetto(codiceEffP,f,codiceZona);
            } catch (RisorseInsufficientiException e) {
                e.printStackTrace();
            }
        }
    }

    public CostoCarta getCosto(){
        return costo;
    }

    public void confrontaCosto(Giocatore g) throws RisorseInsufficientiException {
        if(!(costo.getCostoLegna()<=g.getLegna() && costo.getCostoMonete()<=g.getMonete()
                && costo.getCostoPietra()<=g.getPietra() && costo.getCostoServitori()<=g.getServitori()
                && costo.getCostoPuntiMilitari()<=g.getPuntiMilitari()) )
            throw new RisorseInsufficientiException();

    }

    private void stampaSitua(Giocatore g) {
        System.out.println("p g: "+g.getPietra());
        System.out.println("p c: "+costo.getCostoPietra());
        System.out.println("l g: "+g.getLegna());
        System.out.println("l c: "+costo.getCostoLegna());
        System.out.println("s g: "+g.getServitori());
        System.out.println("s c: "+costo.getCostoServitori());
        System.out.println("m g: "+g.getMonete());
        System.out.println("m c: "+costo.getCostoMonete());
        System.out.println("mil g: "+g.getPuntiMilitari());
        System.out.println("mil c: "+costo.getCostoPuntiMilitari());
    }


    public String getCodiceEffettoRapido() {
        return codiceEffR;
    }

    public Effetto getEffettoP() {
        return effettoPermanente;
    }
}