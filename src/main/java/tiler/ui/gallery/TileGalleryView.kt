package tiler.ui.gallery

import tiler.model.Tile

interface TileGalleryView {
    fun updateState(state: TileGalleryState)

    data class TileGalleryState(val tiles: List<Tile>)
}
