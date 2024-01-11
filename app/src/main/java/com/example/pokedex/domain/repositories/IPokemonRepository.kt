package com.example.pokedex.domain.repositories

import com.example.pokedex.data.models.Pokemon
import com.example.pokedex.data.models.PokemonList

interface IPokemonRepository {
    suspend fun getPokemonByName(name: String): Pokemon

    suspend fun getPokemonList(offset: Int, limit: Int): PokemonList

    suspend fun getAllPokemonNames(): List<String>
}