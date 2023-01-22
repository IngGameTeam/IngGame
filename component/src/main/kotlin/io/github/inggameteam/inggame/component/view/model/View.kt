package io.github.inggameteam.inggame.component.view.model

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.entity.Player
import org.koin.core.Koin
import org.koin.core.qualifier.named
import kotlin.reflect.KProperty

interface View {

    val app: Koin
    val plugin: IngGamePlugin

    val view get() = app.get<ComponentService>(named("view"))

    val player: Player

}

