package com.runt9.fusionOfSouls.model.loot

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.runt9.fusionOfSouls.model.GameUnitEffect
import com.runt9.fusionOfSouls.model.loot.Rarity.COMMON
import com.runt9.fusionOfSouls.model.loot.Rarity.LEGENDARY
import com.runt9.fusionOfSouls.model.loot.Rarity.RARE
import com.runt9.fusionOfSouls.model.loot.Rarity.UNCOMMON
import com.runt9.fusionOfSouls.model.unit.GameUnit
import com.runt9.fusionOfSouls.service.generateModifiers
import com.runt9.fusionOfSouls.service.runState
import com.runt9.fusionOfSouls.util.rectPixmapTexture
import com.runt9.fusionOfSouls.util.smallTextTooltip
import ktx.scene2d.KGroup

private val Rarity.numRuneAttrs: Int
    get() = when(this) {
        COMMON -> 1
        UNCOMMON -> 2
        RARE, LEGENDARY -> 3
    }

// TODO: Active vs inactive
class Rune(rarity: Rarity) : GameUnitEffect, Container<Image>(), KGroup, Fusible {
    override val description by lazy { generateDescription() }
    private val modifiers = generateModifiers(rarity, rarity.numRuneAttrs)
    private val passives = if (rarity == LEGENDARY) listOf(randomLegendaryPassive()) else emptyList()
    var isActive = false

    init {
        actor = Image(rectPixmapTexture(25, 25, Color.BLUE))
        smallTextTooltip(description)
        addRightClickMenu()
    }

    override fun applyToUnit(unit: GameUnit) {
        modifiers.forEach { it.applyToUnit(unit) }
        passives.forEach { it.applyToUnit(unit) }
    }

    override fun removeFromUnit(unit: GameUnit) {
        modifiers.forEach { it.removeFromUnit(unit) }
        passives.forEach { it.removeFromUnit(unit) }
    }

    private fun generateDescription(): String {
        val sb = StringBuilder()
        modifiers.forEach {
            sb.append("${it.description}\n")
        }
        passives.forEach {
            sb.append("${it.description}\n")
        }
        return sb.toString()
    }

    override fun getFusibleEffects() = modifiers + passives

    override fun onFusionChosen(fusion: Fusion) {
        runState.hero.fuseRune(this, fusion)
    }
}

// TODO: Actual passive pool
fun randomLegendaryPassive() = DefaultPassive()
