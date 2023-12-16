package com.example.pokedex.data.repositories

import com.example.pokedex.data.models.Pokemon
import com.example.pokedex.data.models.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {
    @GET("pokemon/{name}/")
    suspend fun getPokemonByName(@Path("name") name: String): Pokemon

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonList

    @GET("pokemon?limit=1300")
    suspend fun getAllPokemonNames(): PokemonList
}