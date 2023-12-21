package com.example.pokedex.data.repositories

import android.app.Application
import com.example.pokedex.data.models.Pokemon
import com.example.pokedex.domain.repositories.IPokemonRepository
import com.google.gson.Gson
import javax.inject.Inject

class PokemonFromJsonRepositoryImpl @Inject constructor(
    private val application: Application
): IPokemonRepository {
    override fun getPokemonByName(): Pokemon {
        val gson = Gson()
        val jsonInputStream = application.assets.open("ditto.json")

        return gson.fromJson(jsonInputStream.reader(), Pokemon::class.java)
    }

    override fun getPokemonList() {
        TODO("Not yet implemented")
    }
}