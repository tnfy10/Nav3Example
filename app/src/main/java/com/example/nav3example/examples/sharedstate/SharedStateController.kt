package com.example.nav3example.examples.sharedstate

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface SharedStateRoute : NavKey {
    @Serializable
    data object Parent : SharedStateRoute

    @Serializable
    data object Child : SharedStateRoute

    @Serializable
    data object Standalone : SharedStateRoute
}

data class ParentCounterState(var count: Int = 0)

class SharedStateController(
    private val parentState: ParentCounterState,
    private val standaloneState: ParentCounterState
) {
    fun incrementFromParent() {
        parentState.count += 1
    }

    fun incrementFromChild() {
        parentState.count += 1
    }

    fun incrementStandalone() {
        standaloneState.count += 1
    }
}
