package tiler.ui.gallery

import io.reactivex.Observable.just
import io.reactivex.schedulers.Schedulers
import javafx.scene.shape.Rectangle
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import tiler.interactor.getters.GetCellsUC
import tiler.model.Cell
import tiler.ui.canvas.CanvasGrid
import tiler.ui.canvas.CanvasGrid.CanvasGridState
import tiler.ui.canvas.CanvasGridPresenter

@RunWith(MockitoJUnitRunner::class)
class CanvasGridPresenterTest {

    @Mock
    lateinit var getCellsUC: GetCellsUC
    @Mock
    lateinit var view: CanvasGrid

    var presenter: CanvasGridPresenter? = null

    @Before
    fun setUp() {
        presenter = CanvasGridPresenter(
                getCellsUC,
                Schedulers.trampoline(),
                Schedulers.trampoline()
        )
    }

    @Test
    fun start() {
        // Given
        val expected = listOf(
                Cell(Rectangle()),
                Cell(Rectangle())
        )
        given(getCellsUC.cells())
                .willReturn(just(expected))

        // When
        presenter?.start(view)

        // Then
        verify(view).updateState(CanvasGridState(expected))
    }
}