package io.github.inggameteam.inggame.component.model

import io.github.inggameteam.inggame.mongodb.Model
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.*
import org.bukkit.entity.Player

interface AlertReciver

interface Alert {
    fun send(reciver: AlertReciver, vararg args: Any)
}

@Model
class ChatAlert(
    var message: String
) : Alert {
    override fun send(reciver: AlertReciver, vararg args: Any) {
        val format = message.format(*args)
        if (reciver is Player) reciver.sendMessage(format)
        else println("$reciver: $format")
    }

    override fun toString() = "ChatAlert($message)}"

}

@Model
class ActionBarAlert(var message: String) : Alert {
    override fun send(reciver: AlertReciver, vararg args: Any) {
        val format = message.format(*args)
        if (reciver is Player) reciver.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(format))
        else println("$reciver: $format")
    }
    override fun toString() = "ActionBarAlert($message)}"
}

@Model
class TitleAlert(
    var title: String,
    var subTitle: String,
    var fadeIn: Int,
    var stay: Int,
    var fadeOut: Int,
) : Alert {
    override fun send(reciver: AlertReciver, vararg args: Any) {
        if (reciver is Player) reciver.sendTitle(
            title.format(*args),
            subTitle.format(*args),
            fadeIn, stay, fadeOut,
        ) else println("$reciver: $title, $subTitle")
    }
    override fun toString() = "TitleAlert($title, $subTitle, $fadeIn, $stay, $fadeOut)}"
}

@Model
class BaseComponentAlert(
    var components: ArrayList<ActionComponent>
) : Alert {
    override fun send(reciver: AlertReciver, vararg args: Any) {
        if (reciver is Player) {
            val component = TextComponent(*components.map { it.append(*args) }.toTypedArray())
            reciver.spigot().sendMessage(component)
        } else println("$reciver:(component alert)")
    }
    override fun toString() = "BaseComponentAlert($components)}"
}

@Model
class ActionComponent(
    var message: String,
    var clickAction: ClickEvent.Action?,
    var clickValue: String?,
    var hoverAction: HoverEvent.Action?,
    var hoverValue: String?,

    ) {
    @Suppress("DEPRECATION")
    fun append(vararg args: Any) =
        TextComponent(message.format(*args)).apply {
            if (clickAction !== null) clickEvent = ClickEvent(clickAction, clickValue?.format(*args))
            if (hoverAction !== null) hoverEvent = HoverEvent(hoverAction, arrayOf(TextComponent(hoverValue?.format(*args))))
        }
    override fun toString() = "ActionComponent($message, $clickAction, $clickValue, $hoverAction, $hoverValue)}"

}

