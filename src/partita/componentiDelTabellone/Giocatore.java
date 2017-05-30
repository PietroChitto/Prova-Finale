package partita.componentiDelTabellone;

import partita.carteDaGioco.*;
import partita.carteDaGioco.*;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Created by william on 10/05/2017.
 */
public class Giocatore implements Serializable{
    private String nome;
    private int id;
    private Familiare[] familiari;
    private int turno; //ordine di gioco giocatore
    private int legna; //risorsa
    private int pietra; //risorsa
    private int monete; //risorsa
    private int servitori; //risorsa
    private int pergamene; //risorsa
    private int puntiVittoria; //punti
    private int puntiFede; //punti
    private int puntiMilitari; //punti
    private ArrayList<CartaScomunica> scomuniche;
    private ArrayList<CartaTerritorio> carteTerritorio;
    private ArrayList<CartaEdificio> carteEdificio;
    private ArrayList<CartaPersonaggio> cartePersonaggio;
    private ArrayList<CartaImpresa> carteImpresa;


    public Giocatore(int id) { //costruttore per condizioni iniziali del Player

        this.id = id;
        this.legna = 2;
        this.pietra = 2;
        this.servitori = 3;
        this.monete = 0;
        this.pergamene = 0;
        this.puntiFede = 0;
        this.puntiMilitari = 0;
        this.puntiVittoria = 0;
        this.familiari = new Familiare[4];
        creaFamiliari();
        scomuniche = new ArrayList<CartaScomunica>();
        carteTerritorio = new ArrayList<CartaTerritorio>();
        carteEdificio = new ArrayList<CartaEdificio>();
        cartePersonaggio = new ArrayList<CartaPersonaggio>();
        carteImpresa = new ArrayList<CartaImpresa>();
    }

    private void creaFamiliari() {
        familiari[0] = new Familiare(false, id, 0, "arancio", true, this);
        familiari[1] = new Familiare(false, id, 0, "bianco", true, this);
        familiari[2] = new Familiare(false, id, 0, "nero", true, this);
        familiari[3] = new Familiare(true, id, 0, "neutro", true, this);
    }

    public int getPuntiFede() {
        return puntiFede;
    }

    public ArrayList<CartaScomunica> getScomuniche() {
        return scomuniche;
    }

    public ArrayList<CartaEdificio> getCarteEdificio() {
        return carteEdificio;
    }

    public ArrayList<CartaPersonaggio> getCartePersonaggio() {
        return cartePersonaggio;
    }

    public ArrayList<CartaImpresa> getCarteImpresa() {
        return carteImpresa;
    }

    public ArrayList<CartaTerritorio> getCarteTerritorio() {
        return carteTerritorio;
    }

    public Familiare[] getFamiliari() {
        return familiari;
    }

    public int getId() {
        return id;
    }

    public int getLegna() {
        return legna;
    }

    public int getMonete() {
        return monete;
    }

    public int getPietra() {
        return pietra;
    }

    public int getPuntiMilitari() {
        return puntiMilitari;
    }

    public int getPuntiVittoria() {
        return puntiVittoria;
    }

    public int getServitori() {
        return servitori;
    }

    public int getTurno() {
        return turno;
    }

    public void setLegna(int legna) {
        this.legna = legna;
    }

    public void setMonete(int monete) {
        this.monete = monete;
    }

    public void setPietra(int pietra) {
        this.pietra = pietra;
    }

    public void setPuntiMilitari(int puntiMilitari) {
        this.puntiMilitari = puntiMilitari;
    }

    public void setPuntiFede(int puntiFede) {
        this.puntiFede = puntiFede;
    }

    public void setPuntiVittoria(int puntiVittoria) {
        this.puntiVittoria = puntiVittoria;
    }

    public void setServitori(int servitori) {
        this.servitori = servitori;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public void aggiungiScomunica(CartaScomunica carta){
        scomuniche.add(carta);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void aggiungiTerritorio(CartaTerritorio carta){
        if(carteTerritorio.size()<6) {
            carteTerritorio.add(carta);
        }
    }

    public void aggiungiEdificio(CartaEdificio carta){
        if(carteEdificio.size()<6) {
            carteEdificio.add(carta);
        }
    }

    public void aggiungiPersonaggio(CartaPersonaggio carta){
        if (cartePersonaggio.size()<6) {
            cartePersonaggio.add(carta);
        }
    }

    public void aggiungiImpresa(CartaImpresa carta){
        if(carteImpresa.size()<6) {
            carteImpresa.add(carta);
        }
    }

    public void incrementaRisorse(int [] incrementi){
        pietra += incrementi[0];
        legna += incrementi[1];
        servitori += incrementi[2];
        monete += incrementi[3];
        puntiMilitari += incrementi[4];
        puntiFede += incrementi[5];
        puntiVittoria += incrementi[6];
    }

    public void decrementaRisorse(int [] incrementi){
        pietra += -incrementi[0];
        legna += -incrementi[1];
        servitori += -incrementi[2];
        monete += -incrementi[3];
        puntiMilitari += -incrementi[4];
        puntiFede += -incrementi[5];
        puntiVittoria += -incrementi[6];
    }

    public void scambiaPergamena(int scelta){
        switch (scelta){
            case 1: pietra += 1;
                    legna += 1;
                    break;
            case 2: servitori+=2;
                    break;
            case 3: monete += 2;
                    break;
            case 4: puntiMilitari += 2;
                    break;
            case 5: puntiFede += 1;
                    break;
        }
    }



}
