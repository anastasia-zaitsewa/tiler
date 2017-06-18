package tiler.ui.gallery

import tiler.model.Tile

/**
 * Shows gallery with [Tile]s
 */
interface TileGalleryView {
    fun updateState(state: TileGalleryState)

    data class TileGalleryState(val tiles: List<Tile>)
}
