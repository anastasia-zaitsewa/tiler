package tiler.ui.gallery

import io.reactivex.Scheduler
import tiler.interactor.getters.GetTilesFromFolderUC
import tiler.ui.gallery.TileGalleryView.TileGalleryState

/**
 * Presents gallery of tiles
 */
class TileGalleryPresenter(val getTilesUC: GetTilesFromFolderUC,
                           val mainScheduler: Scheduler) {

    private val HARDCODE_PATH_TO_TEST_TILES = System.getProperty("user.dir") + "/Tiles"

    fun start(view: TileGalleryView) {
        getTilesUC.getAll(HARDCODE_PATH_TO_TEST_TILES)
                .map { TileGalleryState(it) }
                .observeOn(mainScheduler)
                .subscribe { view.updateState(it) }
    }
}