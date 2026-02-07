package com.example.planify.main.features.actions.domain.entities

data class ActionsLocalInfo(
    val lastSeenActionId: String
) {
    constructor(
        lastSeenActionId: String? = null
    ) : this(
        lastSeenActionId = lastSeenActionId ?: ActionsLocalInfoDefaults.LAST_SEEN_ACTION_ID
    )
}
