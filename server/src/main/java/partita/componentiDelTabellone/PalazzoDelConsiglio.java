package partita.componentiDelTabellone;

import partita.eccezioniPartita.ForzaInsufficienteException;

import java.util.ArrayList;

/**
 * Created by Pietro on 10/05/2017.
 */
public class PalazzoDelConsiglio {
    private final int costo=1;
    private ArrayList<Giocatore> ordineArrivoGiocatori;
    private boolean giocatorePresente;


    public PalazzoDelConsiglio(int numeroGiocatori){
        ordineArrivoGiocatori= new ArrayList<Giocatore>();
    }

    public void arrivaGiocatore(Familiare familiare) throws ForzaInsufficienteException {
        if(familiare.getForza()<costo)
            throw new ForzaInsufficienteException();
        //controllo se il giocatore è la prima volta che arriva, se è la seconda non lo aggiungo alla lista
        for(Giocatore g: ordineArrivoGiocatori){
            if(g == familiare.getGiocatore()){
                giocatorePresente=true;
            }
        }
        if(!giocatorePresente)
            ordineArrivoGiocatori.add(familiare.getGiocatore());
        familiare.getGiocatore().setMonete(familiare.getGiocatore().getMonete()+1);
        giocatorePresente=false;
    }
    public ArrayList<Giocatore> calcoaTurnoSuccessivo(ArrayList<Giocatore> ordineTurnoCorrente){
        ArrayList<Giocatore> nuovoTurno=new ArrayList<>();

        nuovoTurno.addAll(ordineArrivoGiocatori);
        ordineTurnoCorrente.removeAll(ordineArrivoGiocatori);
        nuovoTurno.addAll(ordineTurnoCorrente);

        //setTurnoCorrente();
        svuotaOrdineArrivo();


        return nuovoTurno;
    }

    public void stampaOrdineTurno(ArrayList<Giocatore> ordineTurno) {
        System.out.println("il nuovo turno è: ");
        for (Giocatore g: ordineTurno){
            System.out.println("giocatore "+ g.getId());
        }
    }

    /*
    i turni partono da 1
     */
    /*private void setTurnoCorrente(){
        for (int i=0; i<ordineTurnoCorrente.size(); i++){
            ordineTurnoCorrente.get(i).setTurno(i+1);
        }
    }*/

    private  void svuotaOrdineArrivo(){
        ordineArrivoGiocatori.clear();
    }
}
