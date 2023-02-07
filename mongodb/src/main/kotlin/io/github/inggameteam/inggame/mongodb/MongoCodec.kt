package io.github.inggameteam.inggame.mongodb

import com.mongodb.MongoClientSettings
import io.github.inggameteam.inggame.utils.Model
import org.bson.BsonArray
import org.bson.BsonDocument
import org.bson.BsonDocumentWriter
import org.bson.Document
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.ClassModel
import org.bson.codecs.pojo.PojoCodecProvider
import org.koin.core.component.KoinComponent

class MongoCodec(codecs: Collection<Class<*>>) : KoinComponent {

    private val decodeFunctions: List<DecodeFunction> by lazy { getKoin().getAll() }
    private val encodeFunctions: List<EncodeFunction> by lazy { getKoin().getAll() }
    val codecRegistry = createCodec(codecs)

    fun decode(document: Any?): Any? {
        if (document === null) return null
        if (document is Document) {
            try {
                return codecRegistry[Class.forName(document.getString("_t"))]
                    .decode(
                        document.toBsonDocument().asBsonReader(),
                        DecoderContext.builder().checkedDiscriminator(true).build()
                    )
                    ?: throw AssertionError("An error occurred while decoding Document")
            } catch (_: Throwable) {
                return document.mapValues { decode(it.value) }
            }
        } else if (document is Collection<*>) {
            return document.map { decode(it) }
        } else  {
            var v: Any = document
            decodeFunctions.forEach { it.code.invoke(v)?.apply { v = this } }
            return v
        }
    }

    fun encode(value: Any?): Any? {
        if (value === null) return null
        return if (value.javaClass.getAnnotation(Model::class.java) !== null) {
            val document = BsonDocument()
            val writer = BsonDocumentWriter(document)
            codecRegistry[value.javaClass].encode(writer, value, EncoderContext.builder().build())
            return fromBson(document)
        } else if (value is Collection<*>) {
            BsonArray(value.map {
                (encode(it) as? Document)?.run(::toBson)
            }.toMutableList())
        } else if (value is Map<*, *>) {
            value.mapValues { encode(it.value) }
        } else {
            var v: Any = value
            encodeFunctions.forEach { it.code.invoke(v)?.apply { v = this } }
            return v
        }
    }


    private fun createCodec(codecs: Collection<Class<*>>): CodecRegistry {
        if (codecs.isEmpty()) {
            println("codecs Model is empty!")
        }
        val pojoCodecRegistry: CodecRegistry = CodecRegistries.fromProviders(
            PojoCodecProvider.builder().automatic(true)
                .apply {
                    codecs.map { clazz ->
                        ClassModel.builder(clazz).enableDiscriminator(true).build() }
                        .forEach(this::register)
                }.build()
        )
        return CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry)
    }

    fun fromBson(bson: BsonDocument): Document {
        return codecRegistry[Document::class.java].decode(bson.asBsonReader(), DecoderContext.builder().build())
    }

    fun toBson(document: Document): BsonDocument {
        return document.toBsonDocument(BsonDocument::class.java, codecRegistry)
    }


}
