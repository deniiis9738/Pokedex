package com.example.pokedex.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.models.Pokemon
import com.example.pokedex.data.repositories.ApiRepositoryImpl
import com.example.pokedex.data.repositories.JsonRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.RuntimeException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val apiRepositoryImpl: ApiRepositoryImpl,
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
                try {
                    val pokemonList = apiRepositoryImpl.getPokemonList(start, pageSize)
                    pokemonList.results.map { pokemon ->
                        apiRepositoryImpl.getPokemonByName(pokemon.name)
                    }   
                } catch (e: UnknownHostException) {
                    val pokemonList = jsonRepositoryImpl.getPokemonList(start, pageSize)
                    pokemonList.results.map { pokemon ->
                        jsonRepositoryImpl.getPokemonByName(pokemon.name)
                    }
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
        try {
            allPokemonNames = withContext(Dispatchers.IO) {
                apiRepositoryImpl.getAllPokemonNames()
            }
        } catch (e: Exception) {
            allPokemonNames = withContext(Dispatchers.IO) {
                jsonRepositoryImpl.getAllPokemonNames()
            }
        }
    }

    private var _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> = _pokemonList

    suspend fun filterPokemonListByName(searchText: String) {
        try {
            val filteredList = allPokemonNames
                .filter { it.startsWith(searchText, ignoreCase = true) }
                .take(10)
                .map { name ->
                    apiRepositoryImpl.getPokemonByName(name)
                }

            _pokemonList.value = filteredList
        } catch (e: Exception) {
            val start = currentPage * pageSize
            val loadedPokemonList = withContext(Dispatchers.IO) {
                val pokemonList = jsonRepositoryImpl.getPokemonList(start, pageSize)
                pokemonList.results.map { pokemon ->
                    jsonRepositoryImpl.getPokemonByName(pokemon.name)
                }
            }
            _pokemonListWithInfo.postValue(_pokemonListWithInfo.value.orEmpty() + loadedPokemonList)
            isLoading = false
            currentPage++
        }
    }

    fun performSearch(searchText: String) {
        viewModelScope.launch {
            filterPokemonListByName(searchText)
        }
    }
}