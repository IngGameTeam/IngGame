package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.NameSpaceNotFoundException
import io.github.inggameteam.inggame.component.delegate.uncoverDelegate
import kotlin.reflect.KClass

@Suppress("NAME_SHADOWING")
class MultiParentsComponentService(
    override val name: String,
    private val rootComponent: () -> ComponentService?,
    private val components: Collection<ComponentService>,
    private val parentKey: Any?
) : ComponentService, AbstractNameSpaceComponentService() {

    init {
        if (components.isEmpty()) {
            throw AssertionError("an error occurred while create multi parents component service that empty components collection")
        }
    }

    override val parentComponent get() = components.first()

    override val layerPriority: Int by lazy { return@lazy components.maxOf { it.layerPriority } }

    private fun findParent(nameSpace: Any): Collection<ComponentService> {
        return try { rootComponent()!![nameSpace, parentKey!!, Any::class]
            .let { name -> components.firstOrNull { it.name == name }!! }.run(::listOf) }
        catch (_: Throwable) { components }
    }

    private fun <T, R> Iterable<T>.firstSuccess(block: (T) -> R, throws: Throwable): R {
        val throwable = ArrayList<Throwable>()
        this.forEach { try { return block(it) } catch (e: Throwable) { throwable.add(e) } }
        throwable.forEach(Throwable::printStackTrace)
        throw throws
    }

    override fun <T : Any> get(nameSpace: Any, key: Any, clazz: KClass<T>): T {
        if (parentKey == key)
            throw AssertionError("an error occurred while perform get method parentKey and key is same")
        val nameSpace = uncoverDelegate(nameSpace)
        return findParent(nameSpace).apply { println(this.map { it.name }) }.firstSuccess({ it[nameSpace, key, clazz] }, NameSpaceNotFoundException(nameSpace))
    }

    override fun set(nameSpace: Any, key: Any, value: Any?) {
        findParent(nameSpace).first().set(nameSpace, key, value)
    }

    override fun getOrNull(name: Any): NameSpace? {
        return findParent(name).firstSuccess({ getOrNull(name) }, NameSpaceNotFoundException(name))
    }

    override fun getAll(): Collection<NameSpace> {
        return emptyList()
    }

}