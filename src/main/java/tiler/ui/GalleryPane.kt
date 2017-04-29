package tiler.ui

import tiler.ImageLoader
import java.awt.Color
import java.awt.Graphics2D
import java.awt.GridLayout
import java.awt.RenderingHints
import java.awt.event.AdjustmentEvent
import java.awt.event.AdjustmentListener
import java.awt.event.ComponentEvent
import java.awt.event.ComponentListener
import java.awt.image.BufferedImage
import javax.swing.BoundedRangeModel
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

/**
 * Gallery panel that only updates images when they become visible in the scrollable pane, which is the parent of this
 * panel.
 */
class GalleryPane : JPanel(), AdjustmentListener, ComponentListener {

    private val images: Array<BufferedImage?>?
    private val icons: Array<JLabel?>?
    /** Nubmer of rows that are visible in the viewport  */
    private var visibleNbRows: Double = 0.toDouble()

    /** Model for the vertical scroll bar  */
    private var brm: BoundedRangeModel? = null

    private val nbCols = 3
    private val nbRows: Int

    /**
     * Create the panel.
     */
    init {
        images = ImageLoader().loadImagesFromFolder(System.getProperty("user.dir") + "/Tiles").toTypedArray()
        icons = arrayOfNulls<JLabel>(images.size)
        nbRows = Math.ceil(1.0 * images.size / nbCols).toInt()
        border = EmptyBorder(5, 5, 5, 5)
        layout = GridLayout(nbRows, nbCols + 1, SPACEING, SPACEING)
        for (i in images.indices) {
            if (i % 3 == 0) {
                add(JLabel((i / 3).toString()))
            }
            icons[i] = JLabel(ImageIcon(images[i]))
            add(icons[i])
        }
    }

    /**
     * This method is called when the scroll bar is moved.
     * @see AdjustmentListener.adjustmentValueChanged
     */
    override fun adjustmentValueChanged(e: AdjustmentEvent) {
        updateView()
    }

    /**
     * Make sure that everything that is visible is loaded
     */
    private fun updateView() {
        val percentage = 100.0 * brm!!.value / (brm!!.maximum - brm!!.extent)
        val firstRow = computeFirstRowIndex(percentage / 100)
        val lastRow = computeLastRowIndex(percentage / 100)
        updateImages(firstRow, lastRow)
    }

    /**
     * Load and unload the images that are contained between the rows
     * @param firstRow
     * *
     * @param lastRow
     */
    private fun updateImages(firstRow: Int, lastRow: Int) {
        val firtsImageIndex = firstRow * nbCols
        val lastImageIndex = Math.min(lastRow * nbCols + nbCols - 1, images!!.size - 1) // make sure we do not try accessing an element that is not there
        for (i in firtsImageIndex..lastImageIndex) {
            if (images[i] == null) {
                val blackSquare = BufferedImage(IMAGE_DIM, IMAGE_DIM, BufferedImage.TYPE_BYTE_GRAY)
                val g = blackSquare.graphics as Graphics2D
                val img = BufferedImage(IMAGE_DIM, IMAGE_DIM, BufferedImage.TYPE_4BYTE_ABGR)
                val g2d = img.graphics as Graphics2D
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON)
                val frc = g2d.fontRenderContext

                var font = g2d.font
                font = font.deriveFont(24)

                val gv = font.createGlyphVector(frc, i.toString())
                val rect = gv.visualBounds
                val x = ((GalleryPane.Companion.IMAGE_DIM - rect.width) / 2).toInt()
                val y = ((GalleryPane.Companion.IMAGE_DIM - rect.height) / 2).toInt()
                g2d.drawGlyphVector(gv, x.toFloat(), y.toFloat())
                g.drawImage(img, 0, 0, this)
                icons?.get(i)?.icon = ImageIcon(blackSquare)
            }
        }
    }

    /**
     * Figure out the index of the last row to be shown
     * @param percentage of scrolling [0,1]
     * *
     * @return
     */
    private fun computeLastRowIndex(percentage: Double): Int {
        val pos = computeTopPosition(percentage)
        val result = Math.floor((pos - GalleryPane.Companion.SPACEING) / (GalleryPane.Companion.SPACEING + GalleryPane.Companion.IMAGE_DIM) + visibleNbRows).toInt()
        return Math.min(result, nbRows)
    }

    /**
     * Figure out the index of the first row to be shown
     * @param percentage of scrolling [0,1]
     * *
     * @return
     */
    private fun computeFirstRowIndex(percentage: Double): Int {
        val pos = computeTopPosition(percentage)
        val result = Math.floor((pos - GalleryPane.Companion.SPACEING) / (GalleryPane.Companion.SPACEING + GalleryPane.Companion.IMAGE_DIM)).toInt()
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
        this.brm = brm
    }

    /**
     * We are not interested in reacting when the component becomes hidden
     * @see ComponentListener.componentHidden
     */
    override fun componentHidden(e: ComponentEvent) {
    }

    /**
     * We are not interested in reacting when the component is moved
     * @see ComponentListener.componentMoved
     */
    override fun componentMoved(e: ComponentEvent) {
    }

    /**
     * When the component is resized, the visible portion changes, take kare of the
     * appropriate actions.
     * @see ComponentListener.componentResized
     */
    override fun componentResized(e: ComponentEvent) {
        val rowHeigth = GalleryPane.Companion.SPACEING + GalleryPane.Companion.IMAGE_DIM
        visibleNbRows = 1.0 * parent.bounds.height / rowHeigth
        updateView() // make sure the view is properly updated
    }

    /**
     * We are not interested in reacting when the component is shown again.
     * @see ComponentListener.componentShown
     */
    override fun componentShown(e: ComponentEvent) {
    }

    companion object {
        private val SPACEING = 5
        private val IMAGE_DIM = 50
    }

}
