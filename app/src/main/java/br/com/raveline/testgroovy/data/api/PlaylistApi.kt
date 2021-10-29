package br.com.raveline.testgroovy.data.api

import br.com.raveline.testgroovy.data.model.Playlists
import retrofit2.http.GET

interface PlaylistApi {
    @GET("playlists")
    suspend fun fetchAllPlaylists(): Playlists {
        return Playlists()
    }
}