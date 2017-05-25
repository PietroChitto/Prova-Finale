package Client.GUI;

import Client.ClientGenerico;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;

public class ControllerGioco {
    private ClientGenerico clientGenerico;
    private ArrayList<ImageView> immagini;
    private HashMap<Integer,String> giocatori;
    private int mioId;


    public void inizializza(HashMap<Integer, String> giocatori, ArrayList<String> carte, int mioId){
        this.giocatori=giocatori;
        this.mioId=mioId;
        mettiCarteNelleTorri(carte);
    }

    public void mettiCarteNelleTorri(ArrayList<String> nomiCarte){
        immagini=new ArrayList<>();
        Image tempImg;
        ImageView tempImageView;
        int i=0;
        for(String nomeCarta: nomiCarte){

            tempImageView =new ImageView();
            tempImg = new Image("Client/GUI/img/Carte/Territori/"+nomeCarta+".jpg");
            tempImageView.setImage(tempImg);
            immagini.add(tempImageView);

            if(i<4){
                gridCarteTorre0.add(tempImageView,0,i%4);
            }
            else if(i<8){
                gridCarteTorre1.add(tempImageView, 0, i%4);
            }else if(i<12){
                gridCarteTorre2.add(tempImageView, 0, i%4);
            }else if(i<16){
                gridCarteTorre3.add(tempImageView, 0, i%4);
            }


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





}