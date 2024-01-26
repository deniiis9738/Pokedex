package com.example.pokedex.mappers.dtotomodel

import com.example.pokedex.data.dto.OfficialArtworkDTO
import com.example.pokedex.data.dto.OtherDTO
import com.example.pokedex.data.dto.PokemonDTO
import com.example.pokedex.data.dto.SpritesDTO
import com.example.pokedex.data.dto.StatDTO
import com.example.pokedex.data.dto.StatInfoDTO
import com.example.pokedex.data.dto.TypeInfoDTO
import com.example.pokedex.data.dto.TypesDTO
import com.example.pokedex.domain.models.OfficialArtworkModel
import com.example.pokedex.domain.models.OtherModel
import com.example.pokedex.domain.models.PokemonModel
import com.example.pokedex.domain.models.SpritesModel
import com.example.pokedex.domain.models.StatInfoModel
import com.example.pokedex.domain.models.StatModel
import com.example.pokedex.domain.models.TypeInfoModel
import com.example.pokedex.domain.models.TypesModel

fun mapPokemonDTOToModel(pokemonDTO: PokemonDTO): PokemonModel {
    return PokemonModel(
        id = pokemonDTO.id,
        spritesModel = mapSpritesDTOToModel(pokemonDTO.spritesDTO),
        name = pokemonDTO.name,
        types = mapTypesDTOListToModelList(pokemonDTO.types),
        weight = pokemonDTO.weight,
        height = pokemonDTO.height,
        statModels = mapStatDTOListToModelList(pokemonDTO.statDTOS)
    )
}

fun mapSpritesDTOToModel(spritesDTO: SpritesDTO): SpritesModel {
    return SpritesModel(otherModel = mapOtherDTOToModel(spritesDTO.otherDTO))
}

fun mapOtherDTOToModel(otherDTO: OtherDTO): OtherModel {
    return OtherModel(officialArtworkModel = mapOfficialArtworkDTOToModel(otherDTO.officialArtworkDTO))
}

fun mapOfficialArtworkDTOToModel(officialArtworkDTO: OfficialArtworkDTO): OfficialArtworkModel {
    return OfficialArtworkModel(frontDefault = officialArtworkDTO.frontDefault)
}

fun mapTypesDTOListToModelList(typesDTOList: List<TypesDTO>): List<TypesModel> {
    return typesDTOList.map { mapTypesDTOToModel(it) }
}

fun mapTypesDTOToModel(typesDTO: TypesDTO): TypesModel {
    return TypesModel(slot = typesDTO.slot, type = mapTypeInfoDTOToModel(typesDTO.type))
}

fun mapTypeInfoDTOToModel(typeInfoDTO: TypeInfoDTO): TypeInfoModel {
    return TypeInfoModel(name = typeInfoDTO.name)
}

fun mapStatDTOListToModelList(statDTOList: List<StatDTO>): List<StatModel> {
    return statDTOList.map { mapStatDTOToModel(it) }
}

fun mapStatDTOToModel(statDTO: StatDTO): StatModel {
    return StatModel(baseStat = statDTO.baseStat, stat = mapStatInfoDTOToModel(statDTO.stat))
}

fun mapStatInfoDTOToModel(statInfoDTO: StatInfoDTO): StatInfoModel {
    return StatInfoModel(name = statInfoDTO.name)
}