package partita.componentiDelTabellone;

/**
 * Created by Pietro on 10/05/2017.
 */
public class Zona {

    //indica se è la zona di produzione o di raccolto
    private boolean produzione;

    //indica se ci sono almeno 3 giocatori per coprire certe zone
    private boolean treGiocatori;

    private CampoAzioneSingolo campoAzioneSinogolo;

    private CampoAzioneMultiplo campoAzioneMultiplo;

    public Zona(boolean produzione, boolean treGiocatori){
        this.produzione=produzione;
        this.treGiocatori=treGiocatori;
        creaCampiAzione();
    }

    private void creaCampiAzione(){
        if(treGiocatori){
            campoAzioneMultiplo=new CampoAzioneMultiplo(1);
        }
        if(produzione){
            campoAzioneSinogolo=new CampoAzioneSingolo(1,true,"attivaProduzione");
        }
        else{
            campoAzioneSinogolo=new CampoAzioneSingolo(1,true,"attivaRaccolto");
        }
    }

    public void svuotaZona(){
        campoAzioneSinogolo.svuotaCampoAzione();
        campoAzioneMultiplo.svuotaCampoAzione();
    }

    public CampoAzioneMultiplo getCampoAzioneMultiplo() {
        return campoAzioneMultiplo;
    }

    public CampoAzioneSingolo getCampoAzioneSinogolo() {
        return campoAzioneSinogolo;
    }
}
