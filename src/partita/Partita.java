package partita;

import partita.componentiDelTabellone.Giocatore;
import partita.componentiDelTabellone.Tabellone;
import partita.eccezioniPartita.TurnoException;
import server.GiocatoreRemoto;
import server.rmiServer.GiocatoreRMI;

import java.util.ArrayList;

/**
 * Created by Pietro on 16/05/2017.
 */
public class Partita {
    private ArrayList<GiocatoreRemoto> giocatori;
    private CampoDaGioco campoDaGioco;
    private ArrayList<Giocatore> ordineTurno;
    private Giocatore giocatoreCorrente;
    private int periodo;
    private int turno;
    private int numeroMosseTurno;
    private int valoreDadoNero;
    private int valoreDadoArancio;
    private int valoreDadoBianco;

    public Partita() {
        giocatori=new ArrayList<>();
        ordineTurno = new ArrayList<Giocatore>();
    }

    private void ordineTurnoIniziale() {
        for(GiocatoreRemoto gr: giocatori){
            ordineTurno.add(gr.getGiocatore());
        }
        System.out.println("ordine turno:"+ordineTurno.size());
        giocatoreCorrente=ordineTurno.get(0);
    }

    public void addGiocatore(GiocatoreRemoto giocatoreRemoto){
        giocatori.add(giocatoreRemoto);
    }

    public void iniziaPartita(){
        ordineTurnoIniziale();
        creaCampoDaGioco();
        periodo=1;
        turno=1;
        numeroMosseTurno=0;
        getCampoDaGioco().mettiCarteNelleTorri();
        //avvisa il client delle carte delle torri;
    }




    public CampoDaGioco getCampoDaGioco() {
        return campoDaGioco;
    }

    private void creaCampoDaGioco() {
        ArrayList<Giocatore> modelloGiocatori=new ArrayList<>();
        for(GiocatoreRemoto gr: giocatori){
            modelloGiocatori.add(gr.getGiocatore());
        }
        campoDaGioco = new CampoDaGioco(modelloGiocatori,true,true);
    }

    public synchronized void setGiocatoreCorrente(Giocatore g){
        giocatoreCorrente=g;
    }

    public synchronized void mioTurno(Giocatore g) throws TurnoException{
        if(!(giocatoreCorrente==g)) {
            throw new TurnoException("Non è il tuo turno");
        }
    }

    public ArrayList<Giocatore> getOrdineTurno() {
        return ordineTurno;
    }

    public int getPeriodo() {
        return periodo;
    }

    public int getValoreDadoArancio() {
        return valoreDadoArancio;
    }

    public int getValoreDadoBianco() {
        return valoreDadoBianco;
    }

    public int getValoreDadoNero() {
        return valoreDadoNero;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public int getTurno() {
        return turno;
    }

    public int getNumeroMosseTurno() {
        return numeroMosseTurno;
    }

    public void setNumeroMosseTurno(int numeroMosseTurno) {
        this.numeroMosseTurno = numeroMosseTurno;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    private void settaProssimoGiocatore() {

    }

    public void passaMossa(GiocatoreRMI giocatoreRMI) {
        fineMossa();
        numeroMosseTurno++;
        if(numeroMosseTurno==giocatori.size()*4){
            passaTurno();
            numeroMosseTurno=0;
        }

        settaProssimoGiocatore();

    }

    public void passaTurno() {

        fineTurno();
        turno++;
        if(turno==3){
            passaPeriodo();
            turno=1;
            ordineTurno=campoDaGioco.getTabellone().getPalazzoDelConsiglio().calcoaTurnoSuccessivo(ordineTurno);
        }

    }

    private void passaPeriodo() {

        finePeriodo();
        periodo ++;
        if (periodo==4){
            finePartita();
        }
    }

    private void fineMossa() {
        //ripristina i costi
    }

    public void fineTurno(){
        //togle le carte e i familiari
    }
    public void finePeriodo(){
        //si occupa delle scomuniche
    }

    private void finePartita() {

        //calcola i punti vittoria
            //punti fede
            //quello che ha più punti militari
            //carte impresa
            //carte personaggio
            //risorse
    }
}

/**
 * https://github.com/PietroChitto/Prova-Finale/invitations
 */
