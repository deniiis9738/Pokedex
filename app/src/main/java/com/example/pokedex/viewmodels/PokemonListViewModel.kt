package com.example.pokedex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.models.Pokemon
import com.example.pokedex.data.models.PokemonList
import com.example.pokedex.data.repositories.PokemonListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonListViewModel: ViewModel() {
    private val pokemonListRepository = PokemonListRepository()

    init {
        viewModelScope.launch {
            getPokemonList()
        }
    }

    private var _pokemonList = MutableLiveData<PokemonList>()
    val pokemonList: LiveData<PokemonList> = _pokemonList

    private fun getPokemonList() {
        viewModelScope.launch {
            val loadedPokemon = withContext(Dispatchers.IO) {
                pokemonListRepository.getPokemonList()
            }
            _pokemonList.postValue(loadedPokemon)
        }
    }
}