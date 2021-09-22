package com.runt9.fusionOfSouls.view.duringRun.battleArea

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.SnapshotArray
import com.kotcrab.vis.ui.widget.Draggable
import com.kotcrab.vis.ui.widget.VisImage
import com.runt9.fusionOfSouls.battleHeight
import com.runt9.fusionOfSouls.battleWidth
import com.runt9.fusionOfSouls.bigMargin
import com.runt9.fusionOfSouls.cellSize
import com.runt9.fusionOfSouls.gridHeight
import com.runt9.fusionOfSouls.model.GridPoint
import com.runt9.fusionOfSouls.model.unit.BasicUnit
import com.runt9.fusionOfSouls.model.unit.GameUnit
import com.runt9.fusionOfSouls.model.unit.Team
import com.runt9.fusionOfSouls.service.BattleManager
import com.runt9.fusionOfSouls.service.GridService
import com.runt9.fusionOfSouls.service.runState
import com.runt9.fusionOfSouls.util.squarePixmap
import com.runt9.fusionOfSouls.view.BattleUnit
import com.runt9.fusionOfSouls.view.duringRun.UnitDraggingDragPane
import com.runt9.fusionOfSouls.view.duringRun.UnitGridTable
import ktx.scene2d.stack

// TODO: On enter with mouse, if dragging a unit, change grid color to DARK_GRAY from CLEAR
class UnitMainGridTable : UnitGridTable() {
    override val gridSquares: List<VisImage>

    init {
        align(Align.left)
        width = (battleWidth.toFloat() - bigMargin) * (4f / 14f)
        height = battleHeight.toFloat() - bigMargin
        val grid = mutableListOf<VisImage>()

        repeat(gridHeight) {
            row()
            repeat(4) {
                stack {
                    grid += squarePixmap(cellSize - 10, Color.DARK_GRAY)
                }.cell(space = 10f)
            }
        }

        gridSquares = grid.toList()
    }
}

class UnitGridDragPane(private val battleManager: BattleManager, private val gridService: GridService) : UnitDraggingDragPane(UnitMainGridTable()) {
    init {
        width = (battleWidth.toFloat() - bigMargin) * (4f / 14f)
        height = battleHeight.toFloat() - bigMargin
        align(Align.bottomLeft)
    }

    override fun accept(actor: Actor): Boolean {
        if (actor is BattleUnit) {
            return true
        }

        if (actor is GameUnit) {
            return runState.activeUnits.size < runState.unitCap
        }

        return super.accept(actor)
    }

    override fun getChildren(): SnapshotArray<Actor> =
        SnapshotArray(getStacks().filterIsInstance<BattleUnit>().toTypedArray())

    override fun addToProperStack(actor: Actor) {
        // TODO: Convert gridpos to table cell # (out of 28 total cells)
        val cellIndex = if (actor is BattleUnit) actor.gridPos.userGridCellIndex else 0
        getStacks()[cellIndex].add(actor)
    }

    override fun getActorToAdd(actor: Actor) = if (actor is BasicUnit) bringUnitFromBench(actor) else actor

    override fun doOnAdd(actor: Actor) {
        super.doOnAdd(actor)
        if (actor is BattleUnit) {
            gridService.unitMovedToCellIndex(actor, getStacks().indexOf(actor.parent))
        }
    }

    private fun bringUnitFromBench(unit: BasicUnit): BattleUnit {
        unit.savedGridPos = GridPoint(0.0, 0.0)
        unit.listeners.removeAll { it is Draggable }
        return runState.activateUnit(unit)
    }
}
