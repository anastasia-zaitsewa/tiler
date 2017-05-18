package tiler.ui.gallery

import tiler.model.Tile
import java.awt.GridLayout
import java.awt.Image.SCALE_SMOOTH
import java.awt.event.ComponentEvent
import java.awt.event.ComponentListener
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
class TileGalleryViewImpl : JPanel(), ComponentListener, TileGalleryView {

    private val SPACEING = 5
    private val IMAGE_DIM = 50
    private val TILE_SIZE = 120


    var tiles: List<Tile> = emptyList()
    /** Number of rows that are visible in the viewport  */
    private var visibleRows: Double = 0.toDouble()

    /** Model for the vertical scroll bar  */
    private var boundedRangeModel: BoundedRangeModel? = null

    private val columns = 3
    private var rows: Int = 0

    /**
     * Create the panel.
     */
    init {
//        val allImageFilesFromFolder = (System.getProperty("user.dir") + "/Tiles")



    }

    override fun updateState(state: TileGalleryView.TileGalleryState) {
        tiles = state.tiles

        calculateGrid()
        drawTiles()
    }

    private fun calculateGrid(){
        rows = Math.ceil((tiles.size / columns).toDouble()).toInt()
        layout = GridLayout(rows, columns + 1, SPACEING, SPACEING)
    }

    private fun drawTiles(){
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

    /**
     * Make sure that everything that is visible is loaded
     */
    private fun updateView() {
        val percentage = 100.0 * boundedRangeModel!!.value / (boundedRangeModel!!.maximum - boundedRangeModel!!.extent)
        val firstRow = computeFirstRowIndex(percentage / 100)
        val lastRow = computeLastRowIndex(percentage / 100)
    }


    /**
     * Figure out the index of the last row to be shown
     * @param percentage of scrolling [0,1]
     * *
     * @return
     */
    private fun computeLastRowIndex(percentage: Double): Int {
        val pos = computeTopPosition(percentage)
        val result = Math.floor((pos - SPACEING) /
                (SPACEING + IMAGE_DIM) + visibleRows).toInt()
        return Math.min(result, rows)
    }

    /**
     * Figure out the index of the first row to be shown
     * @param percentage of scrolling [0,1]
     * *
     * @return
     */
    private fun computeFirstRowIndex(percentage: Double): Int {
        val pos = computeTopPosition(percentage)
        val result = Math.floor((pos - SPACEING) /
                (SPACEING + IMAGE_DIM)).toInt()
        return Math.max(0, result)
    }

    /**
     * Compute the top position in the panel that is visible.
     * @param percentage
     * *
     * @return
     */
    private fun computeTopPosition(percentage: Double): Double {
        /*
		 * The percentage indicates the scroll position, so 0% means all to
		 * the top, 100% is all the bottom. The top position of the bottom however
		 * is the height of the panel - the height of the viewport (parent). anything
		 * in between is procentual
		 */
        val top = bounds.height * percentage
        val correction = parent.bounds.height * percentage
        return top - correction
    }

    fun setBrm(brm: BoundedRangeModel) {
        this.boundedRangeModel = brm
    }

    /**
     * @see ComponentListener.componentHidden
     */
    override fun componentHidden(e: ComponentEvent) {
        // Do nothing
    }

    /**
     * @see ComponentListener.componentMoved
     */
    override fun componentMoved(e: ComponentEvent) {
        // Do nothing
    }

    /**
     * When the component is resized, the visible portion changes, take care of the
     * appropriate actions.
     * @see ComponentListener.componentResized
     */
    override fun componentResized(e: ComponentEvent) {
        val rowHeigth = SPACEING + IMAGE_DIM
        visibleRows = 1.0 * parent.bounds.height / rowHeigth
        updateView() // make sure the view is properly updated
    }

    /**
     * We are not interested in reacting when the component is shown again.
     * @see ComponentListener.componentShown
     */
    override fun componentShown(e: ComponentEvent) {
    }
}
