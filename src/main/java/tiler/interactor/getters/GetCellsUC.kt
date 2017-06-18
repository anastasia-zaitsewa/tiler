package tiler.interactor.getters

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import tiler.model.Cell
import tiler.model.FileSourceImageUrl
import tiler.model.Tile
import tiler.repository.CellRepository
import tiler.repository.FileRepository

open class GetCellsUC(
        private val cellsRepository: CellRepository
) {

    /**
     * Provides all [Cell]s
     */
    open fun getAll(): Observable<List<Cell>> {
        return cellsRepository
                .cells()
                .toObservable()
                .observeOn(Schedulers.io())
    }
}