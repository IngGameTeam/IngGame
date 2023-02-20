package io.github.inggameteam.inggame.minigame.component

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.ContainerHelper
import io.github.inggameteam.inggame.component.componentservice.ContainerHelperImp
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import io.github.inggameteam.inggame.minigame.base.game.Game
import io.github.inggameteam.inggame.minigame.base.player.GPlayer

class GameInstanceRepository(componentService: ComponentService)
    : LayeredComponentService by componentService as LayeredComponentService
