package io.github.inggameteam.inggame.inggame

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.loader.ComponentLoader
import io.github.inggameteam.inggame.component.loader.loadComponents
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.event.IngGamePluginEnableEvent
import org.bukkit.Bukkit
import org.koin.core.Koin
import org.koin.dsl.bind
import org.koin.dsl.koinApplication
import org.koin.dsl.module

object IngGame {

    private fun loadApp(plugin: IngGamePlugin): Koin {
        return plugin.run {
            koinApplication {
                modules(module { single { plugin } bind IngGamePlugin::class })
                listOfNotNull(
                    loadComponents(plugin),
                ).apply { modules(this) }
            }.koin
        }.also { app ->
            app.get<ComponentLoader>()
            app.getAll<ComponentService>().map(ComponentService::layerPriority)
            Bukkit.getPluginManager().callEvent(IngGamePluginEnableEvent(plugin))
        }
    }

    private var appSemaphore = false
    val appDelegate = lazy { loadApp(
        Bukkit.getPluginManager().plugins.filterIsInstance<IngGamePlugin>().firstOrNull()
            ?: throw AssertionError("an app loading error occurred due to IngGamePlugin is not loaded")
    ) }
    val app: Koin
        get() = run {
            if (appSemaphore) throw AssertionError("an error occurred while get app while initializing app")
            appSemaphore = true
            val result = appDelegate.getValue(this, IngGame::appDelegate)
            appSemaphore = false
            result
        }
}