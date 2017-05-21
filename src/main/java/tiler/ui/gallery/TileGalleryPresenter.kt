package tiler.ui.gallery

import io.reactivex.schedulers.Schedulers
import tiler.interactor.getters.GetTilesFromFolderUC
import tiler.ui.gallery.TileGalleryView.TileGalleryState

/**
 * Presents grid of {@link Tile}s
 */
class TileGalleryPresenter(val getTilesUC: GetTilesFromFolderUC) {

    fun start(view: TileGalleryView) {
        getTilesUC.getAll(System.getProperty("user.dir") + "/Tiles")
                .map { TileGalleryState(it) }
                .observeOn(Schedulers.trampoline())
                .subscribeOn(Schedulers.io())
                .subscribe { view.updateState(it) }
    }
}