package tiler.ui.canvas

import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JPanel

/**
 * Draws grid for canvas
 */
class CanvasGrid : JPanel() {

    val HARD_CODE_GRID_SIZE = 10
    val HARD_CODE_CELL_SIZE = 50

    val cells = ArrayList<Rectangle>(HARD_CODE_GRID_SIZE * HARD_CODE_GRID_SIZE)
    var selectedCell: Point? = null

    init {
        addMouseMotionListener(CellMouseAdapter())
    }

    override fun invalidate() {
        cells.clear()
        selectedCell = null
        super.invalidate()
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)

        val graphics = g?.create() as Graphics2D

        val xOffset = (width - (HARD_CODE_GRID_SIZE * HARD_CODE_CELL_SIZE)) / 2
        val yOffset = (height - (HARD_CODE_GRID_SIZE * HARD_CODE_CELL_SIZE)) / 2

        if (cells.isEmpty()) {
            calculateGrid(xOffset, yOffset)
        }

        paintSelectedCell(graphics)

        with(graphics) {
            color = Color.GRAY

            for (cell in cells) {
                draw(cell)
            }

            dispose()
        }

    }

    override fun getPreferredSize(): Dimension {
        return Dimension(HARD_CODE_CELL_SIZE * HARD_CODE_GRID_SIZE, HARD_CODE_CELL_SIZE * HARD_CODE_GRID_SIZE)
    }


    private fun paintSelectedCell(graphics: Graphics2D) {
        selectedCell?.let {
            with(it) {
                val cell = cells[x + (y * HARD_CODE_GRID_SIZE)]

                with(graphics) {
                    color = (Color.CYAN)
                    fill(cell)
                }
            }
        }
    }

    private fun calculateGrid(xOffset: Int, yOffset: Int) {
        for (row in 0 until HARD_CODE_GRID_SIZE) {
            for (col in 0 until HARD_CODE_GRID_SIZE) {
                cells.add(
                        Rectangle(
                                xOffset + (col * HARD_CODE_CELL_SIZE),
                                yOffset + (row * HARD_CODE_CELL_SIZE),
                                HARD_CODE_CELL_SIZE,
                                HARD_CODE_CELL_SIZE
                        )
                )
            }
        }
    }

    private inner class CellMouseAdapter : MouseAdapter() {
        override fun mouseMoved(e: MouseEvent?) {

            if (e == null) {
                return
            }

            val xOffset = (width - (HARD_CODE_GRID_SIZE * HARD_CODE_CELL_SIZE)) / 2
            val yOffset = (height - (HARD_CODE_GRID_SIZE * HARD_CODE_CELL_SIZE)) / 2

            if (e.x >= xOffset && e.y >= yOffset) {
                val column = (e.x - xOffset) / HARD_CODE_CELL_SIZE
                val row = (e.y - yOffset) / HARD_CODE_CELL_SIZE

                if (column in 0 until HARD_CODE_GRID_SIZE
                        && row in 0 until HARD_CODE_GRID_SIZE) {
                    selectedCell = Point(column, row)
                }
            }

            repaint()
        }
    }
}