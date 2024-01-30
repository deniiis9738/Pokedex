package com.example.pokedex.data.repositories

import com.example.pokedex.domain.models.PokemonListModel
import com.example.pokedex.domain.models.PokemonModel
import com.example.pokedex.domain.repositories.IPokemonRepository
import javax.inject.Inject

class FallBackRepositoryImpl @Inject constructor(
    val apiRepositoryImpl: ApiRepositoryImpl,
    val jsonRepositoryImpl: JsonRepositoryImpl
): IPokemonRepository {
    override suspend fun getPokemonByName(name: String): PokemonModel? {
        return try {
            apiRepositoryImpl.getPokemonByName(name)
        }catch (e: Exception) {
            return jsonRepositoryImpl.getPokemonByName(name)
        }
    }

    override suspend fun getPokemonList(offset: Int, limit: Int): PokemonListModel {
        return try {
            apiRepositoryImpl.getPokemonList(offset, limit)
        }catch (e: Exception) {
            jsonRepositoryImpl.getPokemonList(offset, limit)
        }
    }

    override suspend fun getAllPokemonNames(): List<String> {
        return try {
            apiRepositoryImpl.getAllPokemonNames()
        }catch (e: Exception) {
            jsonRepositoryImpl.getAllPokemonNames()
        }
    }

}