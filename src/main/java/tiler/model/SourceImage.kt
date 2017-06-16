package tiler.model

import javafx.scene.image.Image
import javax.swing.ImageIcon

interface SourceImage {
    /**
     * Provides source image
     */
    fun get(): Image
}