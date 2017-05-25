package Client.GUI;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class ControllerGioco {
    private ImageView imgprova;
    private ImageView imgprova1;
    private ImageView imgprova2;
    private ImageView imgprova3;


    public void inizializza(){
        imgprova=new ImageView();
        imgprova.setFitWidth(gridCarteTorre1.getWidth());
        imgprova.setFitHeight(gridCarteTorre1.getHeight()/4);
        Image borgo = new Image("Client/GUI/img/Carte/Territori/Borgo.jpg");
        imgprova.setImage(borgo);
        gridCarteTorre1.add(imgprova, 0,0);

        imgprova1=new ImageView();
        imgprova1.setFitWidth(gridCarteTorre1.getWidth());
        imgprova1.setFitHeight(gridCarteTorre1.getHeight()/4);
        Image borgo1 = new Image("Client/GUI/img/Carte/Territori/Borgo.jpg");
        imgprova1.setImage(borgo1);
        gridCarteTorre1.add(imgprova1, 0,1);

        imgprova2=new ImageView();
        imgprova2.setFitWidth(gridCarteTorre1.getWidth());
        imgprova2.setFitHeight(gridCarteTorre1.getHeight()/4);
        Image borgo2 = new Image("Client/GUI/img/Carte/Territori/Borgo.jpg");
        imgprova2.setImage(borgo2);
        this.gridCarteTorre1.add(imgprova2, 0,2);

        imgprova3=new ImageView();
        imgprova3.setFitWidth(gridCarteTorre1.getWidth());
        imgprova3.setFitHeight(gridCarteTorre1.getHeight()/4);
        Image borgo3 = new Image("Client/GUI/img/Carte/Territori/Citta.jpg");
        imgprova3.setImage(borgo3);
        this.gridCarteTorre1.add(imgprova3,0,3);
    }

    @FXML
    private GridPane gridCarteTorre3;

    @FXML
    private GridPane gridCarteTorre2;

    @FXML
    private GridPane gridCarteTorre1;

    @FXML
    private GridPane gridCarteTorre0;

}