package tiler.interactor.getters

import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import javafx.scene.shape.Rectangle
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import tiler.model.Cell
import tiler.repository.CellRepository

@RunWith(MockitoJUnitRunner::class)
class GetCellsUCTest {

    @Mock
    lateinit var cellRepository: CellRepository


    var uc: GetCellsUC? = null

    @Before
    fun setUp() {
        uc = GetCellsUC(cellRepository)
    }

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
        uc?.cells()?.test()?.let {

            val observer: TestObserver<List<Cell>> = it

            // Then
            observer.assertValue(expected)
            observer.assertComplete()
        }
    }

}