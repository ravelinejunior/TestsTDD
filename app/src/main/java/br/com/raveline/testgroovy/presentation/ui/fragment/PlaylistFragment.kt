package br.com.raveline.testgroovy.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.raveline.testgroovy.data.api.PlaylistApi
import br.com.raveline.testgroovy.data.api.PlaylistService
import br.com.raveline.testgroovy.data.repository.PlaylistRepository
import br.com.raveline.testgroovy.databinding.FragmentPlaylistBinding
import br.com.raveline.testgroovy.presentation.ui.adapter.PlaylistAdapter
import br.com.raveline.testgroovy.presentation.ui.viewmodel.PlaylistViewModel
import br.com.raveline.testgroovy.presentation.ui.viewmodel.PlaylistViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlaylistFragment : Fragment() {

    private var _binding: FragmentPlaylistBinding? = null
    private val mBinding get() = _binding!!

    private lateinit var playlistViewModel: PlaylistViewModel
    private lateinit var viewModelFactory: PlaylistViewModelFactory

    private val playListAdapter = PlaylistAdapter()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3001/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(PlaylistApi::class.java)

    private val service: PlaylistService = PlaylistService(api)
    private val repository = PlaylistRepository(service)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelFactory = PlaylistViewModelFactory(repository)
        playlistViewModel = ViewModelProvider(this, viewModelFactory)[PlaylistViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistBinding.inflate(inflater, container, false)



        lifecycleScope.launch {
            Log.i("TAGL", api.fetchAllPlaylists().toString())
        }

        playlistViewModel.playlistMutableLiveData.observe(viewLifecycleOwner, { playlists ->
            if (playlists.getOrNull() != null) {
                lifecycleScope.launch {
                    playListAdapter.setData(api.fetchAllPlaylists())
                    setupRecyclerView()
                }
            } else {

            }
        })


        return mBinding.root
    }

    private fun setupRecyclerView() {
        mBinding.recyclerViewFragmentPlaylist.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = playListAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}