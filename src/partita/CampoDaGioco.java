package partita;

import partita.carteDaGioco.*;
import partita.componentiDelTabellone.Giocatore;
import partita.componentiDelTabellone.Tabellone;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Pietro on 10/05/2017.
 */
public class CampoDaGioco {
    private Tabellone tabellone;
    private ArrayList<Giocatore> giocatori;
    private CreatoreMazzi creatoreMazzi;
    private ArrayList<CartaSviluppo> mazzoTerritorio;
    private ArrayList<CartaSviluppo> mazzoEdificio;
    private ArrayList<CartaSviluppo> mazzoPersonaggi;
    private ArrayList<CartaSviluppo> mazzoImprese;
    private ArrayList<CartaScomunica> carteScomunica;
    public final int NUMEROGIOCATORI;

    public CampoDaGioco(ArrayList<Giocatore> giocatori, boolean treGiocatori, boolean quattroGiocatori){
        this.giocatori=giocatori;
        NUMEROGIOCATORI=giocatori.size();
        tabellone = new Tabellone(treGiocatori,quattroGiocatori);
        try {
            creatoreMazzi=new CreatoreMazzi();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        creaMazzoTerritorio();
        creaMazzoEdifici();
        creaMazzoImprese();
        creaMazzoPersonaggio();
        carteScomunica=creatoreMazzi.creaCarteScomunica();

    }

    public void test(){
        CampoDaGioco c = new CampoDaGioco(giocatori, false, true);
    }

    private void creaMazzoTerritorio() {
        mazzoTerritorio=new ArrayList<CartaSviluppo>();
        try {
            mazzoTerritorio = creatoreMazzi.primoPeriodoTerreni();
            mazzoTerritorio.addAll(creatoreMazzi.secondoPeriodoTerreni());
            mazzoTerritorio.addAll(creatoreMazzi.terzoPeriodoTerreni());
        } catch (SQLException e) {
            System.out.println("errori SQL");
            e.printStackTrace();
        }
    }

    private void creaMazzoEdifici() {
        mazzoEdificio=new ArrayList<CartaSviluppo>();
        try {
            mazzoEdificio = creatoreMazzi.primoPeriodoEdificio();
            mazzoEdificio.addAll(creatoreMazzi.secondoPeriodoEdificio());
            mazzoEdificio.addAll(creatoreMazzi.terzoPeriodoEdificio());
        } catch (SQLException e) {
            System.out.println("errori SQL");
            e.printStackTrace();
        }
    }

    private void creaMazzoPersonaggio() {
        mazzoPersonaggi=new ArrayList<CartaSviluppo>();
        try {
            mazzoPersonaggi = creatoreMazzi.primoPeriodoPersonaggio();
            mazzoPersonaggi.addAll(creatoreMazzi.secondoPeriodoPersonaggio());
            mazzoPersonaggi.addAll(creatoreMazzi.terzoPeriodoPersonaggio());
        } catch (SQLException e) {
            System.out.println("errori SQL");
            e.printStackTrace();
        }
    }

    private void creaMazzoImprese() {
        mazzoImprese=new ArrayList<CartaSviluppo>();
        try {
            mazzoImprese = creatoreMazzi.primoPeriodoImpresa();
            mazzoImprese.addAll(creatoreMazzi.secondoPeriodoImpresa());
            mazzoImprese.addAll(creatoreMazzi.terzoPeriodoImpresa());
        } catch (SQLException e) {
            System.out.println("errori SQL");
            e.printStackTrace();
        }
    }

    public ArrayList<CartaSviluppo> getMazzoEdificio() {
        return mazzoEdificio;
    }

    public ArrayList<CartaSviluppo> getMazzoImprese() {
        return mazzoImprese;
    }

    public ArrayList<CartaSviluppo> getMazzoPersonaggi() {
        return mazzoPersonaggi;
    }

    public ArrayList<CartaSviluppo> getMazzoTerritorio() {
        return mazzoTerritorio;
    }

    public Tabellone getTabellone() {
        return tabellone;
    }


   /* private void stampaMazzo(ArrayList<CartaTerritorio>  ct){
        for (CartaTerritorio c:ct) {
            System.out.println(c.getNome());
        }
    }*/

    public void mettiCarteNelleTorri() {
        for(int i=0; i<4; i++){
            tabellone.getTorre(0).getPiano(i).setCartaSviluppo(mazzoTerritorio.get(0));
            //System.out.println("torre 0, piano "+i+" : "+ tabellone.getTorre(0).getPiano(i).getCartaSviluppo().getNome()+ " periodo " +tabellone.getTorre(0).getPiano(i).getCartaSviluppo().getPeriodo());
            mazzoTerritorio.remove(0);

            tabellone.getTorre(1).getPiano(i).setCartaSviluppo(mazzoPersonaggi.get(0));
            //System.out.println("torre 1, piano "+i+" : "+ tabellone.getTorre(1).getPiano(i).getCartaSviluppo().getNome()+ " periodo " +tabellone.getTorre(1).getPiano(i).getCartaSviluppo().getPeriodo());
            mazzoPersonaggi.remove(0);

            tabellone.getTorre(2).getPiano(i).setCartaSviluppo(mazzoEdificio.get(0));
            //System.out.println("torre 2, piano "+i+" : "+ tabellone.getTorre(2).getPiano(i).getCartaSviluppo().getNome()+ " periodo " +tabellone.getTorre(2).getPiano(i).getCartaSviluppo().getPeriodo());
            mazzoEdificio.remove(0);

            tabellone.getTorre(3).getPiano(i).setCartaSviluppo(mazzoImprese.get(0));
            //System.out.println("torre 3, piano "+i+" : "+ tabellone.getTorre(3).getPiano(i).getCartaSviluppo().getNome()+ " periodo " +tabellone.getTorre(3).getPiano(i).getCartaSviluppo().getPeriodo());
            mazzoImprese.remove(0);
        }

    }

    public ArrayList<String> getNomiCarteTorri(){
        ArrayList<String> nomiCarte=new ArrayList<>();
        for(int i=0; i<4; i++){
            nomiCarte.add(tabellone.getTorre(i).getPiano(0).getCartaSviluppo().getNome());
            nomiCarte.add(tabellone.getTorre(i).getPiano(1).getCartaSviluppo().getNome());
            nomiCarte.add(tabellone.getTorre(i).getPiano(2).getCartaSviluppo().getNome());
            nomiCarte.add(tabellone.getTorre(i).getPiano(3).getCartaSviluppo().getNome());
        }
        return nomiCarte;
    }

    public ArrayList<String> getCarteScomunica(){
        ArrayList<String> nomiScomuniche=new ArrayList<>();
        for(CartaScomunica c: carteScomunica){
            nomiScomuniche.add(c.getNome());
        }
        return nomiScomuniche;
    }

}
