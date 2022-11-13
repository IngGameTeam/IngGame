package io.github.inggameteam.mongodb.api

import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.api.IngGamePlugin
import io.github.inggameteam.scheduler.repeat
import io.github.inggameteam.utils.fastToString
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.test.assertNotNull

abstract class Container<USER : UUIDUser>(val plugin: IngGamePlugin,
                                          mongo: MongoDBCP,
                                          database: String,
                                          collection: String
)
    : PoolImpl<USER>(plugin, mongo, database, collection), Listener {

    init { Bukkit.getPluginManager().registerEvents(this, plugin) }
    init {
        Bukkit.getOnlinePlayers().forEach { pool.add(pool(it.uniqueId)) }
        plugin.addDisableEvent {
            pool.forEach(::commit)
        }

        ;{
            val onlinePlayers = Bukkit.getOnlinePlayers().map { it.uniqueId }
            synchronized(pool) {
                pool.forEach { user ->
                    if (!onlinePlayers.contains(user.uuid) && !user.isExited) {
                        commitAndRemoveAsync(user.uuid)
                    }
                }
            }
            true
        }.repeat(plugin, 50L, 50L)
    }

    private val pool = ArrayList<USER>()

//    private var threadPool = Executors.newCachedThreadPool()
//
//    fun submit(block: () -> USER) = threadPool.submit(block)

    @Suppress("unused")
    @EventHandler(priority = EventPriority.MONITOR)
    fun onLogin(event: AsyncPlayerPreLoginEvent) {
        if (!plugin.allowTask || !plugin.isEnabled) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "please reconnect...")
            return
        }
        if (event.loginResult !== AsyncPlayerPreLoginEvent.Result.ALLOWED) return
        val uniqueId = event.uniqueId
        if (pool.any { it.uuid == uniqueId })
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "committing your data... please reconnect.")
        else {
            pool.add(pool(uniqueId))
        }
    }

    private fun commitAndRemoveAsync(uuid: UUID) {
        if (plugin.allowTask) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin) { _ -> commitAndRemove(uuid) }
        } else {
            commitAndRemove(uuid)
        }
    }

    private fun commitAndRemove(uuid: UUID) {
        synchronized(pool) {
            pool.firstOrNull { uuid == it.uuid }?.apply {
                isExited = true
                try {
                    commit(this)
                } catch(e: Exception) { e.printStackTrace() }
                pool.remove(this)
            }
        }
    }

    @Suppress("unused")
    @EventHandler(ignoreCancelled = true)
    fun onQuitUploadMongo(event: PlayerQuitEvent) = commitAndRemoveAsync(event.player.uniqueId)

    @Suppress("unused")
    @EventHandler(ignoreCancelled = true)
    fun onKickedUpsert(event: PlayerKickEvent) = commitAndRemoveAsync(event.player.uniqueId)

    operator fun get(key: UUID): USER {
        try {
            return pool.firstOrNull { key == it.uuid }
                ?.apply { assertNotNull(this, "${javaClass.simpleName} does not contain ${key.fastToString()}") }!!
        } catch (e: Exception) {
            e.printStackTrace()
            val userName = Bukkit.getOnlinePlayers().firstOrNull { it.uniqueId == key }?.name ?: "notOnline"
            throw NullPointerException("$userName(${key.fastToString()})")
        }
    }
    operator fun get(key: Player) = get(key.uniqueId)


}