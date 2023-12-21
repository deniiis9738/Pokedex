package com.example.pokedex.data.repositories

import com.example.pokedex.data.models.Pokemon
import android.app.Application
import com.example.pokedex.data.models.StatsColor
import com.example.pokedex.data.models.TypeColor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class InfoPokemonRepository @Inject constructor(
    private val application: Application)
{
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val pokeApiService = retrofit.create(PokeApiService::class.java)

    suspend fun getPokemon(name: String): Pokemon {
        return pokeApiService.getPokemonByName(name)
    }

    //Read pokemon from a json
//    fun getPokemon(): Pokemon {
//        val gson = Gson()
//        val jsonInputStream = application.assets.open("rayquaza.json")
//
//        return gson.fromJson(jsonInputStream.reader(), Pokemon::class.java)
//    }

    fun colorTypes(): HashMap<String, TypeColor> {
        return hashMapOf(
            "normal" to TypeColor.NORMAL,
            "fire" to TypeColor.FIRE,
            "water" to TypeColor.WATER,
            "electric" to TypeColor.ELECTRIC,
            "grass" to TypeColor.GRASS,
            "ice" to TypeColor.ICE,
            "fighting" to TypeColor.FIGHTING,
            "poison" to TypeColor.POISON,
            "ground" to TypeColor.GROUND,
            "flying" to TypeColor.FLYING,
            "psychic" to TypeColor.PSYCHIC,
            "bug" to TypeColor.BUG,
            "rock" to TypeColor.ROCK,
            "ghost" to TypeColor.GHOST,
            "dragon" to TypeColor.DRAGON,
            "dark" to TypeColor.DARK,
            "steel" to TypeColor.STEEL,
            "fairy" to TypeColor.FAIRY
        )
    }

    fun colorStats(): HashMap<String, StatsColor> {
        return hashMapOf(
            "hp" to StatsColor.HP,
            "attack" to StatsColor.ATTACK,
            "special-attack" to StatsColor.SPECIAL_ATTACK,
            "defense" to StatsColor.DEFENSE,
            "special-defense" to StatsColor.SPECIAL_DEFENSE,
            "speed" to StatsColor.SPEED,
        )
    }
}