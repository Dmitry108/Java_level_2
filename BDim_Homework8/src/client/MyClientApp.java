package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MyClientApp extends Application {
    @Override
    public void start (Stage stg) throws Exception {
        stg.setScene(new Scene(FXMLLoader.load(getClass().getResource("myClient.fxml"))));
        stg.setTitle("Чат");
        stg.show();
    }
    public static void main (String[] args){
        launch();
    }
}