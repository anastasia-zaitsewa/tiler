package tiler.model

import javafx.scene.image.Image
import java.io.File

/**
 * Represents image source for {@link Tile}
 */
data class FileSourceImage(val path: String) : SourceImage {

    val get: Image = Image(
            File(path).toURI().toURL().toString(),
            true
    )

    val sourcePath: String = File(path).toURI().toURL().toString()

    override fun get(): Image {
        return get
    }
}