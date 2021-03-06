package partita;

import partita.carteDaGioco.CartaImpresa;
import partita.carteDaGioco.CartaScomunica;
import partita.componentiDelTabellone.Familiare;
import partita.componentiDelTabellone.Giocatore;
import partita.componentiDelTabellone.Torre;
import partita.componentiDelTabellone.Vaticano;
import partita.eccezioniPartita.DadiNonTiratiException;
import partita.eccezioniPartita.ForzaInsufficienteException;
import partita.eccezioniPartita.TurnoException;
import partita.eccezioniPartita.ZonaOccupataExcepion;
import server.GiocatoreRemoto;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Pietro on 16/05/2017.
 */
public class Partita {
    public static int N_GIOCATORI;
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
    private boolean partitaInCorso;

    /**
     *
     * @param numeroGiocatori numero giocatori della partita
     */
    public Partita(int numeroGiocatori) {
        N_GIOCATORI=numeroGiocatori;
        giocatori=new ArrayList<>();
        giocatoriGioco= new ArrayList<>();
        ordineTurno = new ArrayList<>();
        dadiTirati=false;
    }

    public ArrayList<GiocatoreRemoto> getGiocatori() {
        return giocatori;
    }

    private void ordineTurnoIniziale() {
        for(GiocatoreRemoto gr: giocatori){
            ordineTurno.add(gr.getGiocatore());
        }
        System.out.println("ordine turno:"+ordineTurno.size());
        giocatoreCorrente=ordineTurno.get(0);
    }

    public void addGiocatore(GiocatoreRemoto giocatoreRemoto) {
        if (giocatori.size()<N_GIOCATORI){
            giocatori.add(giocatoreRemoto);
            setRisorse(giocatoreRemoto);
        }
    }

    private void setRisorse(GiocatoreRemoto giocatoreRemoto) {
        giocatoreRemoto.getGiocatore().setPietra(2);
        giocatoreRemoto.getGiocatore().setLegna(2);
        giocatoreRemoto.getGiocatore().setServitori(3);
        giocatoreRemoto.getGiocatore().setMonete(giocatori.size()+4);
    }

    private void creaGiocatoriGioco(){
        for(GiocatoreRemoto gr: giocatori){
            giocatoriGioco.add(gr.getGiocatore());
        }
    }

    public void iniziaPartita() throws RemoteException {
        partitaInCorso=true;
        System.out.println("Partita a "+N_GIOCATORI+" giocatori");
        for (GiocatoreRemoto g: giocatori){
            System.out.println(g.getUsername());
        }
        System.out.println("Partita Iniziata");
        creaGiocatoriGioco();
        ordineTurnoIniziale();
        creaCampoDaGioco();
        periodo=1;
        turno=1;
        numeroMosseTurno=0;
        getCampoDaGioco().mettiCarteNelleTorri();
        System.out.println("Inizio il primo turno, il mazzo territorio ha: "+campoDaGioco.getMazzoTerritorio().size());
        nomiCarte=getCampoDaGioco().getNomiCarteTorri();
        nomiScomuniche=getCampoDaGioco().getCarteScomunica();
        //avvisa i client che la partita è iniziata e possono fare le mosse
        avvisoInizioPartita();
        System.out.println("giocatori avvisati");
    }

