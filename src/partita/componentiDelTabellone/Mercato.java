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
        effetti[0]="incrementa5monete";
        effetti[1]="incrementa5servitori";
        effetti[2]="incrementa3militari2monete";
        effetti[3]="incrementa2pergamene";
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
        if(campiAzione[campoAzione].isOccupato()) return false;
        return true;
    }
    public void arrivaGiocatore(Familiare f, int campoAzione ) throws ZonaOccupataExcepion, ForzaInsufficienteException {
            campiAzione[campoAzione-1].setFamiliare(f);
            campiAzione[campoAzione-1].setOccupato(true);
            attivaEffetto(f.getGiocatore(),campoAzione);
    }

    private void attivaEffetto(Giocatore g, int campoAzione){
        switch (campoAzione){
            case 1: g.setMonete(g.getMonete()+5);
                    break;
            case 2: g.setServitori(g.getServitori()+5);
                    break;
            case 3: g.setPuntiMilitari(g.getPuntiMilitari()+3);
                    g.setMonete(g.getMonete()+2);
                    break;
            case 4: //manda messaggio scelta pergamena;
        }
    }

    public void svuotaMercato() throws ZonaOccupataExcepion, ForzaInsufficienteException{

        for (int i=0;i<campiAzione.length;i++){
            campiAzione[i].svuotaCampoAzione();
            campiAzione[i].setOccupato(false);
        }

    }

}
