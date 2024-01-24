package com.example.pokedex.ui.viewmodels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.models.Pokemon
import com.example.pokedex.data.repositories.ApiRepositoryImpl
import com.example.pokedex.data.repositories.JsonRepositoryImpl
import com.example.pokedex.ui.utils.ColorStats
import com.example.pokedex.ui.utils.ColorTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class InfoPokemonViewModel @Inject constructor (
    val apiRepositoryImpl: ApiRepositoryImpl,
    val jsonRepositoryImpl: JsonRepositoryImpl
): ViewModel() {

    private var _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon> = _pokemon

    fun getPokemon(name: String) {
        viewModelScope.launch {
            try {
                val loadedPokemon = withContext(Dispatchers.IO) {
                    apiRepositoryImpl.getPokemonByName(name)
                }
                _pokemon.postValue(loadedPokemon)
            } catch (e: UnknownHostException) {
                val loadedPokemon = withContext(Dispatchers.IO) {
                    jsonRepositoryImpl.getPokemonByName(name)
                }
                _pokemon.postValue(loadedPokemon)
            }
        }
    }

    private val mapTypeColors = ColorTypes()
    fun getTypeColor(type: String): Color? {
        val typeColor = mapTypeColors[type.lowercase(Locale.ROOT)]
        return typeColor?.color
    }

    private val mapStatsColors = ColorStats()
    fun getStatsColor(stat: String): Color? {
        val statColor = mapStatsColors[stat.lowercase(Locale.ROOT)]
        return statColor?.color
    }
}