package br.com.raveline.testgroovy.presentation.ui.viewmodel

import androidx.lifecycle.*
import br.com.raveline.testgroovy.data.model.Playlists
import br.com.raveline.testgroovy.data.repository.PlaylistRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val repository: PlaylistRepository
) : ViewModel() {
    val playlistMutableLiveData = liveData {
        emitSource(repository.getPlaylists().asLiveData())
    }

}