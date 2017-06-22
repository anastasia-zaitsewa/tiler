package tiler.repository

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.File

/**
 * Java specific impl of {@link FileRepository}
 */
class JavaFileRepository : FileRepository {
    override fun listFiles(path: String): Single<List<File>> {
        return Single.fromCallable {
            File(path).listFiles().toList()
        }
    }
}