package io.github.inggameteam.inggame.minigame.handler

import io.github.inggameteam.inggame.component.PropHandler
import io.github.inggameteam.inggame.minigame.GamePlayerService
import io.github.inggameteam.inggame.player.PlayerService
import io.github.inggameteam.inggame.utils.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerMoveEvent
import kotlin.system.measureTimeMillis

@PropHandler
class PrintOnMove(
    private val gamePlayerService: GamePlayerService,
    plugin: IngGamePlugin
) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        val player = event.player.uniqueId
        measureTimeMillis{
            repeat(1000) {
                gamePlayerService[player, javaClass.simpleName, Boolean::class]
            }
        }.apply { println(this) }
        if (gamePlayerService[player, javaClass.simpleName, Boolean::class]) {
            event.player.sendMessage("PrintOnMove!!!")
        }
    }

}