package io.github.inggameteam.minigame.handle

import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.api.PluginHolder
import io.github.inggameteam.downloader.download
import io.github.inggameteam.scheduler.delay
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.Plugin

class AutoUpdater(override val plugin: Plugin) : HandleListener(plugin), PluginHolder<Plugin> {

    @Suppress("unused")
    @EventHandler
    fun onLeftNoPlayer(event: PlayerQuitEvent) {
        if (isEmptyOnline(event.player)) {
            {
                if (isEmptyOnline()) {
                    download(plugin)
                }
            }.delay(plugin, 20 * 10L)
        }

    }

    private fun isEmptyOnline(except: Player? = null): Boolean {
        return if (except !== null)
            Bukkit.getOnlinePlayers()
                .none { it.uniqueId != except.uniqueId }
        else Bukkit.getOnlinePlayers().isEmpty()
    }

}