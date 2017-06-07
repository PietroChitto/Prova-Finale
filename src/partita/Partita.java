package partita;

import partita.carteDaGioco.CartaEdificio;
import partita.carteDaGioco.CartaPersonaggio;
import partita.carteDaGioco.CartaTerritorio;
import partita.carteDaGioco.effetti.effettiCarte.EffettoAumentaForza;
import partita.componentiDelTabellone.*;
import partita.eccezioniPartita.DadiNonTiratiException;
import partita.eccezioniPartita.ForzaInsufficienteException;
import partita.eccezioniPartita.TurnoException;
import partita.eccezioniPartita.ZonaOccupataExcepion;
import server.GiocatoreRemoto;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Pietro on 16/05/2017.
 */
public class Partita {
    public static final int N_GIOCATORI=2;
    private ArrayList<GiocatoreRemoto> giocatori;
    private ArrayList<Giocatore> giocatoriGioco;
    private CampoDaGioco campoDaGioco;
    private ArrayList<Giocatore> ordineTurno;
    private Giocatore giocatoreCorrente;
    private int periodo;
    private int turno;
    private int numeroMosseTurno;
    private int valoreDadoNero;
    private int valoreDadoArancio;
    private int valoreDadoBianco;
    private ArrayList<String> nomiCarte;
    private ArrayList<String> nomiScomuniche;
    private boolean dadiTirati;

    public Partita() {
        giocatori=new ArrayList<>();
        giocatoriGioco= new ArrayList<Giocatore>();
        ordineTurno = new ArrayList<Giocatore>();
        dadiTirati=false;
    }

    public ArrayList<GiocatoreRemoto> getGiocatori() {
        return giocatori;
    }

    public ArrayList<Giocatore> getGiocatoriGioco() {
        return giocatoriGioco;
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
        //setta risorse giocatore
        setRisorse(giocatoreRemoto);
    }

    private void setRisorse(GiocatoreRemoto giocatoreRemoto) {
        giocatoreRemoto.getGiocatore().setPietra(2);
        giocatoreRemoto.getGiocatore().setLegna(2);
        giocatoreRemoto.getGiocatore().setServitori(3);
        giocatoreRemoto.getGiocatore().setMonete(giocatori.size()+4);
    }

    public void creaGiocatoriGioco(){
        for(GiocatoreRemoto gr: giocatori){
            giocatoriGioco.add(gr.getGiocatore());
        }
    }

    public void iniziaPartita() throws RemoteException {
        System.out.println("Partita Iniziata");
        creaGiocatoriGioco();
        ordineTurnoIniziale();
        creaCampoDaGioco();
        periodo=1;
        turno=1;
        numeroMosseTurno=0;
        getCampoDaGioco().mettiCarteNelleTorri();
        nomiCarte=getCampoDaGioco().getNomiCarteTorri();
        nomiScomuniche=getCampoDaGioco().getCarteScomunica();
        //avvisa i client che la partita è iniziata e possono fare le mosse
        avvisoInizioPartita();
        System.out.println("giocatori avvisati");
    }

