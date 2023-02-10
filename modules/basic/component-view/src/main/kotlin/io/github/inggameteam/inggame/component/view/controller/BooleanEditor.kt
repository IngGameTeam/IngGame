package io.github.inggameteam.inggame.component.view.controller

import org.bukkit.entity.Player

class BooleanEditor(
    view: EditorView<Boolean>,
    override val previousSelector: Selector<*>? = null
) : Editor, EditorView<Boolean> by view {

    override fun open(player: Player) {
        val result = try { get() } catch (_: Throwable) { false }
        set(result?.not()?: true)
        previousSelector?.open(player)
    }

}