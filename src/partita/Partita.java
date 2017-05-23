package partita;

import partita.carteDaGioco.CartaEdificio;
import partita.carteDaGioco.CartaPersonaggio;
import partita.carteDaGioco.CartaTerritorio;
import partita.carteDaGioco.effetti.EffettoAumentaForza;
import partita.componentiDelTabellone.*;
import partita.eccezioniPartita.ForzaInsufficienteException;
import partita.eccezioniPartita.TurnoException;
import partita.eccezioniPartita.ZonaOccupataExcepion;
import server.GiocatoreRemoto;
import server.rmiServer.GiocatoreRMI;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Pietro on 16/05/2017.
 */
public class Partita {
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

    public Partita() {
        giocatori=new ArrayList<>();
        giocatoriGioco= new ArrayList<Giocatore>();
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

    public void creaGiocatoriGioco(){
        for(GiocatoreRemoto gr: giocatori){
            giocatoriGioco.add(gr.getGiocatore());
        }
    }

    public void iniziaPartita() throws RemoteException {
        creaGiocatoriGioco();
        System.out.println("creatiGiocatoriGioco");
        //avvisa i client che la partita è iniziata e possono fare le mosse
        avvisoInizioPartita();
        System.out.println("GiocatoriAvvisati");
        ordineTurnoIniziale();
        creaCampoDaGioco();
        periodo=1;
        turno=1;
        numeroMosseTurno=0;
        getCampoDaGioco().mettiCarteNelleTorri();
        //avvisa il client delle carte delle torri;
    }

    private void avvisoInizioPartita() throws RemoteException {
        for(GiocatoreRemoto g: giocatori){
            System.out.println("provo avviso...");
            g.iniziaPartita(g.getGiocatore().getId());
            System.out.println("avvisato");
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

    }

    public void passaMossa(GiocatoreRemoto giocatoreRemoto) throws ZonaOccupataExcepion, ForzaInsufficienteException {
        ripristinaForzaTabellone();
        numeroMosseTurno++;
        if(numeroMosseTurno==giocatori.size()*4){
            passaTurno();
            numeroMosseTurno=0;
        }

        settaProssimoGiocatore(giocatoreRemoto);

    }

    public void passaTurno() throws ForzaInsufficienteException, ZonaOccupataExcepion {

        pulisciTabellone();
        turno++;
        if(turno==3){
            passaPeriodo();
            turno=1;
            ordineTurno=campoDaGioco.getTabellone().getPalazzoDelConsiglio().calcoaTurnoSuccessivo(ordineTurno);
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

    public void ripristinaForzaTabellone(){

        String codice;
        int [] zone=new int[6];
        ArrayList<CartaPersonaggio> cartePersonaggio= new ArrayList<CartaPersonaggio>();

        //Ripristino la condizioni iniziali sulla forza a livello del tabellone
        cartePersonaggio=giocatoreCorrente.getCartePersonaggio();
        Tabellone tab = getCampoDaGioco().getTabellone();

        for (CartaPersonaggio cp : cartePersonaggio){

            if (cp.getEffettoP() instanceof EffettoAumentaForza){

                codice=cp.getCodEffP();

                int forza=codice.charAt(0);

                for (int i=0; i<6; i++){
                    zone[i]=(int) codice.charAt((i+1)*2);
                }

                if(zone[0]==1){
                    for (Piano p:tab.getTorre(1).getPiani()){
                        p.getCampoAzione().setCosto(p.getCampoAzione().getCosto()+forza);
                    }
                }
                if(zone[1]==1){
                    for (Piano p:tab.getTorre(2).getPiani()){
                        p.getCampoAzione().setCosto(p.getCampoAzione().getCosto()+forza);
                    }
                }
                if(zone[2]==1){
                    for (Piano p:tab.getTorre(3).getPiani()){
                        p.getCampoAzione().setCosto(p.getCampoAzione().getCosto()+forza);
                    }
                }
                if(zone[3]==1){
                    for (Piano p:tab.getTorre(4).getPiani()){
                        p.getCampoAzione().setCosto(p.getCampoAzione().getCosto()+forza);
                    }

                }
                if(zone[4]==1){
                    for (CartaEdificio cs: giocatoreCorrente.getCarteEdificio()){
                        cs.setCostoAttivazioneEffettoPermanente(cs.getCostoAttivazioneEffettoPermanente()+forza);
                    }
                }if(zone[5]==1){
                    for (CartaTerritorio cs: giocatoreCorrente.getCarteTerritorio()){
                        cs.setCostoAttivazioneEffettoPermanente(cs.getCostoAttivazioneEffettoPermanente()+forza);
                    }
                }


            }
        }
    }

    public void pulisciTabellone() throws ZonaOccupataExcepion, ForzaInsufficienteException {

        //pulisco il tabellone per il turno successivo
        Torre[] torri = new Torre[4];
        torri=getCampoDaGioco().getTabellone().getTorri();

        int posizione=0;

        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                torri[i].pulisciCarte();
                torri[i].getPiano(j).getCampoAzione().svuotaCampoAzione();
                torri[i].getPiano(j).getCampoAzione().setOccupato(false);
                torri[i].setOccupata(false);
            }
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


}

/**
 * https://github.com/PietroChitto/Prova-Finale/invitations
 */
