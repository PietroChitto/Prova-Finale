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
    private ArrayList<CartaTerritorio> mazzoTerritorio;
    private ArrayList<CartaEdificio> mazzoEdificio;
    private ArrayList<CartaPersonaggio> mazzoPersonaggi;
    private ArrayList<CartaImpresa> mazzoImprese;
    public final int NUMEROGIOCATORI;//final ??

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

    }

    public void test(){
        CampoDaGioco c = new CampoDaGioco(giocatori, false, true);
    }

    private void creaMazzoTerritorio() {
        mazzoTerritorio=new ArrayList<CartaTerritorio>();
        try {
            mazzoTerritorio = creatoreMazzi.terzoPeriodoTerreni();
            mazzoTerritorio.addAll(creatoreMazzi.secondoPeriodoTerreni());
            mazzoTerritorio.addAll(creatoreMazzi.primoPeriodoTerreni());
        } catch (SQLException e) {
            System.out.println("errori SQL");
            e.printStackTrace();
        }
    }

    private void creaMazzoEdifici() {
        mazzoEdificio=new ArrayList<CartaEdificio>();
        try {
            mazzoEdificio = creatoreMazzi.terzoPeriodoEdificio();
            mazzoEdificio.addAll(creatoreMazzi.secondoPeriodoEdificio());
            mazzoEdificio.addAll(creatoreMazzi.primoPeriodoEdificio());
        } catch (SQLException e) {
            System.out.println("errori SQL");
            e.printStackTrace();
        }
    }

    private void creaMazzoPersonaggio() {
        mazzoPersonaggi=new ArrayList<CartaPersonaggio>();
        try {
            mazzoPersonaggi = creatoreMazzi.terzoPeriodoPersonaggio();
            mazzoPersonaggi.addAll(creatoreMazzi.secondoPeriodoPersonaggio());
            mazzoPersonaggi.addAll(creatoreMazzi.primoPeriodoPersonaggio());
        } catch (SQLException e) {
            System.out.println("errori SQL");
            e.printStackTrace();
        }
    }

    private void creaMazzoImprese() {
        mazzoImprese=new ArrayList<CartaImpresa>();
        try {
            mazzoImprese = creatoreMazzi.terzoPeriodoImpresa();
            mazzoImprese.addAll(creatoreMazzi.secondoPeriodoImpresa());
            mazzoImprese.addAll(creatoreMazzi.primoPeriodoImpresa());
        } catch (SQLException e) {
            System.out.println("errori SQL");
            e.printStackTrace();
        }
    }

    public ArrayList<CartaEdificio> getMazzoEdificio() {
        return mazzoEdificio;
    }

    public ArrayList<CartaImpresa> getMazzoImprese() {
        return mazzoImprese;
    }

    public ArrayList<CartaPersonaggio> getMazzoPersonaggi() {
        return mazzoPersonaggi;
    }

    public ArrayList<CartaTerritorio> getMazzoTerritorio() {
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
            mazzoTerritorio.remove(0);
            tabellone.getTorre(1).getPiano(i).setCartaSviluppo(mazzoPersonaggi.get(0));
            mazzoPersonaggi.remove(0);
            tabellone.getTorre(2).getPiano(i).setCartaSviluppo(mazzoEdificio.get(0));
            mazzoEdificio.remove(0);
            tabellone.getTorre(3).getPiano(i).setCartaSviluppo(mazzoImprese.get(0));
            mazzoImprese.remove(0);
        }
    }

    //commento


}