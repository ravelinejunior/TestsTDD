package br.com.raveline.testgroovy.playlist

import br.com.raveline.testgroovy.data.api.PlaylistApi
import br.com.raveline.testgroovy.data.api.PlaylistService
import br.com.raveline.testgroovy.data.model.Playlists
import br.com.raveline.testgroovy.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class PlaylistServiceShould : BaseUnitTest() {
    private lateinit var service: PlaylistService
    private val api: PlaylistApi = mock()
    private val playlists: Playlists = mock()

    @Test
    fun fetchPlaylistsFromApi() = runBlockingTest {
        service = PlaylistService(api)
        service.fetchPlayLists().first()

        verify(api, times(1)).fetchAllPlaylists()
    }

    @Test
    fun convertValuesToFlowResultAndEmitsThem() = runBlockingTest {
        whenever(api.fetchAllPlaylists()).thenReturn(playlists)

        service = PlaylistService(api)

        assertEquals(Result.success(playlists), service.fetchPlayLists().first())
    }

    @Test
    fun emitsErrorResultWhenNetworkFails() = runBlockingTest {
        mockErrorCase()

        assertEquals(
            "Something went wrong",
            service.fetchPlayLists().first().exceptionOrNull()?.message
        )
    }

    private suspend fun mockErrorCase() {
        whenever(api.fetchAllPlaylists()).thenThrow(RuntimeException("Damn backend developers"))

        service = PlaylistService(api)
    }


}