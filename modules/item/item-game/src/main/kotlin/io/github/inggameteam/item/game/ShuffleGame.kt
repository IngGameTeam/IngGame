package io.github.inggameteam.item.game

import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.item.api.Interact
import io.github.inggameteam.item.api.Item
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.event.GameJoinEvent
import io.github.inggameteam.player.GPlayer
import org.bukkit.event.EventHandler

class ShuffleGame(override val plugin: GamePlugin) : Item, Interact, HandleListener(plugin) {
    override val name: String = "shuffle-game"
    override fun use(name: String, player: GPlayer) {
        player[SHUFFLE_KEY] = true
        shuffleJoin(player)
    }

    @Suppress("unused")
    @EventHandler
    fun onPlayerJoinHub(event: GameJoinEvent) {
        if (plugin.gameRegister.hubName != event.join.name) {
            event.player.remove(SHUFFLE_KEY)
        } else {
            if (event.player[SHUFFLE_KEY] !== null) {
                shuffleJoin(event.player)
            }
        }
    }

    fun shuffleJoin(player: GPlayer) {
        plugin.gameRegister.join(player, randomGame(player), forceCreateGame = true)
        player[SHUFFLE_KEY] = true
    }

    fun randomGame(player: GPlayer): String {
        val games = itemComp.stringList("$name-games", player.lang(plugin))
        return games.random()
    }

    companion object {
        const val SHUFFLE_KEY = "HandleShuffle"
    }
}