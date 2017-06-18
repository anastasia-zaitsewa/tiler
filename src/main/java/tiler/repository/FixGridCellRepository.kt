package tiler.repository

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javafx.scene.shape.Rectangle
import tiler.model.Cell

class FixGridCellRepository : CellRepository {

    val HARD_CODE_GRID_SIZE = 10
    val HARD_CODE_CELL_SIZE = 50.0

    val cells = ArrayList<Rectangle>(HARD_CODE_GRID_SIZE * HARD_CODE_GRID_SIZE)

    init {
        calculateGrid()
    }

    override fun cells(): Single<List<Cell>> {
        return Single.fromCallable {
            cells.map { Cell(it) }
        }.subscribeOn(Schedulers.io())
    }

    private fun calculateGrid() {
        for (row in 0 until HARD_CODE_GRID_SIZE) {
            for (col in 0 until HARD_CODE_GRID_SIZE) {
                cells.add(
                        Rectangle(
                                col * HARD_CODE_CELL_SIZE,
                                row * HARD_CODE_CELL_SIZE,
                                HARD_CODE_CELL_SIZE,
                                HARD_CODE_CELL_SIZE
                        )
                )
            }
        }
    }
}