package tiler

import tiler.ui.GalleryPane
import java.awt.EventQueue
import javax.swing.JFrame
import javax.swing.JFrame.EXIT_ON_CLOSE
import javax.swing.JScrollPane


class Tiler {

    private val mainFrame: JFrame = JFrame()
    private val contentPane: GalleryPane = GalleryPane()

    /**
     * Create the frame.
     */
    init {
        initMainFrame()
        createComponents()
    }

    private fun initMainFrame() {
        mainFrame.defaultCloseOperation = EXIT_ON_CLOSE
        mainFrame.setBounds(100, 100, 600, 300)
        println("Frame bounds: " + mainFrame.bounds)
        println("Frame insets: " + mainFrame.insets)
    }

    private fun createComponents() {
        mainFrame.addComponentListener(contentPane) // resize listener to the frame

        val scrollPane = JScrollPane(contentPane)
        scrollPane.verticalScrollBar.unitIncrement = 16
        val verticalScrollBar = scrollPane.verticalScrollBar
        val brm = verticalScrollBar.model

        contentPane.setBrm(brm)
        verticalScrollBar.addAdjustmentListener(contentPane)

        mainFrame.contentPane = scrollPane
    }

    companion object {

        /**
         * Launch the application.
         */
        @JvmStatic fun main(args: Array<String>) {
            EventQueue.invokeLater {
                try {
                    Tiler().mainFrame.isVisible = true
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
