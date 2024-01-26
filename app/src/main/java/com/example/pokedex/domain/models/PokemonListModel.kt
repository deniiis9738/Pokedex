package com.example.pokedex.domain.models

data class PokemonListModel(
    val results: List<PokemonUrlModel>
)

data class PokemonUrlModel(
    val name: String,
    val url: String
)