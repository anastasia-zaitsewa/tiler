package tiler.repository

import io.reactivex.Single
import java.io.File

interface FileRepository {
    fun listFiles(path: String): Single<List<File>>
}
