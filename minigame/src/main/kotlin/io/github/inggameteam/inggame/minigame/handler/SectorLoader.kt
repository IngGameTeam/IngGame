package io.github.inggameteam.inggame.minigame.handler

import io.github.inggameteam.inggame.component.Handler.Companion.isHandler
import io.github.inggameteam.inggame.component.delegate.getAll
import io.github.inggameteam.inggame.minigame.GameInstanceService
import io.github.inggameteam.inggame.minigame.Sector
import io.github.inggameteam.inggame.minigame.singleton.GameServer
import io.github.inggameteam.inggame.minigame.wrapper.game.SectionalImp
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.world.WorldGenerator
import kotlin.math.sqrt

class SectorLoader(
    private val gameInstanceService: GameInstanceService,
    private val plugin: IngGamePlugin
) {

    fun loadWorld(world: String) {
        plugin.server.getWorld(world)?: run {
            WorldGenerator.generateWorld(world) {}
            plugin.server.getWorld(world)
        }
    }

    fun newAllocatable(worldString: String): Sector {
        loadWorld(worldString)
        val list = gameInstanceService.getAll(::SectionalImp)
            .filter { it.isHandler(Sectional::class) }.filter(SectionalImp::isAllocatedGame)
            .map(SectionalImp::gameSector).filter { it.worldOrNull?.name == worldString }.toSet()
        val line = sqrt(list.size.toDouble()).toInt() + 1
        var x = 1
        while (x <= line) {
            var z = 1
            while (z <= line) {
                if (!list.any { it.equals(x, z) }) return Sector(x, z, worldString)
                z++
            }
            x++
        }
        return Sector(1, 1, worldString)
    }

}