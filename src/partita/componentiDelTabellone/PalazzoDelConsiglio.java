package partita.componentiDelTabellone;

import partita.carteDaGioco.effetti.Effetto;
import partita.carteDaGioco.effetti.EffettoIncrementa;
import partita.eccezioniPartita.ForzaInsufficienteException;

import java.util.ArrayList;

/**
 * Created by Pietro on 10/05/2017.
 */
public class PalazzoDelConsiglio {
    private final int costo=1;
    private ArrayList<Giocatore> ordineArrivoGiocatori;


    public PalazzoDelConsiglio(int numeroGiocatori){
        ordineArrivoGiocatori= new ArrayList<Giocatore>();
    }

    public void arrivaGiocatore(Familiare familiare) throws ForzaInsufficienteException {
        if(familiare.getForza()<costo)
            throw new ForzaInsufficienteException();
        ordineArrivoGiocatori.add(familiare.getGiocatore());
        familiare.getGiocatore().setMonete(familiare.getGiocatore().getMonete()+1);
        /*
        la scelta dello scambio della pergamena è gestita nel ciclo while del server
         */
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
