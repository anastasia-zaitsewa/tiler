package tiler

import tiler.interactor.getters.GetAllTilesFromFolderUC
import tiler.ui.gallery.TileGalleryPresenter
import tiler.ui.gallery.TileGalleryViewImpl
import java.awt.EventQueue
import javax.swing.JFrame
import javax.swing.JFrame.EXIT_ON_CLOSE
import javax.swing.JScrollPane


class Tiler {

    private val mainFrame: JFrame = JFrame()
    private val tileGalleryViewImpl: TileGalleryViewImpl = TileGalleryViewImpl()

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
        val scrollPane = JScrollPane(tileGalleryViewImpl)
        scrollPane.verticalScrollBar.unitIncrement = 16
        val verticalScrollBar = scrollPane.verticalScrollBar
        val brm = verticalScrollBar.model

        tileGalleryViewImpl.setBrm(brm)

        mainFrame.contentPane = scrollPane
    }

    companion object {

        /**
         * Launch the application.
         */
        @JvmStatic fun main(args: Array<String>) {
            EventQueue.invokeLater {
                try {
                    val tiler = Tiler()
                    tiler.mainFrame.isVisible = true
                    TileGalleryPresenter(GetAllTilesFromFolderUC())
                            .start(tiler.tileGalleryViewImpl)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
