package io.github.inggameteam.minigame.angangang.game.base

import io.github.inggameteam.minigame.base.*

interface SimpleGame : Sectional, VoidDeath, SpawnOnStart, SpawnOnJoin, SpawnPlayer,
    LeaveWhenYouClickLeaveItem, StartPlayersAmountAlert
