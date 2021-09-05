package com.runt9.fusionOfSouls.service

import com.runt9.fusionOfSouls.gridWidth
import com.runt9.fusionOfSouls.model.unit.GameUnit
import com.runt9.fusionOfSouls.model.unit.Team
import com.runt9.fusionOfSouls.model.unit.`class`.TankClass
import com.runt9.fusionOfSouls.model.unit.attribute.AttributeModifier
import com.runt9.fusionOfSouls.model.unit.skill.DefaultSkill
import com.runt9.fusionOfSouls.view.BattleUnit
import com.soywiz.korio.file.std.resourcesVfs

class EnemyGenerator(private val gridService: GridService) {
    fun generateEnemies(count: Int, strength: Double): List<BattleUnit> {
        return (0 until count).map { generateEnemy(strength) }
    }

    fun generateEnemy(strength: Double): BattleUnit {
        val randomEnemyPoint = gridService.addRandomlyToGrid(gridWidth - 5, gridWidth - 1)
        val enemyUnit = GameUnit("enemy", resourcesVfs["redArrow-tp.png"], DefaultSkill(), listOf(TankClass()))
        enemyUnit.savedGridPos = randomEnemyPoint
        val enemyUnitView = BattleUnit(enemyUnit, Team.ENEMY)
        enemyUnit.primaryAttrs.all.forEach {
            it.addModifier(AttributeModifier(percentModifier = strength))
        }
        return enemyUnitView
    }
}