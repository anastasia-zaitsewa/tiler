package tiler

import tiler.ui.GalleryPane
import java.awt.EventQueue
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.SwingUtilities


class Tiler : JFrame() {

    private var contentPane: GalleryPane? = null

    /**
     * Create the frame.
     */
    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        setBounds(100, 100, 600, 300)
        println("Frame bounds: " + bounds)
        println("Frame insets: " + insets)
        SwingUtilities.invokeLater {
            // Swing object creation and modification code!
            createCompontents()
        }
    }

    private fun createCompontents() {
        contentPane = GalleryPane()
        this.addComponentListener(contentPane) // resize listener to the frame
        val scrollPane = JScrollPane(contentPane)
        scrollPane.verticalScrollBar.unitIncrement = 16
        val verticalScrollBar = scrollPane.verticalScrollBar
        val brm = verticalScrollBar.model
        contentPane!!.setBrm(brm)
        verticalScrollBar.addAdjustmentListener(contentPane)
        setContentPane(scrollPane)

    }

    companion object {

        /**
         * Launch the application.
         */
        @JvmStatic fun main(args: Array<String>) {
            EventQueue.invokeLater {
                try {
                    val frame = Tiler()
                    frame.isVisible = true
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
