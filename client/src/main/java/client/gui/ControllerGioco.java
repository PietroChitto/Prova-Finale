package client.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import server.InterfacciaClient;
import server.rmiServer.InterfaciaServer;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class ControllerGioco implements InterfacciaClient {

    private InterfaciaServer clientGenerico;
    private ArrayList<ImageView> immaginiCarte;
    //i primi 4 sono della prima torre ecc
    private ArrayList<Pane> paneCampiAzioneTorri;
    private ImageView[] immaginiDadoNero;
    private ImageView[] immaginiDadoBianco;
    private ImageView[] immaginiDadoArancio;
    private int mioId;
    private ArrayList<FamiliareGrafico> familiari;
    private int contaCarteT;
    private int contaCarteP;
    private int contaCarteE;
    private int contaCarteI;
    private int contaFamiliariPalazzoConsigglio;
    private int contaFamZonaProd;
    private int contaFamZonaRac;

    public void setClientGenerico(InterfaciaServer clientGenerico){
        this.clientGenerico=clientGenerico;
    }



    public void inizializza(HashMap<Integer, String> giocatori, ArrayList<String> carte, int mioId, int[] risorse, ArrayList<String> scomuniche){
        if(giocatori.size()==0){
            System.out.println("MappaVuota");
        }
        for (int i=0; i<giocatori.size();i++){
            System.out.println(giocatori.get(i));
        }
        this.mioId=mioId;
        settaLabelGiocatori(giocatori);
        mettiCarteNelleTorri(carte);
        //settaSfondo();
        creaFamiliari(mioColore(mioId));
        creaDadi();
        settaRisorse(risorse);
        creaPaneCampiAzioneTorri();
        mettiScomuniche(scomuniche);
        contaCarteE=0;
        contaCarteI=0;
        contaCarteP=0;
        contaCarteT=0;
        contaFamiliariPalazzoConsigglio=0;
        contaFamZonaProd=0;
        contaFamZonaRac=0;
        //buttonForzaArancio.setStyle("-fx-shape: inherit");
    }

    private void mettiScomuniche(ArrayList<String> scomuniche) {
        Image tempImg = new Image("image/img/ImmaginiTessereScomunica/" + scomuniche.get(0) + ".png");
        imgScomunica1.setImage(tempImg);
        imgScomunica1.setFitWidth(gridScomunica.getWidth()/3);
        imgScomunica1.setFitHeight(gridScomunica.getHeight());

        tempImg = new Image("image/img/ImmaginiTessereScomunica/" + scomuniche.get(1) + ".png");
        imgScomunica2.setImage(tempImg);
        imgScomunica2.setFitWidth(gridScomunica.getWidth()/3);
        imgScomunica2.setFitHeight(gridScomunica.getHeight());

        tempImg = new Image("image/img/ImmaginiTessereScomunica/" + scomuniche.get(2) + ".png");
        imgScomunica3.setImage(tempImg);
        imgScomunica3.setFitWidth(gridScomunica.getWidth()/3);
        imgScomunica3.setFitHeight(gridScomunica.getHeight());

    }

    private void creaPaneCampiAzioneTorri() {
        Pane tempPane;
        paneCampiAzioneTorri=new ArrayList<>();
        for(int i=0; i<4; i++){
            tempPane=new Pane();
            paneCampiAzioneTorri.add(tempPane);
            gridCampiAzioneTorre0.add(tempPane,0,i);
            tempPane.setVisible(true);
            creaEventoCampiAzioneTorre(tempPane, 0,i);
        }
        for(int i=0; i<4; i++){
            tempPane=new Pane();
            tempPane.setPrefWidth(gridCampiAzioneTorre1.getWidth());
            tempPane.setPrefHeight(gridCampiAzioneTorre1.getHeight());
            paneCampiAzioneTorri.add(tempPane);
            gridCampiAzioneTorre1.add(tempPane,0,i);
            tempPane.setVisible(true);
            creaEventoCampiAzioneTorre(tempPane, 1,i);
        }
        for(int i=0; i<4; i++){
            tempPane=new Pane();
            tempPane.setPrefWidth(gridCampiAzioneTorre2.getWidth());
            tempPane.setPrefHeight(gridCampiAzioneTorre2.getHeight());
            paneCampiAzioneTorri.add(tempPane);
            gridCampiAzioneTorre2.add(tempPane,0,i);
            tempPane.setVisible(true);
            creaEventoCampiAzioneTorre(tempPane, 2,i);
        }
        for(int i=0; i<4; i++){
            tempPane=new Pane();
            tempPane.setPrefWidth(gridCampiAzioneTorre3.getWidth());
            tempPane.setPrefHeight(gridCampiAzioneTorre3.getHeight());
            paneCampiAzioneTorri.add(tempPane);
            gridCampiAzioneTorre3.add(tempPane,0,i);
            tempPane.setVisible(true);
            creaEventoCampiAzioneTorre(tempPane, 3,i);
        }
    }

    private void creaEventoCampiAzioneTorre(Pane tempPane, int torre, int piano) {
        tempPane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> eventoCampiAzioneTorre(torre, piano));
    }

    private void eventoCampiAzioneTorre(int torre, int piano){
        piano=specchiaPiano(piano);
        System.out.println("piano mandato: "+piano);
        try {
            clientGenerico.spostaFamiliarePiano(torre, piano);
            System.out.println("messaggio sposta familiare piano mandato");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void settaRisorse(int[] risorse) {
        labelPietra.setText(risorse[0]+"");
        labelLegna.setText(risorse[1]+"");
        labelServitori.setText(risorse[2]+"");
        labelMonete.setText(risorse[3]+"");
    }

    private void creaDadi() {
        immaginiDadoArancio=new ImageView[6];
        immaginiDadoBianco=new ImageView[6];
        immaginiDadoNero=new ImageView[6];
        ImageView da;
        Image facciaDado;
        for(int i=1; i<7; i++){
            da=new ImageView();
            facciaDado=new Image("image/img/dadi/dadoArancio/a" +i+".png");
            da.setImage(facciaDado);
            da.setFitHeight(30);
            da.setFitWidth(30);
            immaginiDadoArancio[i-1]=da;
            da=new ImageView();
            facciaDado=new Image("image/img/dadi/dadoNero/n" +i+".png");
            da.setImage(facciaDado);
            da.setFitHeight(30);
            da.setFitWidth(30);
            immaginiDadoNero[i-1]=da;
            da=new ImageView();
            facciaDado=new Image("image/img/dadi/dadoBianco/b" +i+".png");
            da.setImage(facciaDado);
            da.setFitHeight(30);
            da.setFitWidth(30);
            immaginiDadoBianco[i-1]=da;
        }

        if(mioId==0){
            paneDadoArancio.getChildren().add(immaginiDadoArancio[0]);
            paneDadoBianco.getChildren().add(immaginiDadoBianco[0]);
            paneDadoNero.getChildren().add(immaginiDadoNero[0]);
        }

        aggiungiEventoDadi();
    }

    private void aggiungiEventoDadi() {
        paneDadoNero.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                clientGenerico.tiraIDadi();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        paneDadoBianco.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                clientGenerico.tiraIDadi();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        paneDadoArancio.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                clientGenerico.tiraIDadi();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    private Color mioColore(int id) {
        switch (id){
            case 0: return Color.BLUE;
            case 1: return Color.YELLOW;
            case 2: return Color.RED;
            case 3: return Color.GREEN;
        }
        return null;
    }

    private void creaFamiliari(Color colore) {
        familiari=new ArrayList<>();
        FamiliareGrafico tempFam;
        tempFam=new FamiliareGrafico((double) 20,colore, Color.BLACK);
        FamiliareGrafico finalTempFam = tempFam;
        tempFam.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> eventoFamiliari(finalTempFam));
        gridFamiliari.add(tempFam,0,0);
        familiari.add(tempFam);
        tempFam=new FamiliareGrafico((double) 20,colore, Color.WHITE);
        FamiliareGrafico finalTempFam1 = tempFam;
        tempFam.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> eventoFamiliari(finalTempFam1));
        gridFamiliari.add(tempFam,1,0);
        familiari.add(tempFam);
        tempFam=new FamiliareGrafico((double) 20,colore, Color.ORANGE);
        FamiliareGrafico finalTempFam2 = tempFam;
        tempFam.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> eventoFamiliari(finalTempFam2));
        gridFamiliari.add(tempFam,2,0);
        familiari.add(tempFam);
        tempFam=new FamiliareGrafico((double) 20,Color.SILVER, mioColore(mioId));
        FamiliareGrafico finalTempFam3 = tempFam;
        tempFam.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> eventoFamiliari(finalTempFam3));
        gridFamiliari.add(tempFam,3,0);
        familiari.add(tempFam);
        System.out.println("familiari creati");
    }





    private void eventoFamiliari(FamiliareGrafico tempFam) {
        if (tempFam.isSelezionato()) {
            tempFam.setSelezionato(false);
            tempFam.setEffetto(null);
            try {
                clientGenerico.deselezionaFamiliare();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            for (FamiliareGrafico f : familiari) {
                f.setEffetto(null);
                f.setSelezionato(false);
            }
            tempFam.setSelezionato(true);
            tempFam.setEffetto(new Shadow());
            try {
                System.out.println("colore dado familiare "+tempFam.getNomeColoreDado());
                clientGenerico.selezionaFamiliare(tempFam.getNomeColoreDado(), mioId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    }

    private void settaLabelGiocatori(HashMap<Integer, String> giocatori) {
        String nome;
        nome=giocatori.get(0);
        labelGiocatore1.setText(nome);
        nome=giocatori.get(1);
        labelGiocatore2.setText(nome);
        nome=giocatori.get(2);
        labelGiocatore3.setText(nome);
        nome=giocatori.get(3);
        labelGiocatore4.setText(nome);
    }

    private void mettiCarteNelleTorri(ArrayList<String> nomiCarte){
        immaginiCarte =new ArrayList<>();
        Image tempImg;
        ImageView tempImageView;
        int i=0;
        int j=0;
        for(String nomeCarta: nomiCarte){

            if(i<4){
                tempImageView =new ImageView();
                tempImg = new Image("image/img/Carte/Territori/" +nomeCarta+".jpg");
                tempImageView.setImage(tempImg);
                tempImageView.setFitWidth(gridCarteTorre1.getWidth());
                tempImageView.setFitHeight(gridCarteTorre1.getHeight()/4);
                immaginiCarte.add(tempImageView);
                j=i%4;
                j=specchiaPiano(j);
                gridCarteTorre0.add(tempImageView,0,j);
            }
            else if(i<8){
                tempImageView =new ImageView();
                tempImg = new Image("image/img/Carte/Personaggi/" +nomeCarta+".jpg");
                tempImageView.setImage(tempImg);
                tempImageView.setFitWidth(gridCarteTorre1.getWidth());
                tempImageView.setFitHeight(gridCarteTorre1.getHeight()/4);
                immaginiCarte.add(tempImageView);
                j=i%4;
                j=specchiaPiano(j);
                gridCarteTorre1.add(tempImageView, 0, j);
            }else if(i<12){
                tempImageView =new ImageView();
                tempImg = new Image("image/img/Carte/Edifici/" +nomeCarta+".jpg");
                tempImageView.setImage(tempImg);
                tempImageView.setFitWidth(gridCarteTorre1.getWidth());
                tempImageView.setFitHeight(gridCarteTorre1.getHeight()/4);
                immaginiCarte.add(tempImageView);
                j=i%4;
                j=specchiaPiano(j);
                gridCarteTorre2.add(tempImageView, 0, j);
            }else if(i<16){
                tempImageView =new ImageView();
                tempImg = new Image("image/img/Carte/Imprese/" +nomeCarta+".jpg");
                tempImageView.setImage(tempImg);
                tempImageView.setFitWidth(gridCarteTorre1.getWidth());
                tempImageView.setFitHeight(gridCarteTorre1.getHeight()/4);
                immaginiCarte.add(tempImageView);
                j=i%4;
                j=specchiaPiano(j);
                gridCarteTorre3.add(tempImageView, 0, j);
            }

            i++;
            j++;
        }
        settaEventiCarta(immaginiCarte);
    }

    private void settaEventiCarta(ArrayList<ImageView> carte){

        for (ImageView iv: carte){

            iv.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> imageCarta.setImage(iv.getImage()));

            iv.addEventHandler(MouseEvent.MOUSE_EXITED, event -> imageCarta.setImage(null));
        }
    }

    public void eventoAumentaForza(ActionEvent actionEvent) {
        try {
            if(actionEvent.getSource().equals(buttonForzaNero)){
                clientGenerico.aumentaForzaFamiliare("nero", mioId);
            }
            else if(actionEvent.getSource().equals(buttonForzaBianco)){
                clientGenerico.aumentaForzaFamiliare("bianco", mioId);
            }
            else if(actionEvent.getSource().equals(buttonForzaArancio)){
                clientGenerico.aumentaForzaFamiliare("arancio", mioId);
            }
            else if(actionEvent.getSource().equals(buttonForzaNeutro)){
                clientGenerico.aumentaForzaFamiliare("neutro", mioId);
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public void familiareInMercato(MouseEvent mouseEvent){
        try {
            if (mouseEvent.getSource().equals(campoAzioneMercato0)){
                clientGenerico.spostaFamiliareMercato(0);

            }
            else if(mouseEvent.getSource().equals(campoAzioneMercato1)){
                clientGenerico.spostaFamiliareMercato(1);
            }
            else if(mouseEvent.getSource().equals(campoAzioneMercato2)){
                clientGenerico.spostaFamiliareMercato(2);
            }
            else if(mouseEvent.getSource().equals(campoAzioneMercato3)){
                clientGenerico.spostaFamiliareMercato(3);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private Color convertiStringaColore(String nomeColore){
        switch (nomeColore){
            case "nero": return Color.BLACK;
            case "bianco": return Color.WHITE;
            case "arancio": return Color.ORANGE;
            case "neutro": return mioColore(mioId);
        }
        return null;

    }

    private int specchiaPiano(int numeroPiano){
        switch (numeroPiano){
            case 0: return 3;
            case 1: return 2;
            case 2: return 1;
            case 3: return 0;
        }
        return 0;
    }

    public void eventoPalazzoDelConsiglio(){
        try {
            clientGenerico.spostaFamiliarePalazzoDelConsiglio();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void eventoZonaRaccolto(){
        try {
            if(paneCasZonaRaccolto.getChildren().size()==0){
                clientGenerico.spostaFamiliareZonaRaccolto(0);
            }
            else {
                clientGenerico.spostaFamiliareZonaRaccolto(1);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void esci(){
        try {
            clientGenerico.esci(mioId);
            System.exit(0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void eventoZonaProduzione(){
        try {
            if(paneCasZonaProd.getChildren().size()==0){
                clientGenerico.spostaFamiliareZonaProduzione(0);
            }
            else {
                clientGenerico.spostaFamiliareZonaProduzione(1);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void saltaMossa(){
        try {
            clientGenerico.saltaMossa(mioId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private Pane paneDadoNero;

    @FXML
    private Pane paneCasZonaRaccolto;

    @FXML
    private GridPane gridCampiAzioneTorre3;

    @FXML
    private GridPane gridCampiAzioneTorre2;

    @FXML
    private Pane paneCasZonaProd;

    @FXML
    private ImageView imageCarta;

    @FXML
    private GridPane gridZonaProduzione;

    @FXML
    private GridPane gridCarteTorre3;

    @FXML
    private GridPane gridCampiAzioneTorre0;

    @FXML
    private GridPane gridCampiAzioneTorre1;

    @FXML
    private GridPane gridCarteTorre0;

    @FXML
    private GridPane gridCarteTorre1;

    @FXML
    private GridPane gridCarteTorre2;

    @FXML
    private Pane paneDadoBianco;

    @FXML
    private GridPane gridPalazzoDelConsiglio;

    @FXML
    private Pane paneDadoArancio;

    @FXML
    private Label labelGiocatore1;

    @FXML
    private Label labelGiocatore2;

    @FXML
    private Label labelGiocatore3;

    @FXML
    private Label labelGiocatore4;

    @FXML
    private GridPane gridFamiliari;

    @FXML
    private Label labelMessaggiServer;

    @FXML
    private Label labelForzaNero;

    @FXML
    private Label labelForzaBianco;

    @FXML
    private Label labelForzaArancio;

    @FXML
    private Label labelForzaNeutro;

    @FXML
    private Label labelMonete;

    @FXML
    private Label labelServitori;

    @FXML
    private Label labelLegna;

    @FXML
    private Label labelPietra;

    @FXML
    private Button buttonForzaNero;

    @FXML
    private Button buttonForzaBianco;

    @FXML
    private Button buttonForzaArancio;

    @FXML
    private Button buttonForzaNeutro;

    @FXML
    private GridPane gridPlancia;

    @FXML
    private Pane campoAzioneMercato0;

    @FXML
    private Pane campoAzioneMercato1;

    @FXML
    private Pane campoAzioneMercato2;

    @FXML
    private Pane campoAzioneMercato3;

    @FXML
    private GridPane gridZonaRaccolto;

    @FXML
    private ImageView imgScomunica1;

    @FXML
    private ImageView imgScomunica2;

    @FXML
    private ImageView imgScomunica3;

    @FXML
    private GridPane gridScomunica;

    @FXML
    private VBox vBoxScomunica1;

    @FXML
    private VBox vBoxScomunica2;

    @FXML
    private VBox vBoxScomunica3;

    @FXML
    private Label labelPuntiVG0;
    @FXML
    private Label labelPuntiFG0;
    @FXML
    private Label labelPuntiMG0;
    @FXML
    private Label labelPuntiVG1;
    @FXML
    private Label labelPuntiFG1;
    @FXML
    private Label labelPuntiMG1;
    @FXML
    private Label labelPuntiVG2;
    @FXML
    private Label labelPuntiFG2;
    @FXML
    private Label labelPuntiMG2;
    @FXML
    private Label labelPuntiVG3;
    @FXML
    private Label labelPuntiFG3;
    @FXML
    private Label labelPuntiMG3;



    @Override
    public void iniziaPartita(int mioId, ArrayList<String> carte, ArrayList<String> giocatori, int[] risorse, ArrayList<String> scomuniche) throws RemoteException {

    }

    @Override
    public void spostatoFamiliarePiano(int numeroTorre, int numeroPiano, String coloreDado, int idGiocatore) throws RemoteException {
        numeroPiano=specchiaPiano(numeroPiano);
        System.out.println("numero piano" +numeroPiano);
        FamiliareGrafico tempFam;
        FamiliareGrafico tempFam2;
        if(idGiocatore==mioId) {
            tempFam2=prendiFamiliare(coloreDado);
            assert tempFam2 != null;
            tempFam=new FamiliareGrafico(10,tempFam2.getColore(),tempFam2.getColoreDado());

        }
        else{
            tempFam=creaFamiliare(coloreDado, idGiocatore);
        }
        switch (numeroTorre){
            case 0: spostaInTorre0(numeroPiano, tempFam);
                break;
            case 1: spostaInTorre1(numeroPiano, tempFam);
                break;
            case 2: spostaInTorre2(numeroPiano, tempFam);
                break;
            case 3: spostaInTorre3(numeroPiano, tempFam);
                break;
        }
        prendiCarta(numeroTorre, numeroPiano, idGiocatore);
    }

    private void prendiCarta(int numeroTorre, int numeroPiano, int id) {
        //indice della carta nella lista di carte
        int i=4*numeroTorre+specchiaPiano(numeroPiano);
        ImageView tempImg=immaginiCarte.get(i);
        immaginiCarte.remove(i);
        immaginiCarte.add(i,null);
        rimuoviCartaDallaTorre(numeroTorre, tempImg);
        if(mioId==id) {
            mettiCartaNellaPlancia(numeroTorre, tempImg);
        }

    }

    private void rimuoviCartaDallaTorre(int numeroTorre, ImageView tempImg) {
        switch (numeroTorre){
            case 0: gridCarteTorre0.getChildren().remove(tempImg);
                break;
            case 1: gridCarteTorre1.getChildren().remove(tempImg);
                break;
            case 2: gridCarteTorre2.getChildren().remove(tempImg);
                break;
            case 3: gridCarteTorre3.getChildren().remove(tempImg);
                break;
        }
    }

    private void mettiCartaNellaPlancia(int numeroTorre, ImageView tempImg) {
        tempImg.setFitHeight(gridPlancia.getHeight()/4);
        tempImg.setFitWidth(gridPlancia.getWidth()/6);
        switch (numeroTorre){
            case 0: gridPlancia.add(tempImg,contaCarteT,numeroTorre);
                contaCarteT +=1;
                break;
            case 1: gridPlancia.add(tempImg,contaCarteP,numeroTorre);
                contaCarteP+=1;
                break;
            case 2: gridPlancia.add(tempImg,contaCarteE,numeroTorre);
                contaCarteE+=1;
                break;
            case 3: gridPlancia.add(tempImg,contaCarteI,numeroTorre);
                contaCarteI+=1;
                break;
        }
    }

    private void spostaInTorre3(int numeroPiano, FamiliareGrafico familiare) {
        paneCampiAzioneTorri.get(numeroPiano+12).getChildren().removeAll();
        familiare.setLayoutX(paneCampiAzioneTorri.get(numeroPiano).getWidth()/2);
        familiare.setLayoutY(paneCampiAzioneTorri.get(numeroPiano).getHeight()/2);
        paneCampiAzioneTorri.get(numeroPiano+12).getChildren().add(familiare);
    }

    private void spostaInTorre2(int numeroPiano, FamiliareGrafico familiare) {
        paneCampiAzioneTorri.get(numeroPiano+8).getChildren().removeAll();
        familiare.setLayoutX(paneCampiAzioneTorri.get(numeroPiano).getWidth()/2);
        familiare.setLayoutY(paneCampiAzioneTorri.get(numeroPiano).getHeight()/2);
        paneCampiAzioneTorri.get(numeroPiano+8).getChildren().add(familiare);
    }

    private void spostaInTorre1(int numeroPiano, FamiliareGrafico familiare) {
        paneCampiAzioneTorri.get(numeroPiano+4).getChildren().removeAll();
        familiare.setLayoutX(paneCampiAzioneTorri.get(numeroPiano).getWidth()/2);
        familiare.setLayoutY(paneCampiAzioneTorri.get(numeroPiano).getHeight()/2);
        paneCampiAzioneTorri.get(numeroPiano+4).getChildren().add(familiare);
    }

    private void spostaInTorre0(int numeroPiano, FamiliareGrafico familiare) {
        paneCampiAzioneTorri.get(numeroPiano).getChildren().removeAll();
        familiare.setLayoutX(paneCampiAzioneTorri.get(numeroPiano).getWidth()/2);
        familiare.setLayoutY(paneCampiAzioneTorri.get(numeroPiano).getHeight()/2);
        paneCampiAzioneTorri.get(numeroPiano).getChildren().add(familiare);
    }

    private FamiliareGrafico creaFamiliare(String coloreDado, int idGiocatore) {
        Color color;
        if(coloreDado.equals("neutro")){
            return new FamiliareGrafico(10, Color.SILVER, mioColore(idGiocatore));

        }
        color= convertiStringaColore(coloreDado);
        switch (idGiocatore){
            case 0: return new FamiliareGrafico(10, Color.BLUE, color);
            case 1: return new FamiliareGrafico(10, Color.YELLOW, color);
            case 2: return new FamiliareGrafico(10,Color.RED, color);
            case 3: return new FamiliareGrafico(10,Color.GREEN, color);
        }
        return null;
    }

    private FamiliareGrafico prendiFamiliare(String coloreDado) {
        for (FamiliareGrafico f: familiari){
            f.setEffetto(null);
        }
        switch (coloreDado){
            case "nero": gridFamiliari.getChildren().remove(familiari.get(0));
                        return familiari.get(0);
            case "bianco": gridFamiliari.getChildren().remove(familiari.get(1));
                            return familiari.get(1);
            case "arancio": gridFamiliari.getChildren().remove(familiari.get(2));
                            return familiari.get(2);
            case "neutro": gridFamiliari.getChildren().remove(familiari.get(3));
                            return familiari.get(3);
        }
        return null;
    }

    @Override
    public void dadiTirati(int ar, int ne, int bi) throws RemoteException {
        paneDadoArancio.getChildren().removeAll(paneDadoArancio.getChildren());
        paneDadoBianco.getChildren().removeAll(paneDadoBianco.getChildren());
        paneDadoNero.getChildren().removeAll(paneDadoNero.getChildren());
        paneDadoArancio.getChildren().add(immaginiDadoArancio[ar-1]);
        paneDadoBianco.getChildren().add(immaginiDadoBianco[bi-1]);
        paneDadoNero.getChildren().add(immaginiDadoNero[ne-1]);
        labelForzaNero.setText(ne+"");
        labelForzaNero.setTextAlignment(TextAlignment.CENTER);
        labelForzaBianco.setText(bi+"");
        labelForzaBianco.setTextAlignment(TextAlignment.CENTER);
        labelForzaArancio.setText(ar+"");
        labelForzaArancio.setTextAlignment(TextAlignment.CENTER);
        labelForzaNeutro.setText("0");
        labelForzaNeutro.setTextAlignment(TextAlignment.CENTER);
    }

    @Override
    public void messaggio(String s) throws RemoteException {
        labelMessaggiServer.setText(s);
    }

    @Override
    public void forzaAumentata(String colore, int forza) throws RemoteException {
        switch (colore){
            case "nero": labelForzaNero.setText(forza+"");
                break;
            case "bianco": labelForzaBianco.setText(forza+"");
                break;
            case "arancio": labelForzaArancio.setText(forza+"");
                break;
            case "neutro": labelForzaNeutro.setText(forza+"");
                break;
        }
    }

    @Override
    public void risorseIncrementate(int pietra, int legna, int servitori, int monete, int puntiMilitari, int puntiFede, int puntiVittoria) throws RemoteException {
        labelPietra.setText(pietra+"");
        labelLegna.setText(legna+"");
        labelServitori.setText(servitori+"");
        labelMonete.setText(monete+"");
        ArrayList<Integer> punteggi=new ArrayList<>();
        punteggi.add(puntiVittoria);
        punteggi.add(puntiFede);
        punteggi.add(puntiMilitari);
        puntiGiocatore(mioId,punteggi);
    }

    @Override
    public void spostatoFamiliareMercato(int zonaMercato, String coloreDado, int id) throws RemoteException {
        FamiliareGrafico tempFam;
        FamiliareGrafico tempFam2;
        if(id==mioId) {
            tempFam2=prendiFamiliare(coloreDado);
            assert tempFam2 != null;
            tempFam=new FamiliareGrafico(10,tempFam2.getColore(),tempFam2.getColoreDado());

        }
        else{
            tempFam=creaFamiliare(coloreDado, id);
        }
        switch (zonaMercato){
            case 0:
                assert tempFam != null;
                tempFam.setLayoutX(campoAzioneMercato0.getWidth()/2);
                    tempFam.setLayoutY(campoAzioneMercato0.getHeight()/2);
                    campoAzioneMercato0.getChildren().add(tempFam);
                    break;
            case 1:
                assert tempFam != null;
                tempFam.setLayoutX(campoAzioneMercato1.getWidth()/2);
                    tempFam.setLayoutY(campoAzioneMercato1.getHeight()/2);
                    campoAzioneMercato1.getChildren().add(tempFam);
                    break;
            case 2:
                assert tempFam != null;
                tempFam.setLayoutX(campoAzioneMercato2.getWidth()/2);
                    tempFam.setLayoutY(campoAzioneMercato2.getHeight()/2);
                    campoAzioneMercato2.getChildren().add(tempFam);
                    break;
            case 3:
                assert tempFam != null;
                tempFam.setLayoutX(campoAzioneMercato3.getWidth()/2);
                    tempFam.setLayoutY(campoAzioneMercato3.getHeight()/2);
                    campoAzioneMercato3.getChildren().add(tempFam);
                    break;
        }
    }

    @Override
    public void spostatoFamiliarePalazzoDelConsiglio(String coloreDado, int id) throws RemoteException {
        FamiliareGrafico tempFam;
        FamiliareGrafico tempFam2;
        if(id==mioId) {
            tempFam2=prendiFamiliare(coloreDado);
            assert tempFam2 != null;
            tempFam=new FamiliareGrafico(10,tempFam2.getColore(),tempFam2.getColoreDado());

        }
        else{
            tempFam=creaFamiliare(coloreDado, id);
        }

        gridPalazzoDelConsiglio.add(tempFam,contaFamiliariPalazzoConsigglio,0);
        contaFamiliariPalazzoConsigglio +=1;
    }

    @Override
    public void spostatoFamiliareZonaProduzione(String coloreDado, int id, int zona) throws RemoteException {
        FamiliareGrafico tempFam;
        FamiliareGrafico tempFam2;
        if(id==mioId) {
            tempFam2=prendiFamiliare(coloreDado);
            assert tempFam2 != null;
            tempFam=new FamiliareGrafico(10,tempFam2.getColore(),tempFam2.getColoreDado());

        }
        else{
            tempFam=creaFamiliare(coloreDado, id);
        }

        if(zona==0){
            assert tempFam != null;
            tempFam.setLayoutX(paneCasZonaProd.getWidth()/2);
            tempFam.setLayoutY(paneCasZonaProd.getHeight()/2);
            paneCasZonaProd.getChildren().add(tempFam);
        }
        else {
            gridZonaProduzione.add(tempFam,contaFamZonaProd,0);
            contaFamZonaProd ++;
        }


    }

    @Override
    public void spostatoFamiliareZonaRaccolto(String coloreDado, int id, int zona) throws RemoteException {
        FamiliareGrafico tempFam;
        FamiliareGrafico tempFam2;
        if(id==mioId) {
            tempFam2=prendiFamiliare(coloreDado);
            assert tempFam2 != null;
            tempFam=new FamiliareGrafico(10,tempFam2.getColore(),tempFam2.getColoreDado());

        }
        else{
            tempFam=creaFamiliare(coloreDado, id);
        }

        if(zona==0){
            assert tempFam != null;
            tempFam.setLayoutX(paneCasZonaRaccolto.getWidth()/2);
            tempFam.setLayoutY(paneCasZonaRaccolto.getHeight()/2);
            paneCasZonaRaccolto.getChildren().add(tempFam);
        }
        else {
            gridZonaRaccolto.add(tempFam,contaFamZonaRac,0);
            contaFamZonaRac ++;
        }
    }

    @Override
    public void mossaSaltata(int id) {

    }

    @Override
    public void avvisoInizioTurno(ArrayList<String> nomiCarte) {

    }

    @Override
    public void scegliPergamena() throws RemoteException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Lorenzo Il Magnifico");
        alert.setHeaderText("Privilegio del Consiglio");
        alert.setContentText("Fai una Scelta");

        ButtonType buttonTypeOne = new ButtonType("1 Legna\n1Pietra");
        ButtonType buttonTypeTwo = new ButtonType("2 Servitori");
        ButtonType buttonTypeThree = new ButtonType("2 Monete");
        ButtonType buttonTypeFour = new ButtonType("2 Punti Militari");
        ButtonType buttonTypeFive = new ButtonType("1 Punto Fede");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeFour, buttonTypeFive);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            clientGenerico.sceltaPergamena(0);
        } else if (result.get() == buttonTypeTwo) {
            clientGenerico.sceltaPergamena(1);
        } else if (result.get() == buttonTypeThree) {
            clientGenerico.sceltaPergamena(2);
        } else if (result.get() == buttonTypeFour) {
            clientGenerico.sceltaPergamena(3);
        }else if (result.get() == buttonTypeFive) {
            clientGenerico.sceltaPergamena(4);
        }
    }

    @Override
    public void scegliScomunica() throws RemoteException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Lorenzo Il Magnifico");
        alert.setHeaderText("Appoggio alla Chiesa");
        alert.setContentText("Fai una Scelta");

        ButtonType buttonTypeOne = new ButtonType("Appoggio la Chiesa");
        ButtonType buttonTypeTwo = new ButtonType("Non Appoggio la Chiesa");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            clientGenerico.sceltaScomunica(true);
        } else if (result.get() == buttonTypeTwo) {
            clientGenerico.sceltaScomunica(false);
        }

    }

    @Override
    public void giocatoreScomunicato(int id, int periodo) throws RemoteException {
        Color color;
        color=mioColore(id);
        Rectangle rectangle=new Rectangle(10,10,color);

        if(periodo==1){
            vBoxScomunica1.getChildren().add(rectangle);
        }
        else if(periodo==2){
            vBoxScomunica2.getChildren().add(rectangle);
        }
        else if(periodo==3){
            vBoxScomunica3.getChildren().add(rectangle);
        }
        else {
            System.out.println("Giocatore scomunicato: Periodo non valido");
        }


    }

    @Override
    public void finePartita(ArrayList<Integer> classificaId,ArrayList<String> username, ArrayList<Integer> punti) throws RemoteException {
        if(classificaId!=null){
            Stage primaryStage=new Stage();
            System.out.println("fine partita");
            if(mioId==classificaId.get(0)){
                //carico il messaggio hai vinto
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Client/GUI/You Win.fxml"));
                Parent root;
                try {
                    root = fxmlLoader.load();
                    primaryStage.setTitle("Lorenzo il magnifico");
                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Client/GUI/You Lose.fxml"));
                try {
                    Parent root = fxmlLoader.load();
                    primaryStage.setTitle("Lorenzo il magnifico");
                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            labelMessaggiServer.setText("Un giocatore ha abbandonato");
        }
    }

    @Override
    public void puntiGiocatore(int id, ArrayList<Integer> punteggi) throws RemoteException {
        switch (id){
            case 0: labelPuntiVG0.setText(String.valueOf(punteggi.get(0)));
                    labelPuntiFG0.setText(String.valueOf(punteggi.get(1)));
                    labelPuntiMG0.setText(String.valueOf(punteggi.get(2)));
                    break;
            case 1: labelPuntiVG1.setText(String.valueOf(punteggi.get(0)));
                    labelPuntiFG1.setText(String.valueOf(punteggi.get(1)));
                    labelPuntiMG1.setText(String.valueOf(punteggi.get(2)));
                    break;
            case 2: labelPuntiVG2.setText(String.valueOf(punteggi.get(0)));
                    labelPuntiFG2.setText(String.valueOf(punteggi.get(1)));
                    labelPuntiMG2.setText(String.valueOf(punteggi.get(2)));
                    break;
            case 3: labelPuntiVG3.setText(String.valueOf(punteggi.get(0)));
                    labelPuntiFG3.setText(String.valueOf(punteggi.get(1)));
                    labelPuntiMG3.setText(String.valueOf(punteggi.get(2)));
                    break;
        }
    }


    public void nuovoTurno(ArrayList<String> nomiCarte) {
        pulisciTabellone();
        mettiCarteNelleTorri(nomiCarte);
        creaFamiliari(mioColore(mioId));
    }

    private void pulisciTabellone() {
        pulisciFamiliariTorri();
        pulisciCarteTorri();
        pulisciMercato();
        pulisciPalazzoConsiglio();
        pulisciZonaRaccolto();
        pulisciZonaProduzione();
        pulisciZonaFamiliri();
    }

    private void pulisciCarteTorri() {
        gridCarteTorre0.getChildren().clear();
        gridCarteTorre1.getChildren().clear();
        gridCarteTorre2.getChildren().clear();
        gridCarteTorre3.getChildren().clear();
    }

    private void pulisciZonaFamiliri() {
        familiari.clear();
        gridFamiliari.getChildren().clear();
    }

    private void pulisciZonaProduzione() {
        paneCasZonaProd.getChildren().clear();
        gridZonaProduzione.getChildren().clear();
        contaFamZonaProd=0;
    }

    private void pulisciZonaRaccolto() {
        paneCasZonaRaccolto.getChildren().clear();
        gridZonaRaccolto.getChildren().clear();
        contaFamZonaRac=0;
    }

    private void pulisciPalazzoConsiglio() {
        gridPalazzoDelConsiglio.getChildren().clear();
        contaFamiliariPalazzoConsigglio=0;
    }

    private void pulisciMercato() {
        campoAzioneMercato0.getChildren().clear();
        campoAzioneMercato1.getChildren().clear();
        campoAzioneMercato2.getChildren().clear();
        campoAzioneMercato3.getChildren().clear();
    }

    private void pulisciFamiliariTorri() {
        gridCampiAzioneTorre0.getChildren().clear();
        gridCampiAzioneTorre1.getChildren().clear();
        gridCampiAzioneTorre2.getChildren().clear();
        gridCampiAzioneTorre3.getChildren().clear();
        creaPaneCampiAzioneTorri();
    }
}