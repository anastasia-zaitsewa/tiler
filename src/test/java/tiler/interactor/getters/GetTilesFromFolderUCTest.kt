package tiler.interactor.getters

import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.anyString
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import tiler.model.FileSourceImageUrl
import tiler.model.Tile
import tiler.repository.FileRepository
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class GetTilesFromFolderUCTest {

    @Mock
    lateinit var fileRepository: FileRepository

    @InjectMocks
    lateinit var uc: GetTilesFromFolderUC

    @Test
    fun getAll() {
        // Given
        val files = listOf(
                File("path/name1"),
                File("path/name2")
        )

        val expected = listOf(
                Tile(FileSourceImageUrl("path/name1"), "name1"),
                Tile(FileSourceImageUrl("path/name2"), "name2")

        )

        given(fileRepository.listFiles(anyString()))
                .willReturn(Single.just(files))

        // When
        val observer: TestObserver<List<Tile>> = uc.getAll("folderPath").test()

        // Then
        observer.assertValue(expected)
        observer.assertComplete()
    }

}