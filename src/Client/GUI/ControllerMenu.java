package Client.GUI;

import Client.ClientRMI;
import Client.ClientSocket;
import Client.MainClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.WindowEvent;
import server.rmiServer.InterfaciaServer;

import java.io.IOException;
import java.rmi.NotBoundException;

public class ControllerMenu {

    private InterfaciaServer client;

    public void initialize(){
        ToggleGroup toggleGroup=new ToggleGroup();
        toggleRMI.setToggleGroup(toggleGroup);
        toggleSocket.setToggleGroup(toggleGroup);
    }

    @FXML
    private ToggleButton toggleRMI;

    @FXML
    private ToggleButton toggleSocket;

    @FXML
    private TextField txtUsername;

    @FXML
    private Button btnDueGiocatori;

    @FXML
    private Button btnTreGiocatori;

    @FXML
    private Button btnQuattroGiocatori;

    @FXML
    void exit(ActionEvent event) {
        MainClient.stage.close();


    }

    @FXML
    void login(ActionEvent event) throws IOException, NotBoundException {

        int numeroGiocatori;

        numeroGiocatori=getNumeroGiocatori();

        if (txtUsername.getText().equals("")){

        }else{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Client/GUI/SchermataDiGioco.fxml"));
            Parent root = fxmlLoader.load();
            Platform.runLater(()->{
                MainClient.stage.setTitle("Lorenzo il magnifico");
                MainClient.stage.setScene(new Scene(root));
                MainClient.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        System.exit(0);
                    }
                });
            });
            ControllerGioco controllerGioco= fxmlLoader.getController();


            if (toggleSocket.isSelected()){

                client = new ClientSocket(txtUsername.getText(), controllerGioco, numeroGiocatori);


            }else{

                client=new ClientRMI(txtUsername.getText(), controllerGioco, numeroGiocatori);

            }

        }

    }

    private int getNumeroGiocatori() {
        if(btnDueGiocatori.isFocused())
            return 2;
        if(btnTreGiocatori.isFocused())
            return 3;
        if (btnQuattroGiocatori.isFocused())
            return 4;
        else
            System.out.println("C'è qualquadra che non va nel menu");
        return 0;
    }
}
