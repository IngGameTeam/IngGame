package io.github.inggameteam.inggame.minigame.wrapper.player

import io.github.inggameteam.inggame.component.delegate.Wrapper
import io.github.inggameteam.inggame.minigame.wrapper.game.Game
import io.github.inggameteam.inggame.minigame.wrapper.game.GameImp
import io.github.inggameteam.inggame.player.warpper.WrappedPlayer
import io.github.inggameteam.inggame.utils.TagContainer
import java.util.concurrent.CopyOnWriteArraySet

class GPlayer(wrapper: Wrapper) : WrappedPlayer(wrapper), TagContainer {

    var joinedGame: GameImp? by nullable
    override var tags: CopyOnWriteArraySet<String> by default { CopyOnWriteArraySet<String>() }

}