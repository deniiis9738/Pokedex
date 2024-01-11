package com.example.pokedex.data.repositories

import com.example.pokedex.data.models.Pokemon
import com.example.pokedex.data.models.PokemonList
import com.example.pokedex.domain.repositories.IPokemonRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class ApìRepositoryImpl @Inject constructor(): IPokemonRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val pokeApiService = retrofit.create(PokeApiService::class.java)

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