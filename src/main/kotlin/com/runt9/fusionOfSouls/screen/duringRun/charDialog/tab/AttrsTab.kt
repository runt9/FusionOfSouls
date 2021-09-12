package com.runt9.fusionOfSouls.screen.duringRun.charDialog.tab

import com.badlogic.gdx.utils.Align
import com.runt9.fusionOfSouls.screen.duringRun.charDialog.scaledLabel
import com.runt9.fusionOfSouls.service.runState
import ktx.scene2d.vis.KVisTable

class AttrsTab : CharDialogTab("Attributes") {
    init {
        (contentTable as KVisTable).apply {
            runState.hero.secondaryAttrs.all.forEach { attr ->
                scaledLabel(attr.type.displayName).cell(align = Align.left, growX = true)
                scaledLabel(attr.displayValue()).cell(align = Align.center)
                row()
            }
        }
    }
}
