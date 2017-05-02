package tiler

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class ImageLoader {
    fun loadImage(path: String): BufferedImage {
        return ImageIO.read(File(path))
    }

    fun loadImagesFromFolder(folderPath: String): List<BufferedImage> {
        return (File(folderPath).listFiles() ?: emptyArray())
                .take(20)
                .toList()
                .map { loadImage(it.absolutePath) }
    }

    fun getAllImagesFromFolder(folderPath: String): List<String> {
        return (File(folderPath).listFiles() ?: emptyArray())
                .take(200)
                .toList()
                .map { it.absolutePath }
    }
}