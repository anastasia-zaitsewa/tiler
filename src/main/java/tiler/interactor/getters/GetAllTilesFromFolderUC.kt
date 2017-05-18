package tiler.interactor.getters

import io.reactivex.Observable
import tiler.model.FileSourceImage
import tiler.model.Tile
import java.io.File

class GetAllTilesFromFolderUC() {

    fun get(path: String): Observable<List<Tile>> {
        return Observable.fromCallable {
            (File(path).listFiles() ?: emptyArray())
                .toList()
                .map { file -> Tile(FileSourceImage(file.path), file.nameWithoutExtension) }
        }
    }
}