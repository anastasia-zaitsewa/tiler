package tiler.ui.gallery

import tiler.model.Tile
import java.awt.GridLayout
import java.awt.Image.SCALE_SMOOTH
import javax.swing.BoundedRangeModel
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JLabel.BOTTOM
import javax.swing.JLabel.CENTER
import javax.swing.JPanel

/**
 * Gallery panel that only updates images when they become visible in the scrollable pane, which is the parent of this
 * panel.
 */
class TileGalleryViewImpl : JPanel(), TileGalleryView {

    private val SPACING = 5
    private val TILE_SIZE = 120
    private val COLUMNS = 2

    var tiles: List<Tile> = emptyList()

    /** Model for the vertical scroll bar  */
    private var boundedRangeModel: BoundedRangeModel? = null

    private var rows: Int = 0

    override fun updateState(state: TileGalleryView.TileGalleryState) {
        tiles = state.tiles

        calculateGrid()
        drawTiles()
    }

    private fun calculateGrid() {
        rows = Math.ceil(tiles.size.toDouble() / COLUMNS).toInt()
        layout = GridLayout(rows, COLUMNS, SPACING, SPACING)
    }

    private fun drawTiles() {
        for ((sourceImage, name) in tiles) {

            val imageIcon = ImageIcon(sourceImage
                    .get()
                    .image
                    .getScaledInstance(TILE_SIZE, TILE_SIZE, SCALE_SMOOTH)
            )

            val jLabel = JLabel(name, imageIcon, CENTER)
            with(jLabel) {
                verticalTextPosition = BOTTOM
                horizontalTextPosition = CENTER
            }

            add(jLabel)
        }
    }

    fun setBrm(brm: BoundedRangeModel) {
        this.boundedRangeModel = brm
    }
}
