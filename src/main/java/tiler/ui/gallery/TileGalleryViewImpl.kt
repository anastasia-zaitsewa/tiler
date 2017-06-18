package tiler.ui.gallery

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.ContentDisplay
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.TilePane
import javafx.scene.paint.Color
import tiler.model.Tile

/**
 * Swing implementation - shows tile gallery.
 */
class TileGalleryViewImpl : TileGalleryView, TilePane() {

    private val SPACING = 5
    private val TILE_SIZE = 120
    private val COLUMNS = 2

    var tiles: List<Tile> = emptyList()


    init {
        prefColumns = COLUMNS
        prefTileWidth = TILE_SIZE.toDouble()
        hgap = SPACING.toDouble()
        vgap = SPACING.toDouble()
        tileAlignment = Pos.CENTER
        background = Background(BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY))
    }

    override fun updateState(state: TileGalleryView.TileGalleryState) {
        tiles = state.tiles
        drawTiles()
    }

    private fun drawTiles() {
        for ((sourceImageUrl, name) in tiles) {
            val element = Label(
                    name,
                    ImageView(
                            Image(
                                    sourceImageUrl.get(),
                                    Math.ceil(TILE_SIZE * 0.8),
                                    Math.ceil(TILE_SIZE * 0.8),
                                    true,
                                    true,
                                    true
                            )
                    )
            )

            element.isWrapText = true
            element.contentDisplay = ContentDisplay.TOP

            children.add(element)
        }
    }
}
