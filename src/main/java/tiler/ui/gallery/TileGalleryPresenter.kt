package tiler.ui.gallery

import io.reactivex.schedulers.Schedulers
import tiler.interactor.getters.GetAllTilesFromFolderUC
import tiler.ui.gallery.TileGalleryView.TileGalleryState

class TileGalleryPresenter(val getAllTilesUC: GetAllTilesFromFolderUC) {

    fun start(view: TileGalleryView) {
        getAllTilesUC.get(System.getProperty("user.dir") + "/Tiles")
                .map { TileGalleryState(it) }
                .observeOn(Schedulers.trampoline())
                .subscribeOn(Schedulers.io())
                .subscribe { view.updateState(it) }
    }
}