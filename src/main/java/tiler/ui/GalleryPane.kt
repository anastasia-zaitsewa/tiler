package tiler.ui

import tiler.ImageLoader
import java.awt.GridLayout
import java.awt.event.AdjustmentEvent
import java.awt.event.AdjustmentListener
import java.awt.event.ComponentEvent
import java.awt.event.ComponentListener
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

    private val images: Array<String>?
    private val icons: Array<JLabel?>?
    private val names: Array<String>?
    /** Number of rows that are visible in the viewport  */
    private var visibleRows: Double = 0.toDouble()

    /** Model for the vertical scroll bar  */
    private var boundedRangeModel: BoundedRangeModel? = null

    private val columns = 3
    private val rows: Int

    /**
     * Create the panel.
     */
    init {
        val allImageFilesFromFolder = ImageLoader()
                .getAllImageFilesFromFolder(System.getProperty("user.dir") + "/Tiles")

        names = allImageFilesFromFolder
                .map { it.nameWithoutExtension }
                .toTypedArray()
        images = allImageFilesFromFolder
                .map { it.absolutePath }
                .toTypedArray()
        icons = arrayOfNulls<JLabel>(images.size)
        rows = Math.ceil(1.0 * images.size / columns).toInt()
        border = EmptyBorder(5, 5, 5, 5)

        layout = GridLayout(rows, columns + 1, SPACEING, SPACEING)

        for (i in images.indices) {
            if (i % 3 == 0) {
                add(JLabel((i / 3).toString()))
            }
            var imageIcon = ImageIcon(images[i]) // load the image to a imageIcon
            val image = imageIcon.image // transform it
            val newimg = image.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH) // scale it the smooth way
            imageIcon = ImageIcon(newimg)  // transform it back

            val jLabel = JLabel(names[i], imageIcon, JLabel.CENTER)
            jLabel.verticalTextPosition = JLabel.BOTTOM
            jLabel.horizontalTextPosition = JLabel.CENTER
            icons[i] = jLabel
            add(icons[i])
        }
    }

    /**
     * This method is called when the scroll bar is moved.
     * @see AdjustmentListener.adjustmentValueChanged
     */
    override fun adjustmentValueChanged(e: AdjustmentEvent) {
        //Do nothing
    }

    /**
     * Make sure that everything that is visible is loaded
     */
    private fun updateView() {
        val percentage = 100.0 * boundedRangeModel!!.value / (boundedRangeModel!!.maximum - boundedRangeModel!!.extent)
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
        val firstImageIndex = firstRow * columns
        val lastImageIndex = Math.min(lastRow * columns + columns - 1, images!!.size - 1) // make sure we do not try accessing an element that is not there
        for (i in firstImageIndex..lastImageIndex) {
            var imageIcon = ImageIcon(images[i]) // load the image to imageIcon
            val image = imageIcon.image // transform it
            val newimg = image.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH) // scale it the smooth way
            imageIcon = ImageIcon(newimg)  // transform it back
            icons?.get(i)?.icon = imageIcon
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
        val result = Math.floor((pos - GalleryPane.Companion.SPACEING) /
                (GalleryPane.Companion.SPACEING + GalleryPane.Companion.IMAGE_DIM) + visibleRows).toInt()
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
        val result = Math.floor((pos - GalleryPane.Companion.SPACEING) /
                (GalleryPane.Companion.SPACEING + GalleryPane.Companion.IMAGE_DIM)).toInt()
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
        visibleRows = 1.0 * parent.bounds.height / rowHeigth
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
