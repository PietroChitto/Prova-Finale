package partita.componentiDelTabellone;

import partita.eccezioniPartita.TorreOccupataException;

/**
 * Created by Pietro on 10/05/2017.
 */
public class Torre {
    private String tipo;
    private Piano[] piani;
    private boolean occupata;
    private int numeroTorre;

    public Torre(String tipo, int numeroTorre){
        this.tipo=tipo;
        this.numeroTorre=numeroTorre;
        piani = new Piano[4];
        //creo il primo piano che ha costo 1 e non ha effetto
        piani[0]=new Piano(1,false,  0, numeroTorre);
        //creo il secondo piano che ha costo 3 e non ha effetto
        piani[1]=new Piano(3,false, 1, numeroTorre);
        //creo il terzo piano che ha costo 5 e ha effetto
        piani[2]=new Piano(5,true,2,numeroTorre);
        //creo il primo piano che ha costo 7 e ha effetto
        piani[3]=new Piano(7,false, 3,numeroTorre);
        occupata=false;
    }

    public String getTipo() {
        return tipo;
    }

    public void controlloTorreOccupata(){
        for (Piano p: piani) {
            if(p.getCampoAzione().isOccupato()){
                occupata=true;
            }
        }
    }

    public boolean isOccupata(){
        controlloTorreOccupata();
        return occupata;
    }

    public Piano[] getPiani() {
        return piani;
    }

    public Piano getPiano(int i) {
        return piani[i];
    }

    public void setOccupata(boolean bool){
        occupata=bool;
    }

    public void pulisciCarte(){
        for (int i=0;i<4;i++){
            piani[i].togliCarta();
        }
    }

    public void controllaFamiliare(int id) throws TorreOccupataException{
        for (Piano p: piani) {
            if(p.getCampoAzione().getFamiliare()!=null && p.getCampoAzione().getFamiliare().getIdGiocatore()==id){
                throw new TorreOccupataException();
            }
        }
    }
}
