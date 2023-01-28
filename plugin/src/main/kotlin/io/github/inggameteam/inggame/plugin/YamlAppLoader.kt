package io.github.inggameteam.inggame.plugin

import io.github.inggameteam.inggame.component.*
import io.github.inggameteam.inggame.component.view.createEditorRegistry
import io.github.inggameteam.inggame.minigame.createGameHandlers
import io.github.inggameteam.inggame.minigame.registerGameModels
import io.github.inggameteam.inggame.mongodb.loadMongoModule
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.koin.core.Koin
import org.koin.dsl.bind
import org.koin.dsl.koinApplication
import org.koin.dsl.module

fun loadApp(plugin: IngGamePlugin): Koin {
    return plugin.run {
        koinApplication {
            modules(module { single { plugin } bind IngGamePlugin::class })
            listOfNotNull(
                *loadComponentServiceModel().toTypedArray(),
                loadMongoModule(plugin),
                createSubClassRegistry(),
                createEditorRegistry(),
                createPropertyRegistry(),
                registerComponentModels(),
                registerGameModels(),
                createEmpty("default"),
                createGameHandlers(),
            ).apply { modules(this) }
        }.koin
    }
}