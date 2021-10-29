package br.com.raveline.testgroovy.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.raveline.testgroovy.R
import br.com.raveline.testgroovy.data.model.Playlist
import br.com.raveline.testgroovy.data.model.Playlists
import br.com.raveline.testgroovy.data.util.MyDiffUtil
import br.com.raveline.testgroovy.databinding.FragmentPlaylistBinding
import br.com.raveline.testgroovy.databinding.ItemAdapterBinding

class PlaylistAdapter : RecyclerView.Adapter<PlaylistAdapter.MyViewHolder>() {

    var playlists = emptyList<Playlist>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val playlist = playlists[position]
        holder.bind(playlist)
    }

    override fun getItemCount(): Int = playlists.size

    class MyViewHolder(private val playlistBinding: ItemAdapterBinding) :
        RecyclerView.ViewHolder(playlistBinding.root) {

            fun bind(playlist: Playlist){
                playlistBinding.tvCategoryItemAdapter.text = playlist.category
                playlistBinding.tvTitleItemAdapter.text = playlist.name
            }

    }

    fun setData(newData:Playlists){
        val playlistDiffUtil = MyDiffUtil(playlists,newData)
        val diffUtilResult = DiffUtil.calculateDiff(playlistDiffUtil)
        playlists = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }


}