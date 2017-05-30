package tiler.ui.canvas

import java.awt.Color
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JPanel
import javax.swing.border.MatteBorder


class CanvasGrid : JPanel() {

    val HARD_CODE_GRID_SIZE = 10

    init {
        layout = GridBagLayout()
        background = Color.DARK_GRAY

        for (row in 0 until HARD_CODE_GRID_SIZE) {
            for (col in 0 until HARD_CODE_GRID_SIZE) {

                val gridBagConstraints = GridBagConstraints()

                with(gridBagConstraints) {
                    gridx = col
                    gridy = row
                }

                val cell = GridCell()
                with(cell) {
                    border = createProperBorder(row, col)
                }
                add(cell, gridBagConstraints)
            }
        }
    }

    private fun createProperBorder(row: Int, col: Int): MatteBorder {
        if (notRightOrBottomEdge(row)) {
            if (notRightOrBottomEdge(col)) {
                return MatteBorder(1, 1, 0, 0, Color.GRAY)
            } else {
                return MatteBorder(1, 1, 0, 1, Color.GRAY)
            }
        } else {
            if (notRightOrBottomEdge(col)) {
                return MatteBorder(1, 1, 1, 0, Color.GRAY)
            } else {
                return MatteBorder(1, 1, 1, 1, Color.GRAY)
            }
        }
    }

    private fun notRightOrBottomEdge(index: Int) = index < HARD_CODE_GRID_SIZE - 1

    class GridCell : JPanel() {

        val HARD_CODE_CELL_SIZE = 50

        override fun getPreferredSize(): Dimension {
            return Dimension(HARD_CODE_CELL_SIZE, HARD_CODE_CELL_SIZE)
        }

        override fun getMinimumSize(): Dimension {
            return Dimension(HARD_CODE_CELL_SIZE, HARD_CODE_CELL_SIZE)
        }
    }
}