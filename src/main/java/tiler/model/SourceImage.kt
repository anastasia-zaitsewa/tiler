package tiler.model

import javax.swing.ImageIcon

interface SourceImage {
    /**
     * Provides source image
     */
    fun get(): ImageIcon
}