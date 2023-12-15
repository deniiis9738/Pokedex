package com.example.pokedex.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.models.Pokemon
import com.example.pokedex.data.repositories.InfoPokemonRepository
import com.example.pokedex.data.repositories.PokemonListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonListViewModel: ViewModel() {
    private val pokemonListRepository = PokemonListRepository()
    private val infoPokemonRepository = InfoPokemonRepository(Application())

    init {
        viewModelScope.launch {
            getPokemonList(){}
        }
    }

    private var _pokemonListWithInfo = MutableLiveData<List<Pokemon>>()
    val pokemonListWithInfo: LiveData<List<Pokemon>> = _pokemonListWithInfo

    private var currentPage = 0
    private var isLoading = false
    private val pageSize = 20

    fun onListEndReached(onLoadComplete: () -> Unit) {
        if (!isLoading) {
            getPokemonList(onLoadComplete)
        }
    }

    private fun getPokemonList(onLoadComplete: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            val start = currentPage * pageSize
            val loadedPokemonList = withContext(Dispatchers.IO) {
                val pokemonList = pokemonListRepository.getPokemonList(start, pageSize)
                pokemonList.results.map { pokemon ->
                    infoPokemonRepository.getPokemon(pokemon.name)
                }
            }
            _pokemonListWithInfo.postValue(_pokemonListWithInfo.value.orEmpty() + loadedPokemonList)
            isLoading = false
            currentPage++
            onLoadComplete()
        }
    }

}