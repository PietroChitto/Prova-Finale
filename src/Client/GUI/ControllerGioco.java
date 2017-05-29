package Client.GUI;

import Client.InterfacciaClient;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import partita.eccezioniPartita.DadiNonTiratiException;
import partita.eccezioniPartita.TurnoException;
import server.rmiServer.InterfaciaRemotaRMI;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class ControllerGioco implements InterfacciaClient{
    private InterfaciaRemotaRMI clientGenerico;
    private ArrayList<ImageView> immaginiCarte;
    private ImageView[] immaginiDadoNero;
    private ImageView[] immaginiDadoBianco;
    private ImageView[] immaginiDadoArancio;
    private HashMap<Integer,String> giocatori;
    private int mioId;
    private ArrayList<FamiliareGrafico> familiari;
    private boolean mioTurno;

    public void setClientGenerico(InterfaciaRemotaRMI clientGenerico){
        this.clientGenerico=clientGenerico;
    }



    public void inizializza(HashMap<Integer, String> giocatori, ArrayList<String> carte, int mioId, int[] risorse){
        if(mioId==0)
            mioTurno=true;
        else
            mioTurno=false;
        this.giocatori=giocatori;
        this.mioId=mioId;
        settaLabelGiocatori(this.giocatori);
        mettiCarteNelleTorri(carte);
        creaFamiliari(mioColore());
        creaDadi();
        settaRisorse(risorse);
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
            facciaDado=new Image("Client/GUI/img/dadi/dadoArancio/a"+i+".png");
            da.setImage(facciaDado);
            da.setFitHeight(30);
            da.setFitWidth(30);
            immaginiDadoArancio[i-1]=da;
            da=new ImageView();
            facciaDado=new Image("Client/GUI/img/dadi/dadoNero/n"+i+".png");
            da.setImage(facciaDado);
            da.setFitHeight(30);
            da.setFitWidth(30);
            immaginiDadoNero[i-1]=da;
            da=new ImageView();
            facciaDado=new Image("Client/GUI/img/dadi/dadoBianco/b"+i+".png");
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        paneDadoBianco.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                clientGenerico.tiraIDadi();
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        paneDadoArancio.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                clientGenerico.tiraIDadi();
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private Color mioColore() {
        switch (mioId){
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
        tempFam=new FamiliareGrafico((double) 20,Color.SILVER, Color.BLACK);
        FamiliareGrafico finalTempFam3 = tempFam;
        tempFam.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> eventoFamiliari(finalTempFam3));
        gridFamiliari.add(tempFam,3,0);
        familiari.add(tempFam);

    }

    private void eventoFamiliari(FamiliareGrafico tempFam) {
        if(mioTurno) {
            if (tempFam.isSelezionato()) {
                tempFam.setSelezionato(false);
                tempFam.setEffetto(null);
                try {
                    clientGenerico.deselezionaFamiliare();
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (TurnoException e) {
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
                    clientGenerico.selezionaFamiliare(tempFam.getNomeColoreDado(), mioId);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (TurnoException e) {
                    e.printStackTrace();
                } catch (DadiNonTiratiException e) {
                    e.printStackTrace();
                }

            }
        }else {
            labelMessaggiServer.setText("non è il tuo turno");

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

    public void mettiCarteNelleTorri(ArrayList<String> nomiCarte){
        immaginiCarte =new ArrayList<>();
        Image tempImg;
        ImageView tempImageView;
        int i=0;
        int j=0;
        for(String nomeCarta: nomiCarte){

            if(i<4){
                tempImageView =new ImageView();
                tempImg = new Image("Client/GUI/img/Carte/Territori/"+nomeCarta+".jpg");
                tempImageView.setImage(tempImg);
                tempImageView.setFitWidth(gridCarteTorre1.getWidth());
                tempImageView.setFitHeight(gridCarteTorre1.getHeight()/4);
                immaginiCarte.add(tempImageView);
                j=i%4;
                gridCarteTorre0.add(tempImageView,0,j);
            }
            else if(i<8){
                tempImageView =new ImageView();
                tempImg = new Image("Client/GUI/img/Carte/Personaggi/"+nomeCarta+".jpg");
                tempImageView.setImage(tempImg);
                tempImageView.setFitWidth(gridCarteTorre1.getWidth());
                tempImageView.setFitHeight(gridCarteTorre1.getHeight()/4);
                immaginiCarte.add(tempImageView);
                j=i%4;
                gridCarteTorre1.add(tempImageView, 0, i%4);
            }else if(i<12){
                tempImageView =new ImageView();
                tempImg = new Image("Client/GUI/img/Carte/Edifici/"+nomeCarta+".jpg");
                tempImageView.setImage(tempImg);
                tempImageView.setFitWidth(gridCarteTorre1.getWidth());
                tempImageView.setFitHeight(gridCarteTorre1.getHeight()/4);
                immaginiCarte.add(tempImageView);
                j=i%4;
                gridCarteTorre2.add(tempImageView, 0, i%4);
            }else if(i<16){
                tempImageView =new ImageView();
                tempImg = new Image("Client/GUI/img/Carte/Imprese/"+nomeCarta+".jpg");
                tempImageView.setImage(tempImg);
                tempImageView.setFitWidth(gridCarteTorre1.getWidth());
                tempImageView.setFitHeight(gridCarteTorre1.getHeight()/4);
                immaginiCarte.add(tempImageView);
                j=i%4;
                gridCarteTorre3.add(tempImageView, 0, i%4);
            }

            i++;
            j++;
        }
        settaEventiCarta(immaginiCarte);
    }

    public void settaEventiCarta(ArrayList<ImageView> carte){

        for (ImageView iv: carte){

            iv.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    imageCarta.setImage(iv.getImage());
                }
            });

            iv.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    imageCarta.setImage(null);
                }
            });
        }
    }


    @FXML
    private Pane paneDadoNero;

    @FXML
    private Pane paneCasZonaRaccolto;

    @FXML
    private GridPane gridCampiAzioneTorre4;

    @FXML
    private GridPane gridCampiAzioneTorre2;

    @FXML
    private Pane paneCasZonaProd;

    @FXML
    private ImageView imageCarta;

    @FXML
    private Pane paneCamZonaProduzione;

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
    private Pane campoAzionePalazzoDelConsiglio;

    @FXML
    private Pane paneCarta;

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


    @Override
    public void iniziaPartita(int mioId, ArrayList<String> carte, ArrayList<String> giocatori, int[] risorse) throws RemoteException {

    }

    @Override
    public void spostatoFamiliarePiano(int numeroTorre, int numeroPiano, String coloreDado, int idGiocatore) throws RemoteException {

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
        labelForzaBianco.setText(bi+"");
        labelForzaArancio.setText(ar+"");
        labelForzaNeutro.setText("0");
    }

    @Override
    public void messaggio(String s) throws RemoteException {
        labelMessaggiServer.setText(s);
    }
}