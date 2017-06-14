package Client;

import Client.GUI.ControllerGioco;
import Client.GUI.ControllerLogin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.NotBoundException;

/**
 * Created by Pietro on 16/05/2017.
 */
public class MainClient extends Application {
    public static Stage  stage;
    public static void main(String[] args) throws IOException, NotBoundException {

        launch(args);

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        stage=primaryStage;
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GUI/SchermataDiGioco.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GUI/menu.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Lorenzo il magnifico");
        //ControllerGioco controller= (ControllerGioco)fxmlLoader.getController();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

       //controller.inizializza(null,null,0);

    }


}
