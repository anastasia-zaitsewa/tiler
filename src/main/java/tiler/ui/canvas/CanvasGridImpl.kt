package tiler.ui.canvas

import javafx.event.EventHandler
import javafx.geometry.Dimension2D
import javafx.scene.canvas.Canvas
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import tiler.model.Cell
import tiler.model.DEFAULT_CELL_SIZE
import tiler.model.DEFAULT_GRID_SIZE

/**
 * Draws grid for canvas
 */
class CanvasGridImpl : Canvas(), CanvasGrid {

    val gc = graphicsContext2D

    var gridSize = DEFAULT_GRID_SIZE
    var cellSize = DEFAULT_CELL_SIZE

    var cells: List<Cell> = emptyList()

    var selectedCellCoordinates: Dimension2D? = null

    init {
        height = (cellSize * gridSize)
        width = (cellSize * gridSize)
        onMouseMoved = CellMouseAdapter()
        onMouseExited = EventHandler<MouseEvent> {
            _ -> clearSelectedCell()
        }

        paintComponent()
    }

    override fun updateState(state: CanvasGrid.CanvasGridState) {
        cells = state.cells
        paintComponent()
    }

    fun paintComponent() {

        if (cells.isEmpty()) {
            return
        }

        gc.stroke = Color.GRAY
        cells.forEach {
            gc.strokeRect(
                    it.rectangle.x, it.rectangle.y,
                    it.rectangle.width, it.rectangle.height
            )
        }
    }

    private fun paintSelectedCell() {
        selectedCellCoordinates?.let {
            with(it) {
                val rectangle = cells[(width + (height * gridSize)).toInt()]
                        .rectangle

                gc.fill = Color.CYAN
                gc.fillRect(
                        rectangle.x, rectangle.y,
                        rectangle.width, rectangle.height
                )
            }
        }
    }

    private inner class CellMouseAdapter : EventHandler<MouseEvent> {
        override fun handle(event: MouseEvent?) {
            if (event == null || cells.isEmpty()) {
                return
            }

            val column = Math.floor(event.x / cellSize)
            val row = Math.floor(event.y / cellSize)

            selectedCellCoordinates?.let {
                if (sameCell(it, column, row)) {
                    return
                }
            }

            if (column in 0 until gridSize
                    && row in 0 until gridSize) {

                clearSelectedCell()
                selectedCellCoordinates = Dimension2D(column, row)
                paintSelectedCell()
            }
        }

        private fun sameCell(it: Dimension2D, column: Double, row: Double) = it.width == column && it.height == row
    }

    private fun clearSelectedCell() {
        if (cells.isEmpty()) {
            return
        }

        selectedCellCoordinates?.let {
            val rectangle = cells[(it.width + (it.height * gridSize)).toInt()].rectangle
            gc.clearRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height)
        }
    }
}

