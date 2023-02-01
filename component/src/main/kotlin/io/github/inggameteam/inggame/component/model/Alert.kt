package io.github.inggameteam.inggame.component.model

import io.github.inggameteam.inggame.mongodb.Model
import io.github.inggameteam.inggame.utils.ColorUtil.color
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.*
import org.bukkit.entity.Player

interface AlertReciver

class AlertRecivingPlayer(player: Player) : Player by player, AlertReciver

@Model
interface Alert {
    fun send(reciver: AlertReciver, vararg args: Any)
}

class EmptyAlert() : Alert {
    override fun send(reciver: AlertReciver, vararg args: Any) {
        //nothing
    }

    override fun toString(): String {
        return this.javaClass.simpleName
    }
}

class ChatAlert(
    var message: String?
) : Alert {
    override fun send(reciver: AlertReciver, vararg args: Any) {
        val format = message?.color?.format(*args)
        if (reciver is Player) reciver.sendMessage(format)
        else println("$reciver: $format")
    }

    override fun toString() = "ChatAlert{$message}"

}

class ActionBarAlert(var message: String?) : Alert {
    override fun send(reciver: AlertReciver, vararg args: Any) {
        val format = message?.color?.format(*args)
        if (reciver is Player) reciver.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(format))
        else println("$reciver: $format")
    }
    override fun toString() = "ActionBarAlert{$message}"
}

class TitleAlert(
    var title: String?,
    var subTitle: String?,
    var fadeIn: Int,
    var stay: Int,
    var fadeOut: Int,
) : Alert {
    override fun send(reciver: AlertReciver, vararg args: Any) {
        if (reciver is Player) reciver.sendTitle(
            title?.color?.format(*args)?: "",
            subTitle?.color?.format(*args)?: "",
            fadeIn, stay, fadeOut,
        ) else println("$reciver: $title, $subTitle")
    }
    override fun toString() = "TitleAlert{$title, $subTitle, $fadeIn, $stay, $fadeOut}"
}

class BaseComponentAlert(
    var components: ArrayList<ActionComponent>
) : Alert {
    override fun send(reciver: AlertReciver, vararg args: Any) {
        val component = TextComponent(*components.map { it.append(*args) }.toTypedArray())
        if (reciver is Player) {
            reciver.spigot().sendMessage(component)
        } else println("$reciver:($component)")
    }
    override fun toString() = "BaseComponentAlert{$components}"
}

class ActionComponent(
    var message: String?,
    var clickAction: ClickEvent.Action?,
    var clickValue: String?,
    var hoverAction: HoverEvent.Action?,
    var hoverValue: String?,

    ) : Alert {

    override fun send(reciver: AlertReciver, vararg args: Any) {
        val component = TextComponent(append(*args))
        if (reciver is Player) {
            reciver.spigot().sendMessage(component)
        } else println("$reciver:($component)")
    }

    @Suppress("DEPRECATION")
    fun append(vararg args: Any) =
        TextComponent(message?.format(*args)).apply {
            if (clickAction !== null) clickEvent = ClickEvent(clickAction, clickValue?.color?.format(*args))
            if (hoverAction !== null) hoverEvent = HoverEvent(hoverAction, arrayOf(TextComponent(hoverValue?.color?.format(*args))))
        }
    override fun toString() = "ActionComponent{$message, $clickAction, $clickValue, $hoverAction, $hoverValue}"

}
