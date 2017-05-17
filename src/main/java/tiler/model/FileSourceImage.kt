package tiler.model

import javax.swing.ImageIcon

class FileSourceImage(path: String) : SourceImage {

    val image: ImageIcon = ImageIcon(path)

    override fun get(): ImageIcon {
        return image
    }
}