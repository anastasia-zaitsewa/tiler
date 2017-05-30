package tiler.ui.gallery

import tiler.model.Tile
import java.awt.GridLayout
import java.awt.Image.SCALE_SMOOTH
import javax.swing.*
import javax.swing.JLabel.BOTTOM
import javax.swing.JLabel.CENTER

/**
 * Swing implementation - shows tile gallery.
 */
class TileGalleryViewImpl(val mainFrame: JFrame) : JPanel(), TileGalleryView {

    private val SPACING = 5
    private val TILE_SIZE = 120
    private val COLUMNS = 2

    var tiles: List<Tile> = emptyList()

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

        mainFrame.pack()
    }
}
