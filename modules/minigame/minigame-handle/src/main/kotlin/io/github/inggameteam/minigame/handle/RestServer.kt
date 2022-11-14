package io.github.inggameteam.minigame.handle

import io.github.inggameteam.minigame.GamePlugin
import io.javalin.Javalin
import io.javalin.http.Header
import kotlin.concurrent.thread

class RestServer(val plugin: GamePlugin) {

    init {
        thread {
            val app = Javalin.create(/*config*/)
                .get("/game-stats") { ctx ->
                    ctx.header(Header.ACCESS_CONTROL_ALLOW_CREDENTIALS, "*")
                    ctx.json(
                    """
                {"message": "Hello World"}
                """.trimIndent()
                )
            }
                .start(8080)
            println("RestServer Started!")
            plugin.addDisableEvent {
                app.stop()
            }

        }
    }

}