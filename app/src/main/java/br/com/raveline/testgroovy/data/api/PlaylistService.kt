package br.com.raveline.testgroovy.data.api

import br.com.raveline.testgroovy.data.model.Playlists
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PlaylistService(
    private val api: PlaylistApi
) {
    suspend fun fetchPlayLists(): Flow<Result<Playlists>> {
        return flow {
            emit(Result.success(api.fetchAllPlaylists()))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }
}