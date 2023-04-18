package partita.componentiDelTabellone;

/**
 * Created by william on 10/05/2017.
 */
public class Familiare {

    private boolean neutro;
    private int idGiocatore;
    private Giocatore giocatore;
    private String coloreDado; // quale dado devo controllare (lancio)
    private int forza; //risultato del dado corrispondente al familiare
    private boolean disponibile; //se posso usufruirne o meno


    public Familiare (boolean neutro, int idGiocatore, int forza, String coloreDado, boolean disponibile, Giocatore giocatore){

        this.neutro=neutro;
        this.coloreDado=coloreDado;
        this.idGiocatore=idGiocatore;
        this.disponibile=disponibile;
        this.forza=forza;
        this.giocatore=giocatore;
    }

    public int getForza(){
        return forza;
    }

    public boolean isDisponibile() {
        return disponibile;
    }

    public boolean isNeutro() {
        return neutro;
    }

    public Giocatore getGiocatore() {
        return giocatore;
    }

    public int getIdGiocatore() {
        return idGiocatore;
    }

    public String getColoreDado() {
        return coloreDado;
    }

    public void setDisponibile(boolean disponibile) {
        this.disponibile = disponibile;
    }

    public void setForza(int forza) {
        this.forza = forza;
    }


}
