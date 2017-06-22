package tiler.repository

import io.reactivex.Single
import javafx.scene.shape.Rectangle
import tiler.model.Cell
import tiler.model.DEFAULT_CELL_SIZE
import tiler.model.DEFAULT_GRID_SIZE

class FixGridCellRepository : CellRepository {

    val cells = ArrayList<Rectangle>(DEFAULT_GRID_SIZE * DEFAULT_GRID_SIZE)

    init {
        calculateGrid()
    }

    override fun cells(): Single<List<Cell>> {
        return Single.fromCallable {
            cells.map { Cell(it) }
        }
    }

    private fun calculateGrid() {
        for (row in 0 until DEFAULT_GRID_SIZE) {
            for (col in 0 until DEFAULT_GRID_SIZE) {
                cells.add(
                        Rectangle(
                                col * DEFAULT_CELL_SIZE,
                                row * DEFAULT_CELL_SIZE,
                                DEFAULT_CELL_SIZE,
                                DEFAULT_CELL_SIZE
                        )
                )
            }
        }
    }
}