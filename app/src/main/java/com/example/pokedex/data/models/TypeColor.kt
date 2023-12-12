package com.example.pokedex.data.models

import androidx.compose.ui.graphics.Color
import com.example.pokedex.ui.theme.bugColor
import com.example.pokedex.ui.theme.darkColor
import com.example.pokedex.ui.theme.dragonColor
import com.example.pokedex.ui.theme.electricColor
import com.example.pokedex.ui.theme.fairyColor
import com.example.pokedex.ui.theme.fightingColor
import com.example.pokedex.ui.theme.fireColor
import com.example.pokedex.ui.theme.flyingColor
import com.example.pokedex.ui.theme.ghostColor
import com.example.pokedex.ui.theme.grassColor
import com.example.pokedex.ui.theme.groundColor
import com.example.pokedex.ui.theme.iceColor
import com.example.pokedex.ui.theme.normalColor
import com.example.pokedex.ui.theme.poisonColor
import com.example.pokedex.ui.theme.psychicColor
import com.example.pokedex.ui.theme.rockColor
import com.example.pokedex.ui.theme.steelColor
import com.example.pokedex.ui.theme.waterColor

enum class TypeColor (val color: Color){
    NORMAL(normalColor),
    FIGHTING(fightingColor),
    FLYING(flyingColor),
    POISON(poisonColor),
    GROUND(groundColor),
    ROCK(rockColor),
    BUG(bugColor),
    GHOST(ghostColor),
    STEEL(steelColor),
    FIRE(fireColor),
    WATER(waterColor),
    GRASS(grassColor),
    ELECTRIC(electricColor),
    PSYCHIC(psychicColor),
    ICE(iceColor),
    DRAGON(dragonColor),
    DARK(darkColor),
    FAIRY(fairyColor)
}