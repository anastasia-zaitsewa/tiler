package tiler;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Tiler extends Application {
    @Override
    public void start(Stage primaryStage) {
        Button button = new Button();
        button.setText("Test");
        button.setOnAction(event -> System.out.println("test"));


        StackPane root = new StackPane();

        root.getChildren().add(button);

        Scene scene = new Scene(root, 300.0, 250.0);

        primaryStage.setTitle("test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
