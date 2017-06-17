package tiler.model

import java.io.File

/**
 * Represents image source for {@link Tile}
 */
data class FileSourceImageUrl(val path: String) : SourceImageUrl {

    val sourcePath: String = File(path).toURI().toURL().toString()

    override fun get(): String {
        return sourcePath
    }
}