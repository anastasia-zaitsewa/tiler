package tiler.repository

import io.reactivex.Single
import java.io.File

interface FileRepository {
    /**
     * List all file in given path.
     */
    fun listFiles(path: String): Single<List<File>>
}
