package tiler;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tiler.interactor.getters.GetTilesFromFolderUC;
import tiler.repository.JavaFileRepository;
import tiler.ui.canvas.CanvasGrid;
import tiler.ui.gallery.TileGalleryPresenter;
import tiler.ui.gallery.TileGalleryViewImpl;

public class Tiler extends Application {

    TileGalleryViewImpl tileGalleryView = new TileGalleryViewImpl();

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        CanvasGrid canvasGrid = new CanvasGrid();
        root.setCenter(canvasGrid);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.prefViewportWidthProperty().bind(tileGalleryView.widthProperty());
        scrollPane.setContent(tileGalleryView);

        root.setLeft(scrollPane);

        new TileGalleryPresenter(new GetTilesFromFolderUC(new JavaFileRepository()))
                .start(tileGalleryView);

        Scene scene = new Scene(root, 800.0, 500.0);

        primaryStage.setTitle("TileGallery");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
