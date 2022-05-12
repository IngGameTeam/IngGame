package io.github.inggameteam.minigame

import io.github.inggameteam.party.PartyPluginImpl
import io.github.inggameteam.world.WorldGenerator
import org.bukkit.Bukkit
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPluginLoader
import java.io.File

open class GamePluginImpl : GamePlugin, PartyPluginImpl {
    lateinit var hubName: String
    var worldName: List<String> = config.getStringList("worlds")
    var width: Int = 0
    var height: Int = 0
    lateinit var init: Array<(GamePlugin) -> Game>

    constructor()
    constructor(hubName: String,
                width: Int, height: Int,
                init: Array<(GamePlugin) -> Game>) {
        this.hubName = hubName
        this.width = width
        this.height = height
        this.init = init
    }

    constructor(hubName: String,
                width: Int, height: Int,
                init: Array<(GamePlugin) -> Game>,
                loader: JavaPluginLoader, description: PluginDescriptionFile, dataFolder: File, file: File)
            : super(loader, description, dataFolder, file) {
        this.hubName = hubName
        this.width = width
        this.height = height
        this.init = init
    }

    override val gameSupplierRegister by lazy { GameSupplierRegister(this, *init) }
    override val gameRegister by lazy { GameRegister(this, hubName, worldName, width, height) }
    override fun onEnable() {
        super.onEnable()
        worldName.forEach { WorldGenerator.generateWorld(it) }
        gameSupplierRegister
        gameRegister
        gameRegister.apply { add(createGame(hubName)) }
        Bukkit.getOnlinePlayers().forEach { gameRegister.join(it, hubName) }
    }

}
