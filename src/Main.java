/**
 * Created by Александр on 14.09.2017.
 */


import client.controler.Controler;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new Controler();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
