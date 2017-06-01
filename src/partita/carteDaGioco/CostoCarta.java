package partita.carteDaGioco;

/**
 * Created by william on 10/05/2017.
 */
public class CostoCarta {

    private int costoLegna;
    private int costoPietra;
    private int costoServitori;
    private int costoMonete;
    private int costoPuntiMilitari;

    public CostoCarta(int costoPietra, int costoLegna, int costoServitori, int costoMonete, int costoPuntiMilitare){

        this.costoLegna=costoLegna;
        this.costoMonete=costoMonete;
        this.costoPietra=costoPietra;
        this.costoPuntiMilitari=costoPuntiMilitare;
        this.costoServitori=costoServitori;

    }

    public int getCostoLegna() {
        return costoLegna;
    }

    public int getCostoMonete() {
        return costoMonete;
    }

    public int getCostoPietra() {
        return costoPietra;
    }

    public int getCostoPuntiMilitari() {
        return costoPuntiMilitari;
    }

    public int getCostoServitori() {
        return costoServitori;
    }

    public int[] toArray(){
        int[] array =new int[7];
        array[0]=costoPietra;
        array[1]=costoLegna;
        array[2]=costoServitori;
        array[3]=costoMonete;
        array[4]=costoPuntiMilitari;
        array[5]=0;
        array[6]=0;
        return array;
    }
}
