package com.example.pokedex.data.models

import com.google.gson.annotations.SerializedName

data class PokemonList(
    @SerializedName("results")
    val results: List<PokemonUrl>
)

data class PokemonUrl(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)