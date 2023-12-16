package com.example.pokedex.data.repositories

import com.example.pokedex.data.models.PokemonList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonListRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val pokeApiService = retrofit.create(PokeApiService::class.java)

    suspend fun getPokemonList(offset: Int, limit: Int): PokemonList {
        return pokeApiService.getPokemonList(limit = limit, offset = offset)
    }

    suspend fun getAllPokemonNames(): List<String> {
        return pokeApiService.getAllPokemonNames().results.map { it.name }
    }
}