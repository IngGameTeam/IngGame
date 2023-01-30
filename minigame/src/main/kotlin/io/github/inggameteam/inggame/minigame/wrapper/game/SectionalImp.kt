package io.github.inggameteam.inggame.minigame.wrapper.game

import io.github.inggameteam.inggame.component.delegate.Wrapper
import io.github.inggameteam.inggame.component.model.Location
import io.github.inggameteam.inggame.minigame.GameState
import org.bukkit.util.Vector
import java.util.concurrent.CopyOnWriteArrayList


class SectionalImp(wrapper: Wrapper) : Game by GameImp(wrapper), Sectional {


//    /**
//     * 할당된 구역 마무리 정리 시간
//     */
//    override val stopWaitingTick = 84600L * 20L
    override val schematicName: String by nonNull
    val schematicLocations: HashMap<String, CopyOnWriteArrayList<Location>> by nonNull

    override val minPoint: Vector
    override val maxPoint: Vector
    override var isUnloaded = false
    override val gameWidth: Int by nonNull
    override val gameHeight: Int by nonNull

    init {
        if (isAllocatedGame) {
            val vector = Vector(gameSector.x * gameWidth, 0, gameSector.y * gameWidth)
            val half = gameWidth / 2
            minPoint = vector.clone().add(Vector(-half, Int.MIN_VALUE, -half))
            maxPoint = vector.clone().add(Vector(half, Int.MAX_VALUE, half))
        } else {
            minPoint = Vector()
            maxPoint = Vector()
        }
    }



    val LOCATION get() = "static"

    override fun getLocation(key: String): org.bukkit.Location {
//        component.get(nameSpace, key, )
//        comp.location(key, schematicName).run {
//            toLocation(point.world).apply {
//                if (tag?.contains(LOCATION) == true) return@apply
//                x += width * point.x
//                y += height
//                z += width * point.y
//            }
//        }
        TODO()
    }


    override fun getLocationOrNull(key: String): org.bukkit.Location? {
//        comp.locationOrNull(key, schematicName)?.run {
//            toLocation(point.world).apply {
//                if (tag?.contains(LOCATION) == true) return@apply
//                x += width * point.x
//                y += height
//                z += width * point.y
//            }
//        }
    TODO()
    }


    override fun isInSector(location: org.bukkit.Location): Boolean {
        if (gameState !== GameState.WAIT && location.toVector().isInAABB(minPoint, maxPoint)) {
            return true
        }
        return false
    }

//    override fun finishGame() {
//        super.finishGame()
//        clearEntitiesToUnload()
//    }


}