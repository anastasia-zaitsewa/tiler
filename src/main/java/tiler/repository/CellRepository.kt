package tiler.repository

import io.reactivex.Single
import tiler.model.Cell

interface CellRepository{
    fun cells(): Single<List<Cell>>
}
