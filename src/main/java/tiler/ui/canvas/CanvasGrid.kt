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
    val HARD_CODE_CELL_SIZE = 50

    val cells = ArrayList<Rectangle>(HARD_CODE_GRID_SIZE * HARD_CODE_GRID_SIZE)
    var selectedCell: Dimension2D? = null

    init {
        height = (HARD_CODE_CELL_SIZE * HARD_CODE_GRID_SIZE).toDouble()
        width = (HARD_CODE_CELL_SIZE * HARD_CODE_GRID_SIZE).toDouble()
        onMouseMoved = CellMouseAdapter()
        paintComponent()
    }


//    override fun invalidate() {
//        cells.clear()
//        selectedCell = null
//        super.invalidate()
//    }

    fun paintComponent() {

        val xOffset = (width - (HARD_CODE_GRID_SIZE * HARD_CODE_CELL_SIZE)) / 2
        val yOffset = (height - (HARD_CODE_GRID_SIZE * HARD_CODE_CELL_SIZE)) / 2

        if (cells.isEmpty()) {
            calculateGrid(xOffset, yOffset)
        }

        paintSelectedCell()

        graphicsContext2D.stroke = Color.GRAY
        for (cell in cells) {
            graphicsContext2D.strokeRect(cell.x, cell.y, cell.width, cell.height)
        }
    }

    private fun paintSelectedCell() {
        selectedCell?.let {
            with(it) {
                val cell = cells[(width + (height * HARD_CODE_GRID_SIZE)).toInt()]

                graphicsContext2D.fill = Color.CYAN
                graphicsContext2D.fillRect(cell.x, cell.y, cell.width, cell.height)
            }
        }
    }

    private fun calculateGrid(xOffset: Double, yOffset: Double) {
        for (row in 0 until HARD_CODE_GRID_SIZE) {
            for (col in 0 until HARD_CODE_GRID_SIZE) {
                cells.add(
                        Rectangle(
                                (xOffset + (col * HARD_CODE_CELL_SIZE)),
                                (yOffset + (row * HARD_CODE_CELL_SIZE)),
                                HARD_CODE_CELL_SIZE.toDouble(),
                                HARD_CODE_CELL_SIZE.toDouble()
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

            val xOffset = (width - (HARD_CODE_GRID_SIZE * HARD_CODE_CELL_SIZE)) / 2
            val yOffset = (height - (HARD_CODE_GRID_SIZE * HARD_CODE_CELL_SIZE)) / 2

            if (event.x >= xOffset && event.y >= yOffset) {
                val column = (event.x - xOffset) / HARD_CODE_CELL_SIZE
                val row = (event.y - yOffset) / HARD_CODE_CELL_SIZE

                if (column in 0 until HARD_CODE_GRID_SIZE
                        && row in 0 until HARD_CODE_GRID_SIZE) {
                    selectedCell = Dimension2D(column, row)
                }
            }
        }
    }
}