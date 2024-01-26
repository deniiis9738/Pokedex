package com.example.pokedex.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.domain.models.PokemonModel
import com.example.pokedex.domain.usecases.InfoPokemonUseCase
import com.example.pokedex.domain.usecases.PokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonListUseCase: PokemonListUseCase,
    private val infoPokemonUseCase: InfoPokemonUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            getAllPokemonNames()
            getPokemonList() {}
        }
    }

    private var _pokemonModelListWithInfo = MutableLiveData<List<PokemonModel>>()
    val pokemonModelListWithInfo: LiveData<List<PokemonModel>> = _pokemonModelListWithInfo

    private var currentPage = 0
    private var isLoading = false
    private val pageSize = 20

    fun getPokemonList(onLoadComplete: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            val start = currentPage * pageSize
            val loadedPokemonList = withContext(Dispatchers.IO) {
                val pokemonList = pokemonListUseCase.getPokemonList(start, pageSize)
                pokemonList.results.map { pokemon ->
                    infoPokemonUseCase.getPokemonByName(pokemon.name)
                }
            }
            _pokemonModelListWithInfo.postValue(_pokemonModelListWithInfo.value.orEmpty() + loadedPokemonList)
            isLoading = false
            currentPage++
            onLoadComplete()
        }
    }

    private var allPokemonNames: List<String> = emptyList()

    private suspend fun getAllPokemonNames() {
        allPokemonNames = withContext(Dispatchers.IO) {
            pokemonListUseCase.getAllPokemonNames()
        }
    }

    private var _pokemonModelFilteredList = MutableLiveData<List<PokemonModel>>()
    val pokemonModelFilteredList: LiveData<List<PokemonModel>> = _pokemonModelFilteredList

    suspend fun filterPokemonListByName(searchText: String) {
        val filteredList = allPokemonNames
            .filter { it.startsWith(searchText, ignoreCase = true) }
            .take(10)
            .map { name ->
                infoPokemonUseCase.getPokemonByName(name)
            }

        _pokemonModelFilteredList.value = filteredList
    }

    fun performSearch(searchText: String) {
        viewModelScope.launch {
            filterPokemonListByName(searchText)
        }
    }
}