package com.example.pokedex.data.repositories

import android.app.Application
import com.example.pokedex.data.models.Pokemon
import com.example.pokedex.data.models.PokemonList
import com.example.pokedex.domain.repositories.IPokemonRepository
import com.google.gson.Gson
import javax.inject.Inject

class JsonRepositoryImpl @Inject constructor(
    private val application: Application
): IPokemonRepository {
    private val gson = Gson()

    override suspend fun getPokemonByName(name: String): Pokemon {
        val jsonInputStream = application.assets.open("$name.json")
        return gson.fromJson(jsonInputStream.reader(), Pokemon::class.java)
    }

    override suspend fun getPokemonList(offset: Int, limit: Int): PokemonList {
        TODO("Not yet implemented")
    }

    override suspend fun getAllPokemonNames(): List<String> {
        TODO("Not yet implemented")
    }
}