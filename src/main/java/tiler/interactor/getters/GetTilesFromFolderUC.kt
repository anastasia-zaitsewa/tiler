package tiler.interactor.getters

import io.reactivex.Observable
import tiler.model.FileSourceImage
import tiler.model.Tile
import java.io.File

/**
 * Provides all {@link Tile}s from given folder
 */
class GetTilesFromFolderUC {
    /**
     * Provides all {@link Tile}s from given folder
     */
    fun getAll(path: String): Observable<List<Tile>> {
        return Observable.fromCallable {
            (File(path).listFiles() ?: emptyArray())
                .toList()
                .map { file -> Tile(FileSourceImage(file.path), file.nameWithoutExtension) }
        }
    }
}