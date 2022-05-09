package io.github.inggameteam.minigame

enum class GameAlert {

    ONLY_LEADER_START,
    JOIN,
    START_SPECTATING,
    ALREADY_JOINED,
    CANNOT_JOIN_DUE_TO_STARTED,
    LEFT_GAME_DUE_TO_SERVER_LEFT,
    LEFT,
    START_CANCELLED_DUE_TO_PLAYERLESS,
    GAME_START_COUNT_DOWN,
    GAME_START,
    GAME_DRAW_HAS_WINNER,
    NEED_PLAYER,
    PLAYER_DEATH_TO_VOID,
    RED_TEAM_WIN,
    BLUE_TEAM_WIN,

    ;

    override fun toString() = name

}