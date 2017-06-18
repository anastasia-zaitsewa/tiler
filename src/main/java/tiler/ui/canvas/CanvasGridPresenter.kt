package tiler.ui.canvas

import io.reactivex.Scheduler
import tiler.interactor.getters.GetCellsUC
import tiler.ui.canvas.CanvasGrid.CanvasGridState

/**
 * Presents grid of cells
 */
class CanvasGridPresenter(val getCellsUC: GetCellsUC,
                          val mainScheduler: Scheduler) {
    fun start(view: CanvasGrid) {
        getCellsUC.cells()
                .map { CanvasGridState(it) }
                .observeOn(mainScheduler)
                .subscribe { view.updateState(it) }
    }
}