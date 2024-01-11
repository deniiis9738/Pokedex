package com.example.pokedex.ui.utils

fun ColorStats(): HashMap<String, StatsColor> {
    return hashMapOf(
        "hp" to StatsColor.HP,
        "attack" to StatsColor.ATTACK,
        "special-attack" to StatsColor.SPECIAL_ATTACK,
        "defense" to StatsColor.DEFENSE,
        "special-defense" to StatsColor.SPECIAL_DEFENSE,
        "speed" to StatsColor.SPEED,
    )
}