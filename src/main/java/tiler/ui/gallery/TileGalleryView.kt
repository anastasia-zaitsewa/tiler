package tiler.ui.gallery

import tiler.model.Tile

/**
 * Shows grid with {@link Tile}s
 */
interface TileGalleryView {
    fun updateState(state: TileGalleryState)

    data class TileGalleryState(val tiles: List<Tile>)
}
