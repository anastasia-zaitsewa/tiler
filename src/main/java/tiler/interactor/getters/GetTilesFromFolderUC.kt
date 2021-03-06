package tiler.interactor.getters

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import tiler.model.FileSourceImageUrl
import tiler.model.Tile
import tiler.repository.FileRepository

/**
 * Provides all {@link Tile}s from given folder
 */
open class GetTilesFromFolderUC(
        private val fileRepository: FileRepository
) {

    /**
     * Provides all {@link Tile}s from given folder
     */
    open fun getAll(path: String): Observable<List<Tile>> {
        return fileRepository
                .listFiles(path)
                .map { it.map {
                        Tile(FileSourceImageUrl(it.path), it.nameWithoutExtension)
                    }
                }
                .toObservable()
                .observeOn(Schedulers.io())
    }
}