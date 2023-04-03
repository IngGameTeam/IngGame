package io.github.inggameteam.inggame.party.handler

import io.github.inggameteam.inggame.party.wrapper.PartyServer
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import io.github.inggameteam.inggame.utils.event.IngGamePluginEnableEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority

class DefaultPartyLoader(
    private val partyServer: PartyServer,
    private val partyHelper: PartyHelper,
    plugin: IngGamePlugin
) : Listener(plugin) {

    @Suppress("unused")
    @EventHandler(priority = EventPriority.LOWEST)
    fun onEnable(event: IngGamePluginEnableEvent) {
        partyServer.defaultParty = partyHelper.createContainer("defaultParty").apply {
            partyNameOrNull = nameSpace.toString()
        }
    }


}