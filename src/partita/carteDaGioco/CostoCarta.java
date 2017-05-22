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

    public CostoCarta(int costoLegna, int costoPietra, int costoServitori, int costoMonete, int costoPuntiMilitare){

        this.costoLegna=costoLegna;
        this.costoMonete=costoMonete;
        this.costoPietra=costoPietra;
        this.costoPuntiMilitari=costoPuntiMilitari;
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
}
