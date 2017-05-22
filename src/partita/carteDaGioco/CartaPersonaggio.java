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
     * @param g: giocatore possessore della carta
     */
    public void attivaEffettoRapido(Giocatore g){
        if(super.getEffettoRapido()){
            //cerca meteodo rapido
        }
    }

    /**
     * controlla se la carta ha un effetto permanente e si occupa dell'attivazione del metodo che implementa l'effetto
     * @param f: familiare del giocatore possessore della carta
     */
    public void attivaEffettoPermanente(Familiare f){
        if(super.getEffettoPermanente()){
            //cerca metodo permanente
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

}