    private void avvisoInizioPartita() throws RemoteException {
        System.out.println("Avviso inizio Partita");
        ArrayList<String> nomiGiocatori=new ArrayList<String>();
        for (GiocatoreRemoto g: giocatori){
            nomiGiocatori.add(g.getUsername());
        }
        System.out.print("giocatori remoti: "+giocatori.size());
        for(GiocatoreRemoto g: giocatori){
            int[] risorse=new int[4];
            //creo l'array di risorse da passare al client
            risorse[0]=g.getGiocatore().getPietra();
            risorse[1]=g.getGiocatore().getLegna();
            risorse[2]=g.getGiocatore().getServitori();
            risorse[3]=g.getGiocatore().getMonete();
            g.iniziaPartita(g.getGiocatore().getId(), nomiCarte, nomiGiocatori, risorse, nomiScomuniche);
        }
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
        System.out.println("tocca al giocatore "+ g.getId());
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

    private void settaProssimoGiocatore(GiocatoreRemoto giocatoreRemoto) {
        for (int i=0; i<N_GIOCATORI; i++){
            if(giocatoreRemoto.getGiocatore().equals(giocatori.get(i).getGiocatore())) {
                if (i < N_GIOCATORI - 1) {
                    setGiocatoreCorrente(giocatori.get(i + 1).getGiocatore());
                } else {
                    setGiocatoreCorrente(giocatori.get(0).getGiocatore());
                }
            }
        }
    }

    public void passaMossa(GiocatoreRemoto giocatoreRemoto) throws ZonaOccupataExcepion, ForzaInsufficienteException {
        //ripristinaForzaTabellone();
        System.out.println("sono in passa mossa");
        numeroMosseTurno++;
        if(numeroMosseTurno==giocatori.size()*4){
            System.out.println("passa Turno");
            passaTurno();
            numeroMosseTurno=0;
            setGiocatoreCorrente(ordineTurno.get(0));

        }else {
            System.out.println("ramo else");
            settaProssimoGiocatore(giocatoreRemoto);
        }



    }

    public void passaTurno() throws ForzaInsufficienteException, ZonaOccupataExcepion {

        pulisciTabellone();
        dadiTirati=false;
        ordineTurno=getCampoDaGioco().getTabellone().getPalazzoDelConsiglio().calcoaTurnoSuccessivo(ordineTurno);
        getCampoDaGioco().getTabellone().getPalazzoDelConsiglio().stampaOrdineTurno(ordineTurno);
        getCampoDaGioco().mettiCarteNelleTorri();
        nomiCarte=getCampoDaGioco().getNomiCarteTorri();
        ripristinaDisponibilitàFamiliari();
        turno++;
        if(turno==3){
            passaPeriodo();
            turno=1;
        }
        try {
            avvisoInizioTurno();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    private void ripristinaDisponibilitàFamiliari() {
        for(Giocatore g: giocatoriGioco){
            for(Familiare f: g.getFamiliari()){
                f.setDisponibile(true);
            }
        }
    }

    private void avvisoInizioTurno() throws RemoteException {
        for(GiocatoreRemoto g: giocatori){
            g.avvisoInizioTurno(nomiCarte);
        }
    }

    private void passaPeriodo() throws ForzaInsufficienteException, ZonaOccupataExcepion {

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



    private void finePartita() {

        //calcola i punti vittoria
            //punti fede
            //quello che ha più punti militari
            //carte impresa
            //carte personaggio
            //risorse
    }

    public void pulisciTabellone() throws ZonaOccupataExcepion, ForzaInsufficienteException {

        //pulisco il tabellone per il turno successivo
        Torre[] torri;
        torri=getCampoDaGioco().getTabellone().getTorri();

        int posizione=0;

        for (int i=0;i<4;i++){
            torri[i].pulisciTorre();
        }

        getCampoDaGioco().getTabellone().getZonaProduzione().svuotaZona();
        getCampoDaGioco().getTabellone().getZonaRaccolto().svuotaZona();
        getCampoDaGioco().getTabellone().getMercato().svuotaMercato();
    }

    public void finePeriodo() throws ZonaOccupataExcepion, ForzaInsufficienteException{

        //pulisco il tabellone per il periodo successivo
        Vaticano vat = getCampoDaGioco().getTabellone().getVaticano();

        for (int i=0;i<giocatori.size();i++){

            if (vat.controlloPuntiFede(giocatori.get(i).getGiocatore(), periodo)){
                //implementa scelta gestione punti fede
                //invia al client la possibilità di secglere

            }
        }
    }


    public void setValoreDadoArancio(int i) {
        this.valoreDadoArancio=i;
    }

    public void setValoreDadoBianco(int valoreDadoBianco) {
        this.valoreDadoBianco = valoreDadoBianco;
    }

    public void setValoreDadoNero(int valoreDadoNero) {
        this.valoreDadoNero = valoreDadoNero;
    }

    public void setDadiTirati(boolean dadiTirati) {
        this.dadiTirati = dadiTirati;
    }

    public void dadiTirati() throws DadiNonTiratiException{
        if(!dadiTirati)
            throw new DadiNonTiratiException();
    }
}

/**
 * https://github.com/PietroChitto/Prova-Finale/invitations
 */
