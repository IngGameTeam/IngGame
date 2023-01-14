package io.github.inggameteam.inggame.mongodb

import com.mongodb.ConnectionString
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.Bukkit
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner
import kotlin.collections.ArrayList
import org.reflections.scanners.Scanners.*


fun createMongoModule(
    url: String,
    modelClasses: List<String>,
    vararg codecPackage: String,
) = module {
        single { ConnectionString(url) }
        single { MongoCodec(ArrayList<Class<*>>().apply {
            codecPackage.map { Reflections(it) }
                .map { it.getTypesAnnotatedWith(Model::class.java) }.forEach(::addAll)
            modelClasses.mapNotNull { clazz ->
                try { Class.forName(clazz) } catch (_: Throwable) { null }
            }.forEach(::add)
            apply { println(this) }
        }) }
        single { DatabaseString(get<ConnectionString>().database
            ?: throw AssertionError("database is not specified in the url")) }
        singleOf(::createClient)
        factoryOf(::MongoCollection)
        factoryOf(::MongoRepoImpl) bind MongoRepo::class
}

fun createRepo(name: String, collection: String) = module {
    factory(named(name)) {
        MongoRepoImpl(MongoCollection(get(), CollectionString(collection), get()))
    } bind MongoRepo::class
}


fun createFileRepo(name: String, fileDir: String) = module {
    factory(named(name)) { MongoFileRepo(fileDir) } bind MongoRepo::class
}