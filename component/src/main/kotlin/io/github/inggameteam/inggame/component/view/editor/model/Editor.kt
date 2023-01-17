package io.github.inggameteam.inggame.component.view.editor.model

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.koin.core.Koin
import org.koin.core.qualifier.named
import kotlin.reflect.full.allSuperclasses
import kotlin.reflect.full.superclasses

interface Editor {

    val app: Koin
    val plugin: IngGamePlugin

    val view get() = app.get<ComponentService>(named("view"))

    val selector: String get() = javaClass.kotlin.superclasses.filter { it.java.isInterface }.first().simpleName!!

}

