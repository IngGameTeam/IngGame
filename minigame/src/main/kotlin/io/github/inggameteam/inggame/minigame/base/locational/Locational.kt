package io.github.inggameteam.inggame.minigame.base.locational

import io.github.inggameteam.inggame.component.delegate.Wrapper
import io.github.inggameteam.inggame.component.model.LocationModel
import org.bukkit.Location

val LocationNotFound = Throwable("Location is not exist")
interface Locational : Wrapper {

    val locations: HashMap<String, LocationModel>
    fun getLocation(name: String): Location = getLocationOrNull(name)?: throw LocationNotFound
    fun getLocationOrNull(name: String): Location?

}