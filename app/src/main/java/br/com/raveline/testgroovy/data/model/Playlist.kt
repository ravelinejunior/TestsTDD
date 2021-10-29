package br.com.raveline.testgroovy.data.model


import com.google.gson.annotations.SerializedName

data class Playlist(
    @SerializedName("category")
    val category: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    val imageId:Int
)