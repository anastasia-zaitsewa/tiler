package tiler;

import javafx.application.Application;
import javafx.stage.Stage;

public class Tiler extends Application {


    @Override
    public void start(Stage primaryStage) {
        new TilerKotlin().start(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
