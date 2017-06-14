package Client.GUI;

import Client.MainClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import server.rmiServer.InterfaciaRemotaRMI;

import java.io.IOException;
import java.rmi.NotBoundException;


/**
 * Created by kira9 on 24/05/2017.
 */
public class ControllerLogin {

    InterfaciaRemotaRMI client;


    @FXML
    private Button txtStartRMI;

    @FXML
    private TextField txtUsername;

    @FXML
    private Button txtExit;

    @FXML
    private Button txtStartSocket;

    @FXML
    void exit(ActionEvent event) {
        MainClient.stage.close();


    }

    @FXML
    void login(ActionEvent event) throws IOException, NotBoundException {

        if (txtUsername.getText().equals("")){

        }else{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Client/GUI/SchermataDiGioco.fxml"));
            Parent root = fxmlLoader.load();
            //Parent root = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("Client/GUI/SchermataDiGioco.fxml"));
            Platform.runLater(()->{
                MainClient.stage.setTitle("Lorenzo il magnifico");
                MainClient.stage.setScene(new Scene(root));
            });
            ControllerGioco controllerGioco= fxmlLoader.getController();


            if (txtStartSocket.isFocused()){

                //client = new ClientSocket(txtUsername.getText(), controllerGioco);


            }else{

                //client=new ClientRMI(txtUsername.getText(), controllerGioco);

            }

        }

    }


}
