package io.github.inggameteam.inggame.component.view

import io.github.inggameteam.inggame.component.SubClassRegistry
import io.github.inggameteam.inggame.component.model.ItemModel
import io.github.inggameteam.inggame.component.view.editor.*
import io.github.inggameteam.inggame.component.view.model.ElementView
import io.github.inggameteam.inggame.component.view.model.ModelViewImp
import io.github.inggameteam.inggame.component.view.selector.ItemStackPropSelector
import io.github.inggameteam.inggame.component.view.selector.ModelFieldSelector
import io.github.inggameteam.inggame.component.view.selector.Selector
import io.github.inggameteam.inggame.component.view.selector.SubTypeSelector
import io.github.inggameteam.inggame.mongodb.Model
import kotlin.reflect.KClass
import kotlin.reflect.KFunction2
import kotlin.reflect.KType
import kotlin.reflect.full.isSubclassOf


class EditorRegistry(private val subClassRegistry: SubClassRegistry) {


    fun getEditor(type: KType, elementView: ElementView?, selector: Selector<*>?, paramEditorView: EditorView<*>? = null): Editor {
        val clazz = type.singleClass
            .let { clazz ->
                elementView?.run {
                    try {
                        val any = componentService[nameSpace.name, element.first, Any::class]
                        if (any.javaClass.kotlin.isSubclassOf(clazz.kotlin)) {
                            any.javaClass
                        } else clazz
                    }
                    catch (_: Throwable) { clazz }
                }?: clazz
            }
        val modelView = ModelViewImp(elementView!!, type)
        val editorView = paramEditorView?: run {
            ElementEditorViewImp<Any>(modelView)
        }
        this.map.entries.firstOrNull { clazz.kotlin.isSubclassOf(it.key) }
            ?.value?.invoke(editorView, selector)?.run { return this }
        if (clazz.isEnum) {
            return EnumEditor(ModelViewImp(elementView, type), editorView, selector)
        } else if (clazz.getAnnotation(Model::class.java) !== null) {

            try {
                subClassRegistry.getSubs(clazz.kotlin)
                return SubTypeSelector(editorView, modelView, selector)
            } catch (_: Throwable) { }
            return ModelFieldSelector(editorView, modelView, selector)
        }
        throw AssertionError("$type Editor Not Found")
    }

    val map: HashMap<KClass<*>, (EditorView<*>, Selector<*>?) -> Editor> = hashMapOf(
        *listOf(
            Byte::class, Short::class, Int::class, Long::class,
            Float::class, Double::class,
            java.lang.Byte::class, java.lang.Short::class, java.lang.Integer::class, java.lang.Long::class,
            java.lang.Float::class, java.lang.Double::class, )
            .map { it to code(::NumberEditor) }.toTypedArray(),

//        Any::class to code(::StringEditor),
        java.lang.String::class to code(::StringEditor),
        String::class to code(::StringEditor),
        Boolean::class to code(::BooleanEditor),
        ItemModel::class to code(::ItemStackPropSelector),
        MutableCollection::class to code(::CollectionSelector),
    )

}

@Suppress("UNCHECKED_CAST")
private fun <T : Any> code(block: KFunction2<EditorView<T>, Selector<*>?, Editor>) = { editorView: EditorView<*>, selector: Selector<*>? ->
    block.invoke(editorView as EditorView<T>, selector)
}