package partita.componentiDelTabellone;

/**
 * Created by Pietro on 10/05/2017.
 */
public class Torre {
    private String tipo;
    private Piano[] piani;
    private boolean occupata;

    public Torre(String tipo){
        this.tipo=tipo;
        piani = new Piano[4];
        //creo il primo piano che ha costo 1 e non ha effetto
        piani[0]=new Piano(1,false, "", 1);
        //creo il secondo piano che ha costo 3 e non ha effetto
        piani[1]=new Piano(3,false, "",2);
        //creo il terzo piano che ha costo 5 e ha effetto
        piani[2]=new Piano(5,true, "incrementaRisorse1",3);
        //creo il primo piano che ha costo 7 e ha effetto
        piani[3]=new Piano(7,false, "IncrementaRisorse2",4);
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
        occupata=false;
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
}
