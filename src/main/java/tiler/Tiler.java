package tiler;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tiler.interactor.getters.GetTilesFromFolderUC;
import tiler.repository.JavaFileRepository;
import tiler.ui.gallery.TileGalleryPresenter;
import tiler.ui.gallery.TileGalleryViewImpl;

public class Tiler extends Application {

    TileGalleryViewImpl tileGalleryView = new TileGalleryViewImpl();

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setContent(tileGalleryView);

        root.getChildren().add(scrollPane);

        new TileGalleryPresenter(new GetTilesFromFolderUC(new JavaFileRepository()))
                .start(tileGalleryView);

        Scene scene = new Scene(root, 800.0, 400.0);

        primaryStage.setTitle("TileGallery");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
