package com.example.pokedex.domain.models

data class PokemonModel(
    val id: String,
    val spritesModel: SpritesModel,
    val name: String,
    val types: List<TypesModel>,
    val weight: Float,
    val height: Float,
    val statModels: List<StatModel>
)

data class SpritesModel(
    val otherModel: OtherModel
)

data class OtherModel(
    val officialArtworkModel: OfficialArtworkModel
)

data class OfficialArtworkModel(
    val frontDefault: String
)

data class TypesModel(
    val slot: Int,
    val type: TypeInfoModel
)

data class TypeInfoModel(
    val name: String
)

data class StatModel(
    val baseStat: Int,
    val stat: StatInfoModel
)

data class StatInfoModel(
    val name: String
)