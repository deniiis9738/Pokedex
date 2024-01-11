package com.example.pokedex.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.models.Pokemon
import com.example.pokedex.data.repositories.ApìRepositoryImpl
import com.example.pokedex.data.repositories.JsonRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val apìRepositoryImpl: ApìRepositoryImpl,
    private val jsonRepositoryImpl: JsonRepositoryImpl
) : ViewModel() {

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
                val pokemonList = apìRepositoryImpl.getPokemonList(start, pageSize)
                pokemonList.results.map { pokemon ->
                    apìRepositoryImpl.getPokemonByName(pokemon.name)
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
            apìRepositoryImpl.getAllPokemonNames()
        }
    }

    private var _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> = _pokemonList

    suspend fun filterPokemonListByName(searchText: String) {
        val filteredList = allPokemonNames
            .filter { it.startsWith(searchText, ignoreCase = true) }
            .take(10)
            .map { name ->
                apìRepositoryImpl.getPokemonByName(name)
            }

        _pokemonList.value = filteredList
    }

    fun performSearch(searchText: String) {
        viewModelScope.launch {
            filterPokemonListByName(searchText)
        }
    }
}