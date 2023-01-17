package io.github.inggameteam.inggame.component.view.editor.model

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.koin.core.Koin

data class ElementEditorImp(
    override val componentService: ComponentService,
    override val app: Koin,
    override val nameSpace: NameSpace, override val plugin: IngGamePlugin
) : ElementEditor {
    constructor(csEditor: ComponentServiceEditor, nameSpace: NameSpace)
            : this(csEditor.componentService, csEditor.app, nameSpace, csEditor.plugin)
}