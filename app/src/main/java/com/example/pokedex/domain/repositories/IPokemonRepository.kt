package com.example.pokedex.domain.repositories

import com.example.pokedex.data.models.Pokemon

interface IPokemonRepository {
    fun getPokemonByName(): Pokemon

    fun getPokemonList()
}