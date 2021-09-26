package com.runt9.fusionOfSouls

import com.badlogic.gdx.graphics.Color

// Metadata
const val gameTitle = "Fusion of Souls"
val primaryBgColor = Color.valueOf("#2b2b2b")

// Sizes
const val defaultWindowWidth = 1280
const val defaultWindowHeight = 720
const val viewportWidth = 1280
const val viewportHeight = 720
const val cellSize = 40
const val battleWidth = viewportWidth - (cellSize * 2)
const val battleHeight = viewportHeight - (cellSize * 2)
const val gridWidth = battleWidth / cellSize
const val gridHeight = battleHeight / cellSize
const val basicMargin = cellSize / 8
const val bigMargin = basicMargin * 2
const val resourceBarHeight = cellSize / 2
const val gridXStart = ((viewportWidth - battleWidth) / 2) + basicMargin
const val gridYStart = cellSize + (15)
const val benchBarHeight = cellSize * 0.75
const val maxInactiveUnits = 18
const val userGridWidth = 4
