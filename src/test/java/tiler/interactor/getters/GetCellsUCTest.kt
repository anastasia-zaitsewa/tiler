package tiler.interactor.getters

import io.reactivex.Single
import io.reactivex.observers.TestObserver
import javafx.scene.shape.Rectangle
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import tiler.model.Cell
import tiler.model.Tile
import tiler.repository.CellRepository

@RunWith(MockitoJUnitRunner::class)
class GetCellsUCTest {

    @Mock
    lateinit var cellRepository: CellRepository

    @InjectMocks
    lateinit var uc: GetCellsUC

    @Test
    fun getAll() {
        // Given
        val cell1 = Cell(Rectangle())
        val cell2 = Cell(Rectangle())
        val cells = listOf(
                cell1,
                cell2
        )

        val expected = listOf(
                cell1,
                cell2
        )

        given(cellRepository.cells())
                .willReturn(Single.just(cells))

        // When
        val observer: TestObserver<List<Cell>> = uc.cells().test()

        // Then
        observer.assertValue(expected)
        observer.assertComplete()
    }

}