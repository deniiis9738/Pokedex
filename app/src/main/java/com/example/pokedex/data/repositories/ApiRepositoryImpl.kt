package com.example.pokedex.data.repositories

import com.example.pokedex.domain.models.PokemonListModel
import com.example.pokedex.domain.models.PokemonModel
import com.example.pokedex.domain.repositories.IPokemonRepository
import com.example.pokedex.mappers.dtotomodel.mapPokemonDTOToModel
import com.example.pokedex.mappers.dtotomodel.mapPokemonListDTOToModel
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    val pokeApiService: PokeApiService
): IPokemonRepository {

    override suspend fun getPokemonByName(name: String): PokemonModel {
        val pokemonDTO = pokeApiService.getPokemonByName(name)
        return mapPokemonDTOToModel(pokemonDTO)
    }

    override suspend fun getPokemonList(offset: Int, limit: Int): PokemonListModel {
        val pokemonListDTO = pokeApiService.getPokemonList(limit = limit, offset = offset)
        return mapPokemonListDTOToModel(pokemonListDTO)
    }

    override suspend fun getAllPokemonNames(): List<String> {
        return pokeApiService.getAllPokemonNames().results.map { it.name }
    }
}