import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
 * Created by biovamp on 23/04/2017.
 */
class ImageLoader {
    fun loadImage(): BufferedImage {
         return ImageIO.read(File("/Users/biovamp/projects/Tiler/Tiles/18033-Coast 20.jpg"))
    }
}