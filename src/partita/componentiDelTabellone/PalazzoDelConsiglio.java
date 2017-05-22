package partita.componentiDelTabellone;

import java.util.ArrayList;

/**
 * Created by Pietro on 10/05/2017.
 */
public class PalazzoDelConsiglio {
    private final int costo=1;
    private ArrayList<Giocatore> ordineArrivoGiocatori;
    private String effetto;

    public PalazzoDelConsiglio(int numeroGiocatori){
        ordineArrivoGiocatori= new ArrayList<Giocatore>();
        effetto="incrementa1moneta&&1pergamena";
    }

    public void arrivaGiocatore(Giocatore g){
        ordineArrivoGiocatori.add(g);
        g.setMonete(g.getMonete()+1);
        /*
        la scelta dello scambio della pergamena è gestita nel ciclo while del server
         */
    }
    public ArrayList<Giocatore> calcoaTurnoSuccessivo(ArrayList<Giocatore> ordineTurnoCorrente){
        for (int i=0;i<ordineTurnoCorrente.size() && i<ordineArrivoGiocatori.size();i++){
           if(ordineArrivoGiocatori.get(i)!=null){
            ordineTurnoCorrente.add(i, ordineArrivoGiocatori.get(i));
                for (int j=i+1;j<ordineTurnoCorrente.size();j++){
                    if (ordineTurnoCorrente.get(j)==ordineArrivoGiocatori.get(i)){
                        ordineTurnoCorrente.remove(j);
                    }
                }
           }
        }

        //setTurnoCorrente();
        svuotaOrdineArrivo();
        return ordineTurnoCorrente;
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
