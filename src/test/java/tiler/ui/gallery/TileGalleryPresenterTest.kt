package tiler.ui.gallery

import io.reactivex.Observable.just
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import tiler.interactor.getters.GetTilesFromFolderUC
import tiler.model.SourceImage
import tiler.model.Tile

@RunWith(MockitoJUnitRunner::class)
class TileGalleryPresenterTest {

    @Mock
    lateinit var getTilesFromFolderUC: GetTilesFromFolderUC
    @Mock
    lateinit var sourceImage: SourceImage
    @Mock
    lateinit var view: TileGalleryView
    @InjectMocks
    lateinit var presenter: TileGalleryPresenter

    @Test
    fun start() {
        // Given
        val expected = listOf(
                Tile(sourceImage, "tile1"),
                Tile(sourceImage, "tile2")
        )
        given(getTilesFromFolderUC.getAll(anyString()))
                .willReturn(just(expected))

        // When
        presenter.start(view)

        // Then
        verify(view).updateState(TileGalleryView.TileGalleryState(expected))
    }
}