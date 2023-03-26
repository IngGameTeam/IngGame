package io.github.inggameteam.inggame.component

import io.github.inggameteam.inggame.mongodb.MongoCodec
import io.github.inggameteam.inggame.utils.Debug
import org.bson.Document
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArraySet

data class NameSpace(
    val name: Any,
    var parents: CopyOnWriteArraySet<Any>,
    val elements: ConcurrentHashMap<Any, Any>) {
    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return false
    }

    override fun toString(): String {
        return "NameSpace{name=$name, parents=$parents, elements=$elements}"
    }

    fun clone(): NameSpace {
        return NameSpace(name, CopyOnWriteArraySet(parents), ConcurrentHashMap(elements))
    }

}

fun decodeNameSpace(doc: Document, codec: MongoCodec): NameSpace {
    val name = doc["_id"]!!
    val parents = doc.getList("parents", Any::class.java).run(::CopyOnWriteArraySet)
    val elements = doc.getList("elements", Document::class.java).associate {
        Pair(it["key"]!!, codec.decode(it["value"]!!)!!)
    }.run(::ConcurrentHashMap)
    return NameSpace(name, parents, elements)
}

fun encodeNameSpace(ns: NameSpace, codec: MongoCodec): Document {
    return Document().apply {
        set("_id", ns.name)
        set("parents", ns.parents)
        set("elements", ns.elements.entries.map { entry ->
            codec.encode(entry.value).let { Document().apply { set("key", entry.key); set("value", it) } }
        }.toList())
    }
}

val SINGLETON_NAMESPACE_NOT_FOUND get() = NameSpaceNotFoundException()
val NameSpaceNotFound: NameSpaceNotFoundException get() = if(Debug.isDebug) NameSpaceNotFoundException() else SINGLETON_NAMESPACE_NOT_FOUND

class NameSpaceNotFoundException : Throwable()

class Assert(message: Any) : Exception(message.toString())