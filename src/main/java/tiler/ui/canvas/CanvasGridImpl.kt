package tiler.ui.canvas

import javafx.event.EventHandler
import javafx.geometry.Dimension2D
import javafx.scene.canvas.Canvas
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import tiler.model.Cell

/**
 * Draws grid for canvas
 */
class CanvasGridImpl : Canvas(), CanvasGrid {

    var gridSize = 0
    var cellSize = 0.0
    var cells: List<Cell> = emptyList()

    override fun updateState(state: CanvasGrid.CanvasGridState) {
        cells = state.cells
        gridSize = state.gridSize
        cellSize = state.cellSize
    }

    val gc = graphicsContext2D

    var selectedCellCoordinates: Dimension2D? = null

    init {
        height = (cellSize * gridSize)
        width = (cellSize * gridSize)
        onMouseMoved = CellMouseAdapter()
        onMouseExited = EventHandler<MouseEvent> {
            _ ->
            clearSelectedCell()
        }

        paintComponent()
    }


    fun paintComponent() {

        if (cells.isEmpty()) {
            return
        }

        gc.stroke = Color.GRAY
        cells.map { it.rectangle
        }.forEach {
                    gc.strokeRect(
                            it.x, it.y,
                            it.width, it.height
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
            if (event == null) {
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
        selectedCellCoordinates?.let {
            val rectangle = cells[(it.width + (it.height * gridSize)).toInt()].rectangle
            gc.clearRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height)
        }
    }
}

