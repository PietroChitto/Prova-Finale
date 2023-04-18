package partita.carteDaGioco;

import partita.carteDaGioco.effetti.Effetto;
import partita.carteDaGioco.effettiScomunica.EffettoDiminuisciIncremento;
import partita.componentiDelTabellone.Familiare;
import partita.componentiDelTabellone.Giocatore;
import partita.eccezioniPartita.RisorseInsufficientiException;

/**
 * Created by Pietro on 11/05/2017.
 */
public class CartaScomunica {

    private Effetto effetto;
    private int periodo;
    private String codiceEffetto;
    private String nome;


    public CartaScomunica ( Effetto effetto, int periodo, String codiceEffetto, String nome){

        this.periodo=periodo;
        this.effetto=effetto;
        this.nome=nome;
        this.codiceEffetto=codiceEffetto;


    }

    public int getPeriodo(){
        return periodo;
    }

    /**
     *
     * @param f familiare che sta usando il giocatore
     * @param s codice dell'effettoIncrementa della carta che ha attivato il proprio effetto, serve solo per l'effettoDiminuisciIncremento
     *          , negli altri casi non viene considerato
     * @param zona serve per l'effetto DiminuisciForza, che viene attivato solo se il familiare si trova in determinate zone;
     */
    public void attivaEffettoScomunica(Familiare f, String s, int zona){
        if(effetto instanceof EffettoDiminuisciIncremento){
            try {
                System.out.println("L'effetto scomunica è del tipo diminuisci incremento");
                effetto.attivaEffetto(s+"#"+codiceEffetto, f,0);
            } catch (RisorseInsufficientiException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                effetto.attivaEffetto(codiceEffetto,f,zona);
            } catch (RisorseInsufficientiException e) {
                e.printStackTrace();
            }
        }

    }

    public String getNome(){
        return nome;
    }

}
