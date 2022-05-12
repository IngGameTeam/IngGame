package io.github.inggameteam.minigame.angangang.game.base

import io.github.inggameteam.alert.component.Lang.lang
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

interface OpenKitSelectMenuOnClickItem : KitSelectMenu {

    @Deprecated("EventHandler")
    @EventHandler
    fun onInteract(event: PlayerInteractEvent) = checkItem(event, event.player, event.item)

    @Deprecated("EventHandler")
    @EventHandler
    fun onInteractEntity(event: PlayerInteractEntityEvent) =
        checkItem(event, event.player, event.player.inventory.getItem(event.hand))

    private fun checkItem(event: Cancellable, player: Player, item: ItemStack?) {
        val player = plugin[player]
        if (!isJoined(player)) return
        val lang = player.lang(plugin)
        if (comp.item("kit-select-menu", lang).isSimilar(item)) {
            open(player)
        }
    }

}