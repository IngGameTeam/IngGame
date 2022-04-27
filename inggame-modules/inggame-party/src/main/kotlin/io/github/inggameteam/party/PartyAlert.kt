package io.github.inggameteam.party

enum class PartyAlert {
    PARTY_CREATED,
    PARTY_DISBANDED,
    JOIN_PARTY,
    LEFT_PARTY,
    PARTY_RENAMED,
    PARTY_UNBANNED,
    PARTY_PROMOTED,
    PARTY_KICKED,
    PARTY_BANNED,
    CANNOT_REQUEST_PARTY_DUE_TO_BANNED,
    PARTY_REQUEST,
    SENT_PARTY_REQUEST,
    SENT_PARTY_REQUEST_RECEIVE_ALL,
    PARTY_REQUEST_TO_ALL,
    SENT_PARTY_REQUEST_TO_ALL,
    SENT_PARTY_REQUEST_TO_ALL_RECEIVE_ALL,

    ;

    override fun toString() = name

}