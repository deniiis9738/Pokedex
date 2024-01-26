package com.example.pokedex.data.sources.remote.dto

import com.google.gson.annotations.SerializedName

data class PokemonListDTO(
    @SerializedName("results")
    val results: List<PokemonUrlDTO>
)

data class PokemonUrlDTO(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)