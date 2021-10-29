package br.com.raveline.testgroovy.playlist

import br.com.raveline.testgroovy.data.model.Playlists
import br.com.raveline.testgroovy.data.repository.PlaylistRepository
import br.com.raveline.testgroovy.presentation.ui.viewmodel.PlaylistViewModel
import br.com.raveline.testgroovy.utils.BaseUnitTest
import br.com.raveline.testgroovy.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class PlaylistViewModelShould : BaseUnitTest() {

    private val repository: PlaylistRepository = mock()

    private val playlists = mock<Playlists>()
    private val expected = Result.success(playlists)

    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getPlaylistsFromRepository() = runBlockingTest {

        val playlistViewModel = mockSuccessfulCase()
        playlistViewModel.playlistMutableLiveData.getValueForTest()
        verify(repository, times(1)).getPlaylists()
    }

    @Test
    fun emitPlaylistsFromRepository() = runBlockingTest {
        val playlistViewModel = mockSuccessfulCase()
        assertEquals(expected, playlistViewModel.playlistMutableLiveData.getValueForTest())
    }

    @Test
    fun emitErrorWhenReceiveError(){
        runBlocking {
            whenever(repository.getPlaylists()).thenReturn(
                flow {
                    emit(Result.failure<Playlists>(exception))
                }
            )
        }
        val viewModel = PlaylistViewModel(repository)
        assertEquals(exception,viewModel.playlistMutableLiveData.getValueForTest()?.exceptionOrNull())
//        assertEquals(RuntimeException("Something went really wrong"),viewModel.playlistMutableLiveData.getValueForTest()?.exceptionOrNull())
    }

    private fun TestCoroutineScope.mockSuccessfulCase(): PlaylistViewModel {
        runBlockingTest {
            whenever(repository.getPlaylists()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }
        return PlaylistViewModel(repository)
    }
}