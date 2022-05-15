package io.github.inggameteam.plugin.angangang

import io.github.inggameteam.item.game.MinigameMenu
import io.github.inggameteam.item.impl.HandyGun
import io.github.inggameteam.item.impl.ShotGun
import io.github.inggameteam.minigame.GamePluginImpl
import io.github.inggameteam.minigame.angangang.game.impl.*
import io.github.inggameteam.minigame.handle.ChunkHandler
import io.github.inggameteam.minigame.handle.HandleDeath
import io.github.inggameteam.minigame.ui.MinigameCommand
import io.github.inggameteam.party.PartyCacheSerializer
import io.github.inggameteam.minigame.handle.NoHunger
import io.github.inggameteam.minigame.handle.ReloadWatchDog
import org.bukkit.Bukkit

@Suppress("unused")
class Plugin : GamePluginImpl(
    hubName = "hub",
    width = 300, height = 128,
    init = arrayOf(
        ::Hub,
        ::TNTTag,
        ::TNTRun,
        ::RandomWeaponWar,
        ::BlockHideAndSeek,
        ::TeamWars,
        ::AvoidAnvil,
        ::BedWars,

    ),
) {

    override fun onEnable() {
        super.onEnable()
        MinigameCommand(this)
        ReloadWatchDog(this)
        NoHunger(this, worldName)
        HandleDeath(this)
        ChunkHandler(this)
        PartyCacheSerializer.deserialize(this)

        listOf(
            ::HandyGun,
            ::ShotGun,
            ::MinigameMenu,
        ).forEach {
            Bukkit.getPluginManager().registerEvents(it(this), this)
        }
    }

    override fun onDisable() {
        super.onDisable()
        PartyCacheSerializer.serialize(this)
    }
}
