package tiler

import tiler.ui.GalleryPane

import java.awt.EventQueue

import javax.swing.BoundedRangeModel
import javax.swing.JFrame
import javax.swing.JScrollBar
import javax.swing.JScrollPane

class Tiler : JFrame() {

    private var contentPane: GalleryPane? = null

    /**
     * Create the frame.
     */
    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        setBounds(100, 100, 450, 300)
        println("Frame bounds: " + bounds)
        println("Frame insets: " + insets)
        createCompontents()
    }

    private fun createCompontents() {
        contentPane = GalleryPane()
        this.addComponentListener(contentPane) // resize listener to the frame
        val scrollPane = JScrollPane(contentPane)
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
