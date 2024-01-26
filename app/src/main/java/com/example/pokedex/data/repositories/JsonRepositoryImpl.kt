package com.example.pokedex.data.repositories

import android.app.Application
import com.example.pokedex.data.dto.PokemonListDTO
import com.example.pokedex.domain.models.PokemonListModel
import com.example.pokedex.domain.models.PokemonModel
import com.example.pokedex.domain.repositories.IPokemonRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import javax.inject.Inject

class JsonRepositoryImpl @Inject constructor(
    private val application: Application
): IPokemonRepository {
    private val gson = Gson()

    override suspend fun getPokemonByName(name: String): PokemonModel {
        val jsonInputStream = application.assets.open("$name.json")
        return gson.fromJson(jsonInputStream.reader(), PokemonModel::class.java)
    }

    override suspend fun getPokemonList(offset: Int, limit: Int): PokemonListModel {
        val jsonInputStream = application.assets.open("pokemon_list.json")
        val reader = BufferedReader(jsonInputStream.reader())

        try {
            val jsonString = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                jsonString.append(line)
            }

            val type = object : TypeToken<PokemonListModel>() {}.type
            val pokemonListModel = gson.fromJson<PokemonListModel>(jsonString.toString(), type)

            val startIndex = offset.coerceAtLeast(0)
            val endIndex = (offset + limit).coerceAtMost(pokemonListModel.results.size)
            val trimmedResults = pokemonListModel.results.subList(startIndex, endIndex)

            return PokemonListModel(trimmedResults)
        } finally {
            reader.close()
        }
    }


    override suspend fun getAllPokemonNames(): List<String> {
        val jsonInputStream = application.assets.open("pokemon_list.json")
        val reader = BufferedReader(jsonInputStream.reader())
        val type = object : TypeToken<PokemonListDTO>() {}.type
        val pokemonListDTO = gson.fromJson<PokemonListDTO>(reader, type)
        return pokemonListDTO.results.map { it.name }
    }
}