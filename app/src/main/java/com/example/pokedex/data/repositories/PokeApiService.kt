package com.example.pokedex.data.repositories

import com.example.pokedex.data.models.Pokemon
import com.example.pokedex.data.models.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {
    @GET("pokemon/{name}/")
    suspend fun getPokemonByName(@Path("name") name: String): Pokemon

    @GET("pokemon/?limit=10")
    suspend fun getPokemonList(): PokemonList
}
