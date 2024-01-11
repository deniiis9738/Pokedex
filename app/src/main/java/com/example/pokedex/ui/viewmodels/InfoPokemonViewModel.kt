package com.example.pokedex.ui.viewmodels

import com.example.pokedex.data.models.Pokemon
import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.repositories.ApìRepositoryImpl
import com.example.pokedex.data.repositories.JsonRepositoryImpl
import com.example.pokedex.ui.utils.ColorStats
import com.example.pokedex.ui.utils.ColorTypes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

class InfoPokemonViewModel @Inject constructor (
    application: Application,
): AndroidViewModel(application) {
    private val jsonRepositoryImpl = JsonRepositoryImpl(application)
    private val apìRepositoryImpl = ApìRepositoryImpl()

    private var _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon> = _pokemon

    fun getPokemon(name: String) {
        viewModelScope.launch {
            val loadedPokemon = withContext(Dispatchers.IO) {
                apìRepositoryImpl.getPokemonByName(name)
            }
            _pokemon.postValue(loadedPokemon)
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