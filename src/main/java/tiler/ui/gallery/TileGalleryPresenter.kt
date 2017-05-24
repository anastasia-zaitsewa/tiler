package tiler.ui.gallery

import io.reactivex.schedulers.Schedulers
import tiler.interactor.getters.GetTilesFromFolderUC
import tiler.ui.gallery.TileGalleryView.TileGalleryState

/**
 * Presents grid of {@link Tile}s
 */
class TileGalleryPresenter(val getTilesUC: GetTilesFromFolderUC) {

    private val HARDCODE_PATH_TO_TEST_TILES = System.getProperty("user.dir") + "/Tiles"

    fun start(view: TileGalleryView) {
        getTilesUC.getAll(HARDCODE_PATH_TO_TEST_TILES)
                .map { TileGalleryState(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.trampoline())
                .subscribe { view.updateState(it) }
    }
}