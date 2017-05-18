package tiler.ui.gallery

import tiler.interactor.getters.GetAllTilesFromFolderUC
import tiler.ui.gallery.TileGalleryView.*

class TileGalleryPresenter(val getAllTilesUC: GetAllTilesFromFolderUC) {

    fun start(view: TileGalleryView){
        getAllTilesUC.get(System.getProperty("user.dir") + "/Tiles")
                .map { TileGalleryState(it) }
                .subscribe{view.updateState(it)}
    }
}