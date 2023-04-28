package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Created by Pietro on 16/05/2017.
 */
public class MainClient extends Application {
    public static Stage  stage;
    public static void main(String[] args) {

        launch(args);

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        URL resource = MainClient.class.getResource("/fxml/menu.fxml");
        stage=primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Lorenzo il magnifico");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
