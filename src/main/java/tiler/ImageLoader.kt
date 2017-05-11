package tiler

import java.io.File

class ImageLoader {

    fun getAllImageFilesFromFolder(folderPath: String): List<File> {
        return (File(folderPath).listFiles() ?: emptyArray())
                .toList()
    }
}