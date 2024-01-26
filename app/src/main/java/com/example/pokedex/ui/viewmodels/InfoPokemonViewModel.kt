package com.example.pokedex.ui.viewmodels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.domain.models.PokemonModel
import com.example.pokedex.domain.usecases.InfoPokemonUseCase
import com.example.pokedex.ui.utils.ColorStats
import com.example.pokedex.ui.utils.ColorTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class InfoPokemonViewModel @Inject constructor (
    private val infoPokemonUseCase: InfoPokemonUseCase
): ViewModel() {

    private var _pokemonModel = MutableLiveData<PokemonModel>()
    val pokemonModel: LiveData<PokemonModel> = _pokemonModel

    fun getPokemon(name: String) {
        viewModelScope.launch {
            val loadedPokemon = withContext(Dispatchers.IO) {
                infoPokemonUseCase.getPokemonByName(name)
            }
            _pokemonModel.postValue(loadedPokemon)
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