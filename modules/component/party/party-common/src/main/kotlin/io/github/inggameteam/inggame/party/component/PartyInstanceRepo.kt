package io.github.inggameteam.inggame.party.component

import io.github.inggameteam.inggame.component.Layered
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService

@Layered("party")
class PartyInstanceRepo(componentService: ComponentService)
    : LayeredComponentService by componentService as LayeredComponentService