package io.github.inggameteam.inggame.component.view.controller

import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.entity.NameSpaceView
import net.md_5.bungee.api.ChatColor.RED
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent

class ElementForRemoveSelector(
    nameSpaceView: NameSpaceView,
    override val parentSelector: Selector<*>? = null
) : NameSpaceView by nameSpaceView, SelectorImp<String>() {
    override val elements: Collection<String> get() = nameSpace.elements.keys.map { it.toString() }


    override fun select(t: String, event: InventoryClickEvent) {
        componentService.set(nameSpace.name, t, null)
        parentSelector?.open(player)
    }

    override fun transform(t: String) = createItem(Material.DIRT, "${RED}$t")

}