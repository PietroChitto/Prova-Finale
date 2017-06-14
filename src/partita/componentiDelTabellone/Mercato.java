package partita.componentiDelTabellone;

import partita.eccezioniPartita.ForzaInsufficienteException;
import partita.eccezioniPartita.ZonaOccupataExcepion;

/**
 * Created by Pietro on 10/05/2017.
 */
public class Mercato {
    //serve per capire se vanno coperte alcune zone del mercato
    private boolean menoDiQuattroGiocatori;
    private CampoAzioneSingolo[] campiAzione;
    private String[] effetti;



    public Mercato(boolean menoDiQuattroGiocatori){
        this.menoDiQuattroGiocatori=menoDiQuattroGiocatori;
        effetti=new String[4];
        effetti[0]="0P0L0S5M0E0F0V";
        effetti[1]="0P0L5S0M0E0F0V";
        effetti[2]="0P0L0S2M3E0F0V";
        effetti[3]="0P0L0S0M0E0F0V";
        creaZoneMercato();
    }

    private void creaZoneMercato(){
        if(menoDiQuattroGiocatori){
            campiAzione=new CampoAzioneSingolo[2];
        }
        else{
            campiAzione=new CampoAzioneSingolo[4];
        }
        for(int i=0; i<campiAzione.length; i++) {
            campiAzione[i]=new CampoAzioneSingolo(1,true,effetti[i]);
        }

    }

    public boolean controlloCampoAzioneOccupato(int campoAzione){
        return !campiAzione[campoAzione].isOccupato();
    }
    public void arrivaGiocatore(Familiare f, int campoAzione ) throws ZonaOccupataExcepion, ForzaInsufficienteException {
            campiAzione[campoAzione].setFamiliare(f);
            campiAzione[campoAzione].setOccupato(true);
    }

    public void svuotaMercato(){

        for (int i=0;i<campiAzione.length;i++){
            campiAzione[i].svuotaCampoAzione();
            campiAzione[i].setOccupato(false);
        }

    }

}
