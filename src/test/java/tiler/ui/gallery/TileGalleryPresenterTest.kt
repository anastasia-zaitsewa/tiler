package tiler.ui.gallery

import io.reactivex.Observable.just
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import tiler.interactor.getters.GetTilesFromFolderUC
import tiler.model.SourceImageUrl
import tiler.model.Tile

@RunWith(MockitoJUnitRunner::class)
class TileGalleryPresenterTest {

    @Mock
    lateinit var getTilesFromFolderUC: GetTilesFromFolderUC
    @Mock
    lateinit var sourceImageUrl: SourceImageUrl
    @Mock
    lateinit var view: TileGalleryView

    var presenter: TileGalleryPresenter? = null

    @Before
    fun setUp() {
        presenter = TileGalleryPresenter(
                getTilesFromFolderUC,
                Schedulers.trampoline()
        )
    }
    @Test
    fun start() {
        // Given
        val expected = listOf(
                Tile(sourceImageUrl, "tile1"),
                Tile(sourceImageUrl, "tile2")
        )
        given(getTilesFromFolderUC.getAll(anyString()))
                .willReturn(just(expected))

        // When
        presenter?.start(view)

        // Then
        verify(view).updateState(TileGalleryView.TileGalleryState(expected))
    }
}