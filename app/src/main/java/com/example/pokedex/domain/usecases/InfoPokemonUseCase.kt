package com.example.pokedex.domain.usecases

import com.example.pokedex.domain.models.PokemonModel
import com.example.pokedex.domain.repositories.IPokemonRepository
import javax.inject.Inject

class InfoPokemonUseCase @Inject constructor(
    private val repository: IPokemonRepository
) {
    suspend fun getPokemonByName(name: String): PokemonModel {
        return repository.getPokemonByName(name)
    }
}