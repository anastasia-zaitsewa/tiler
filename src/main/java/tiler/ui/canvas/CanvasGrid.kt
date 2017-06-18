package tiler.ui.canvas

import tiler.model.Cell

/**
 * Shows grid with empty [Cell]s
 */
interface CanvasGrid {

    fun updateState(state: CanvasGridState)

    data class CanvasGridState(
            val cells: List<Cell>,
            val gridSize: Int,
            val cellSize: Double)
}