package tiler.ui.canvas

import javafx.event.EventHandler
import javafx.geometry.Dimension2D
import javafx.scene.canvas.Canvas
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

/**
 * Draws grid for canvas
 */
class CanvasGrid : Canvas() {

    val HARD_CODE_GRID_SIZE = 10
    val HARD_CODE_CELL_SIZE = 50.0

    val gc = graphicsContext2D

    val cells = ArrayList<Rectangle>(HARD_CODE_GRID_SIZE * HARD_CODE_GRID_SIZE)
    var selectedCellCoordinates: Dimension2D? = null

    init {
        height = (HARD_CODE_CELL_SIZE * HARD_CODE_GRID_SIZE).toDouble()
        width = (HARD_CODE_CELL_SIZE * HARD_CODE_GRID_SIZE).toDouble()
        onMouseMoved = CellMouseAdapter()
        onMouseExited = EventHandler<MouseEvent> {
            _ ->  clearSelectedCell()
        }

        paintComponent()
    }


    fun paintComponent() {

        if (cells.isEmpty()) {
            calculateGrid()
        }

        gc.stroke = Color.GRAY
        for (cell in cells) {
            gc.strokeRect(cell.x, cell.y, cell.width, cell.height)
        }
    }

    private fun paintSelectedCell() {
        selectedCellCoordinates?.let {
            with(it) {
                val cell = cells[(width + (height * HARD_CODE_GRID_SIZE)).toInt()]

                gc.fill = Color.CYAN
                gc.fillRect(cell.x, cell.y, cell.width, cell.height)
            }
        }
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

    private inner class CellMouseAdapter : EventHandler<MouseEvent> {
        override fun handle(event: MouseEvent?) {
            if (event == null) {
                return
            }

            val column = Math.floor(event.x / HARD_CODE_CELL_SIZE)
            val row = Math.floor(event.y / HARD_CODE_CELL_SIZE)

            selectedCellCoordinates?.let {
                if (sameCell(it, column, row)){
                    return
                }
            }

            if (column in 0 until HARD_CODE_GRID_SIZE
                    && row in 0 until HARD_CODE_GRID_SIZE)  {

                clearSelectedCell()
                selectedCellCoordinates = Dimension2D(column, row)
                paintSelectedCell()
            }
        }

        private fun sameCell(it: Dimension2D, column: Double, row: Double) = it.width == column && it.height == row
    }

    private fun clearSelectedCell() {
        selectedCellCoordinates?.let {
            val cell = cells[(it.width + (it.height * HARD_CODE_GRID_SIZE)).toInt()]
            gc.clearRect(cell.x, cell.y, cell.width, cell.height)
        }
    }
}