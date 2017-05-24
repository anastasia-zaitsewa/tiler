package tiler.model

import javax.swing.ImageIcon

/**
 * Represents image source for {@link Tile}
 */
data class FileSourceImage(val path: String) : SourceImage {

    val image: ImageIcon = ImageIcon(path)

    override fun get(): ImageIcon {
        return image
    }
}