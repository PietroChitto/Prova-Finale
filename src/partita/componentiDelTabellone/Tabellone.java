package partita.componentiDelTabellone;

/**
 * Created by Pietro on 11/05/2017.
 */
public class Tabellone {
    private Torre[] torri;
    private Vaticano vaticano;
    private Mercato mercato;
    private PalazzoDelConsiglio palazzoDelConsiglio;
    private Zona zonaProduzione;
    private Zona zonaRaccolto;
    private int numeroGiocatori;
    //private boolean treGiocatori;
    //private boolean quattroGiocatori;

    public Tabellone(boolean treGiocatori,boolean quattroGiocatori){
        if(quattroGiocatori)
            numeroGiocatori=4;
        else numeroGiocatori=2;
        System.out.println("creo le torri");
        torri=new Torre[4];
        torri[0]=new Torre("territori",0);
        torri[1]=new Torre("personaggi",1);
        torri[2]=new Torre("edifici",2);
        torri[3]=new Torre("imprese",3);
        System.out.println("creo vaticano");
        vaticano=new Vaticano();
        System.out.println("creo mercato");
        mercato=new Mercato(!quattroGiocatori);
        System.out.println("creo palazzo del consiglio");
        palazzoDelConsiglio = new PalazzoDelConsiglio(numeroGiocatori);
        System.out.println("creo zona produzione");
        zonaProduzione=new Zona(true, treGiocatori);
        System.out.println("creo zona raccolto");
        zonaRaccolto=new Zona(false, treGiocatori);
    }

    public Mercato getMercato() {
        return mercato;
    }

    public PalazzoDelConsiglio getPalazzoDelConsiglio() {
        return palazzoDelConsiglio;
    }

    public Vaticano getVaticano() {
        return vaticano;
    }

    public Torre[] getTorri() {
        return torri;
    }

    public Torre getTorre(int i) {
        return torri[i];
    }

    public Zona getZonaProduzione() {
        return zonaProduzione;
    }

    public Zona getZonaRaccolto() {
        return zonaRaccolto;
    }

}

