package io.github.inggameteam.inggame.minigame.base.spawnplayer

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.component.model.LocationModel
import io.github.inggameteam.inggame.component.wrapper.SimpleWrapper
import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.minigame.base.game.event.GPlayerSpawnEvent
import io.github.inggameteam.inggame.minigame.base.sectional.SectionalImp
import io.github.inggameteam.inggame.player.ContainerState
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler

interface TeleportOnStop : Wrapper {
    val teleportOnStop: LocationModel?
}

class TeleportOnStopImp(wrapper: Wrapper) : SimpleWrapper(wrapper), TeleportOnStop {
    override val teleportOnStop: LocationModel? by nullable
}

@Deprecated("not referenced")
class TeleportOnStopHandler(plugin: IngGamePlugin) : HandleListener(plugin) {
    @Suppress("unused")
    @EventHandler
    fun onGamePlayerSpawn(event: GPlayerSpawnEvent) {
        val player = event.player
        val game = player.joined[::SectionalImp]
        if (isNotHandler(game)) return
        if (game.containerState !== ContainerState.STOP) return
        val location = game[::TeleportOnStopImp].teleportOnStop
            ?.run { game.toRelative(this) }
            ?: return
        player.teleport(location)
    }

}