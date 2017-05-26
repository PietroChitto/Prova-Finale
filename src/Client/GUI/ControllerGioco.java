package Client.GUI;

import Client.ClientGenerico;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ControllerGioco{
    private ClientGenerico clientGenerico;
    private transient ArrayList<ImageView> immagini;
    private transient HashMap<Integer,String> giocatori;
    private int mioId;


    public void inizializza(HashMap<Integer, String> giocatori, ArrayList<String> carte, int mioId){
        this.giocatori=giocatori;
        this.mioId=mioId;
        settaLabelGiocatori(this.giocatori);
        mettiCarteNelleTorri(carte);
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
        immagini=new ArrayList<>();
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
                immagini.add(tempImageView);
                j=i%4;
                gridCarteTorre0.add(tempImageView,0,j);
            }
            else if(i<8){
                tempImageView =new ImageView();
                tempImg = new Image("Client/GUI/img/Carte/Personaggi/"+nomeCarta+".jpg");
                tempImageView.setImage(tempImg);
                tempImageView.setFitWidth(gridCarteTorre1.getWidth());
                tempImageView.setFitHeight(gridCarteTorre1.getHeight()/4);
                immagini.add(tempImageView);
                j=i%4;
                gridCarteTorre1.add(tempImageView, 0, i%4);
            }else if(i<12){
                tempImageView =new ImageView();
                tempImg = new Image("Client/GUI/img/Carte/Edifici/"+nomeCarta+".jpg");
                tempImageView.setImage(tempImg);
                tempImageView.setFitWidth(gridCarteTorre1.getWidth());
                tempImageView.setFitHeight(gridCarteTorre1.getHeight()/4);
                immagini.add(tempImageView);
                j=i%4;
                gridCarteTorre2.add(tempImageView, 0, i%4);
            }else if(i<16){
                tempImageView =new ImageView();
                tempImg = new Image("Client/GUI/img/Carte/Imprese/"+nomeCarta+".jpg");
                tempImageView.setImage(tempImg);
                tempImageView.setFitWidth(gridCarteTorre1.getWidth());
                tempImageView.setFitHeight(gridCarteTorre1.getHeight()/4);
                immagini.add(tempImageView);
                j=i%4;
                gridCarteTorre3.add(tempImageView, 0, i%4);
            }

            i++;
            j++;
        }
        settaEventiCarta(immagini);
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

    public void prova(){
        labelGiocatore1.setText("met pova va");
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





}