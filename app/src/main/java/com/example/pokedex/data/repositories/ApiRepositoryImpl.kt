package com.example.pokedex.data.repositories

import com.example.pokedex.data.models.Pokemon
import com.example.pokedex.data.models.PokemonList
import com.example.pokedex.domain.repositories.IPokemonRepository
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    val pokeApiService: PokeApiService
): IPokemonRepository {

    override suspend fun getPokemonByName(name: String): Pokemon {
        return pokeApiService.getPokemonByName(name)
    }

    override suspend fun getPokemonList(offset: Int, limit: Int): PokemonList {
        return pokeApiService.getPokemonList(limit = limit, offset = offset)
    }

    override suspend fun getAllPokemonNames(): List<String> {
        return pokeApiService.getAllPokemonNames().results.map { it.name }
    }
}