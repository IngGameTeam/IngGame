package io.github.inggameteam.inggame.player.warpper

import io.github.inggameteam.inggame.component.delegate.Wrapper
import io.github.inggameteam.inggame.component.model.AlertReciver
import io.github.inggameteam.inggame.player.bukkit.NotImplementedPlayer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

open class WrappedPlayer(wrapper: Wrapper)
    : Wrapper by wrapper, AlertReciver,
    Player by Bukkit.getPlayer(wrapper.nameSpace as UUID) ?: NotImplementedPlayer()
{
    override fun toString() = name

    override fun equals(other: Any?): Boolean {
        if (super.equals(other)) return true
        if (other is Wrapper) {
            if (nameSpace == other.nameSpace) {
                return true
            }
        }
        return false
    }

}