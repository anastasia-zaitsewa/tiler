package tiler

import tiler.interactor.getters.GetTilesFromFolderUC
import tiler.repository.JavaFileRepository
import tiler.ui.canvas.CanvasGrid
import tiler.ui.gallery.TileGalleryPresenter
import tiler.ui.gallery.TileGalleryViewImpl
import java.awt.BorderLayout
import java.awt.BorderLayout.LINE_START
import java.awt.EventQueue
import javax.swing.JFrame
import javax.swing.JFrame.EXIT_ON_CLOSE
import javax.swing.JScrollPane


class Tiler {

    private val mainFrame: JFrame = JFrame()
    private val tileGalleryViewImpl: TileGalleryViewImpl = TileGalleryViewImpl(mainFrame)

    /**
     * Create the frame.
     */
    init {
        initMainFrame()
        initContainer()
        createComponents()
    }

    private fun initContainer() {
    }

    private fun initMainFrame() {
        with(mainFrame) {
            defaultCloseOperation = EXIT_ON_CLOSE
            setBounds(100, 100, 800, 300)
            layout = BorderLayout()
        }

        println("Frame bounds: " + mainFrame.bounds)
        println("Frame insets: " + mainFrame.insets)
    }

    private fun createComponents() {
        val scrollPane = JScrollPane(tileGalleryViewImpl)
        scrollPane.verticalScrollBar.unitIncrement = 16
        val verticalScrollBar = scrollPane.verticalScrollBar
        val brm = verticalScrollBar.model

        tileGalleryViewImpl.setBrm(brm)

        mainFrame.add(scrollPane, LINE_START)

        mainFrame.add(CanvasGrid())
        mainFrame.setLocationRelativeTo(null)
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
                    TileGalleryPresenter(GetTilesFromFolderUC(JavaFileRepository()))
                            .start(tiler.tileGalleryViewImpl)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
