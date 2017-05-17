package tiler.model

import java.awt.image.BufferedImage
import javax.swing.ImageIcon

interface SourceImage {
    /**
     * Provides source image
     */
    fun get() : ImageIcon
}