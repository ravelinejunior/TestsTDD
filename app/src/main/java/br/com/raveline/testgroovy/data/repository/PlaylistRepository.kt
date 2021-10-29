package br.com.raveline.testgroovy.data.repository

import br.com.raveline.testgroovy.data.api.PlaylistService
import br.com.raveline.testgroovy.data.model.Playlists
import kotlinx.coroutines.flow.Flow

class PlaylistRepository(
    private val service: PlaylistService
) {
    suspend fun getPlaylists(): Flow<Result<Playlists>> = service.fetchPlayLists()

}