    private void avvisoInizioPartita() throws RemoteException {
        System.out.println("Avviso inizio Partita");
        ArrayList<String> nomiGiocatori=new ArrayList<>();
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

    private synchronized void setGiocatoreCorrente(Giocatore g){
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

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    private void settaProssimoGiocatore(GiocatoreRemoto giocatoreRemoto) {
        System.out.println("setto il prossimo giocatore");
        for (int i=0; i<N_GIOCATORI; i++){
            if(giocatoreRemoto.getGiocatore().equals(giocatori.get(i).getGiocatore())) {
                if (i < N_GIOCATORI - 1) {
                    setGiocatoreCorrente(giocatori.get(i + 1).getGiocatore());
                    try {
                        System.out.println("mando il messaggio");
                        giocatori.get(i+1).messaggio("E' il tuo turno");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    setGiocatoreCorrente(giocatori.get(0).getGiocatore());
                    try {
                        System.out.println("mando il messaggio");
                        giocatori.get(0).messaggio("E' il tuo turno");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void passaMossa(GiocatoreRemoto giocatoreRemoto) throws ZonaOccupataExcepion, ForzaInsufficienteException {
        //ripristinaForzaTabellone();
        numeroMosseTurno++;
        if(numeroMosseTurno==giocatori.size()*4){
            passaTurno();
            numeroMosseTurno=0;
            setGiocatoreCorrente(ordineTurno.get(0));

        }else {
            settaProssimoGiocatore(giocatoreRemoto);
        }



    }

    private void passaTurno() throws ForzaInsufficienteException, ZonaOccupataExcepion {

        pulisciTabellone();
        dadiTirati=false;
        ordineTurno=getCampoDaGioco().getTabellone().getPalazzoDelConsiglio().calcoaTurnoSuccessivo(ordineTurno);
        getCampoDaGioco().getTabellone().getPalazzoDelConsiglio().stampaOrdineTurno(ordineTurno);
        System.out.println("Sono in passa turno");
        if(!(periodo==3 && turno==2)) {
            System.out.println("Sono nell'if di passa turno");
            getCampoDaGioco().mettiCarteNelleTorri();
            nomiCarte = getCampoDaGioco().getNomiCarteTorri();
        }
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
        if(partitaInCorso) {
            for (GiocatoreRemoto g : giocatori) {
                g.avvisoInizioTurno(nomiCarte);
            }
        }
    }

    private void passaPeriodo() throws ForzaInsufficienteException, ZonaOccupataExcepion {

        finePeriodo();
        periodo ++;
        System.out.println("fine periodo");
        if (periodo==4){
            finePartita();
        }
    }

    private void finePartita() {
        System.out.println("fine partita");
        partitaInCorso=false;
        calcolaClassifica();
        ArrayList<Integer> classificaId=new ArrayList<>();
        ArrayList<String> classificaUsername=new ArrayList<>();
        ArrayList<Integer> classificaPunti=new ArrayList<>();
        for(GiocatoreRemoto g: giocatori){
            classificaId.add(g.getGiocatore().getId());
            classificaUsername.add(g.getUsername());
            classificaPunti.add(g.getGiocatore().getPuntiVittoria());
        }
        for (GiocatoreRemoto g: giocatori){
            try {
                g.finePartita(classificaId,classificaUsername,classificaPunti);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ordina la lista giocatori in ordine di punti vittoria
     */
    private void calcolaClassifica(){
        Giocatore g;
        for (GiocatoreRemoto giocatoreRemoto: giocatori){
            g=giocatoreRemoto.getGiocatore();
            puntiTerritori(g);
            puntiPersonaggi(g);
            puntiImprese(g);
            puntiRisorse(g);
            puntiPuntiFede(g);
            attivaScomuniche(g);
        }

        GiocatoreRemoto[] classifica=new GiocatoreRemoto[giocatori.size()];

        for (int i=0; i<giocatori.size();i++){
            classifica[i]=giocatori.get(i);
        }

        GiocatoreRemoto tmp;

        for (int i=0;i<giocatori.size();i++){
            for (int j=0;j<(giocatori.size()-1)-i;j++){
                if (classifica[j].getGiocatore().getPuntiVittoria()<classifica[j+1].getGiocatore().getPuntiVittoria()){
                    tmp=classifica[j];
                    classifica[j]=classifica[j+1];
                    classifica[j+1]=tmp;
                }
            }
        }

        giocatori.clear();
        giocatori.addAll(Arrays.asList(classifica));
    }

    private void attivaScomuniche(Giocatore g) {
        for(CartaScomunica c: g.getScomuniche()){
            c.attivaEffettoScomunica(g.getFamiliari()[0],"",0);
        }
    }

    private void puntiPuntiFede(Giocatore g) {
        g.setPuntiVittoria(g.getPuntiVittoria()+g.getPuntiFede());
    }

    private void puntiRisorse(Giocatore g) {
        int quantitaRisorse;
        quantitaRisorse=g.getPietra()+g.getLegna()+g.getServitori()+g.getMonete();
        g.setPuntiVittoria(g.getPuntiVittoria()+(quantitaRisorse/5));
    }

    private void puntiImprese(Giocatore g) {
        for(CartaImpresa c: g.getCarteImpresa()){
            c.attivaEffettoPermanente(g.getFamiliari()[0]);
        }
    }

    private void puntiPersonaggi(Giocatore g) {
        switch (g.getCartePersonaggio().size()){
            case 1: g.setPuntiVittoria(g.getPuntiVittoria()+1);
                break;
            case 2: g.setPuntiVittoria(g.getPuntiVittoria()+3);
                break;
            case 3: g.setPuntiVittoria(g.getPuntiVittoria()+6);
                break;
            case 4: g.setPuntiVittoria(g.getPuntiVittoria()+10);
                break;
            case 5: g.setPuntiVittoria(g.getPuntiVittoria()+15);
                break;
            case 6: g.setPuntiVittoria(g.getPuntiVittoria()+21);
                break;
        }
    }

    private void puntiTerritori(Giocatore g) {
        switch (g.getCarteTerritorio().size()){
            case 3: g.setPuntiVittoria(g.getPuntiVittoria()+1);
                break;
            case 4: g.setPuntiVittoria(g.getPuntiVittoria()+4);
                break;
            case 5: g.setPuntiVittoria(g.getPuntiVittoria()+10);
                break;
            case 6: g.setPuntiVittoria(g.getPuntiVittoria()+20);
                break;
        }
    }

    private void pulisciTabellone() throws ZonaOccupataExcepion, ForzaInsufficienteException {

        //pulisco il tabellone per il turno successivo
        Torre[] torri;
        torri=getCampoDaGioco().getTabellone().getTorri();

        for (int i=0;i<4;i++){
            torri[i].pulisciTorre();
        }

        getCampoDaGioco().getTabellone().getZonaProduzione().svuotaZona();
        getCampoDaGioco().getTabellone().getZonaRaccolto().svuotaZona();
        getCampoDaGioco().getTabellone().getMercato().svuotaMercato();
    }

    private void finePeriodo() throws ZonaOccupataExcepion, ForzaInsufficienteException{

        //pulisco il tabellone per il periodo successivo
        Vaticano vat = getCampoDaGioco().getTabellone().getVaticano();

        for (int i=0;i<giocatori.size();i++){
            //se il giocatore ha abbastanza punti fede gli faccio scesgliere se appoggiare la chiesa o no,
            //se non li ha lo scomunico
            if (vat.controlloPuntiFede(giocatori.get(i).getGiocatore(), periodo)){
                try {
                    giocatori.get(i).scegliScomunica();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            //se il giocatore è scomunicato lo dico a tutti i giocatori
            else{
                for(GiocatoreRemoto g: giocatori){
                    try {
                        if(g!=null)
                        g.giocatoreScomunicato(giocatori.get(i).getGiocatore().getId(), giocatori.get(i).getPartita().getPeriodo());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
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
