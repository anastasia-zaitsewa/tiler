package tiler

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class ImageLoader {
    fun loadImage(path: String): BufferedImage {
        return ImageIO.read(File(path))
    }

    fun getAllImagesFromFolder(folderPath: String): List<String> {
        return (File(folderPath).listFiles() ?: emptyArray())
                .toList()
                .map { it.absolutePath }
    }
}