package com.example.pokedex.ui.viewmodels

import com.example.pokedex.data.models.Pokemon
import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.repositories.InfoPokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class InfoPokemonViewModel(application: Application): AndroidViewModel(application) {
    private val infoPokemonRepository = InfoPokemonRepository(application)

    private var _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon> = _pokemon

    fun getPokemon(name: String) {
        viewModelScope.launch {
            val loadedPokemon = withContext(Dispatchers.IO) {
                infoPokemonRepository.getPokemon(name)
            }
            _pokemon.postValue(loadedPokemon)
        }
    }

    private val mapTypeColors = infoPokemonRepository.colorTypes()
    fun getTypeColor(type: String): Color? {
        val typeColor = mapTypeColors[type.lowercase(Locale.ROOT)]
        return typeColor?.color
    }

    private val mapStatsColors = infoPokemonRepository.colorStats()
    fun getStatsColor(stat: String): Color? {
        val statColor = mapStatsColors[stat.lowercase(Locale.ROOT)]
        return statColor?.color
    }
}