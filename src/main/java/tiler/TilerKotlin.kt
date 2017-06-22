package tiler

import io.reactivex.rxjavafx.schedulers.JavaFxScheduler
import io.reactivex.schedulers.Schedulers
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.ScrollPane
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import tiler.interactor.getters.GetCellsUC
import tiler.interactor.getters.GetTilesFromFolderUC
import tiler.repository.FixGridCellRepository
import tiler.repository.JavaFileRepository
import tiler.ui.canvas.CanvasGridImpl
import tiler.ui.canvas.CanvasGridPresenter
import tiler.ui.gallery.TileGalleryPresenter
import tiler.ui.gallery.TileGalleryViewImpl


/**
 * Highest class representing main application screen,
 * written on Kotlin.
 * [Tiler] exists to pledge needs for statics during [Application] use
 */
class TilerKotlin {

    val tileGalleryView = TileGalleryViewImpl()
    val canvasGrid = CanvasGridImpl()

    fun start(primaryStage: Stage) {

        val root = initRoot()

        TileGalleryPresenter(GetTilesFromFolderUC(
                JavaFileRepository()),
                JavaFxScheduler.platform(),
                Schedulers.io()
        ).start(tileGalleryView)

        CanvasGridPresenter(GetCellsUC(
                FixGridCellRepository()),
                JavaFxScheduler.platform(),
                Schedulers.io()
        ).start(canvasGrid)

        primaryStage.apply {
            title = "Tiler"
            scene = Scene(root, 800.0, 500.0)
            show()
        }
    }

    private fun initRoot(): BorderPane {
        val scrollPaneGallery = ScrollPane().apply {
            isFitToHeight = true
            prefViewportWidthProperty().bind(tileGalleryView.widthProperty())
            content = tileGalleryView
        }

        val root = BorderPane().apply {
            left = scrollPaneGallery
            center = canvasGrid
        }
        return root
    }
}