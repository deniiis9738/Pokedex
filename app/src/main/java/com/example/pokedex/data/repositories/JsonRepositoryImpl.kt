package com.example.pokedex.data.repositories

import android.app.Application
import com.example.pokedex.data.models.Pokemon
import com.example.pokedex.data.models.PokemonList
import com.example.pokedex.domain.repositories.IPokemonRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
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
        val jsonInputStream = application.assets.open("pokemon_list.json")
        val reader = BufferedReader(jsonInputStream.reader())
        val type = object : TypeToken<PokemonList>() {}.type
        return gson.fromJson(reader, type)
    }

    override suspend fun getAllPokemonNames(): List<String> {
        val jsonInputStream = application.assets.open("pokemon_list.json")
        val reader = BufferedReader(jsonInputStream.reader())
        val type = object : TypeToken<PokemonList>() {}.type
        val pokemonList = gson.fromJson<PokemonList>(reader, type)
        return pokemonList.results.map { it.name }
    }
}