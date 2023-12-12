package com.example.pokedex.data.repositories

import com.example.pokedex.data.models.Pokemon
import com.example.pokedex.data.models.PokemonList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonListRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val pokeApiService = retrofit.create(PokeApiService::class.java)

    suspend fun getPokemonList(): PokemonList {
        return pokeApiService.getPokemonList()
    }

    suspend fun getPokemon(name: String): Pokemon {
        return pokeApiService.getPokemonByName(name)
    }
}