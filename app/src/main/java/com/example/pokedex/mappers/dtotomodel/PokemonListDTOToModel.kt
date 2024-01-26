package com.example.pokedex.mappers.dtotomodel

import com.example.pokedex.data.sources.remote.dto.PokemonListDTO
import com.example.pokedex.data.sources.remote.dto.PokemonUrlDTO
import com.example.pokedex.domain.models.PokemonListModel
import com.example.pokedex.domain.models.PokemonUrlModel

fun mapPokemonListDTOToModel(pokemonListDTO: PokemonListDTO): PokemonListModel {
    return PokemonListModel(
        results = mapPokemonUrlDTOListToModelList(pokemonListDTO.results)
    )
}

fun mapPokemonUrlDTOListToModelList(pokemonUrlDTOList: List<PokemonUrlDTO>): List<PokemonUrlModel> {
    return pokemonUrlDTOList.map { mapPokemonUrlDTOToModel(it) }
}

fun mapPokemonUrlDTOToModel(pokemonUrlDTO: PokemonUrlDTO): PokemonUrlModel {
    return PokemonUrlModel(
        name = pokemonUrlDTO.name,
        url = pokemonUrlDTO.url
    )
}