package com.example.pokedex.ui.viewmodels

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
            getAllPokemonNames()
            getPokemonList() {}
        }
    }

    private var _pokemonListWithInfo = MutableLiveData<List<Pokemon>>()
    val pokemonListWithInfo: LiveData<List<Pokemon>> = _pokemonListWithInfo

    private var currentPage = 0
    private var isLoading = false
    private val pageSize = 20

    fun getPokemonList(onLoadComplete: () -> Unit) {
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

    private var allPokemonNames: List<String> = emptyList()

    private suspend fun getAllPokemonNames() {
        allPokemonNames = withContext(Dispatchers.IO) {
            pokemonListRepository.getAllPokemonNames()
        }
    }

    private var _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> = _pokemonList

    suspend fun filterPokemonListByName(searchText: String) {
        val filteredList = allPokemonNames
            .filter { it.startsWith(searchText, ignoreCase = true) }
            .take(10)
            .map { name ->
                infoPokemonRepository.getPokemon(name)
            }

        _pokemonList.value = filteredList
    }

    fun performSearch(searchText: String) {
        viewModelScope.launch {
            filterPokemonListByName(searchText)
        }
    }
}