package com.example.nav3example.examples.sharedstate

import org.junit.Assert.assertEquals
import org.junit.Test

class SharedStateControllerTest {
    @Test
    fun childUpdatesParentScopedCounter() {
        val parentState = ParentCounterState()
        val standaloneState = ParentCounterState()
        val controller = SharedStateController(parentState, standaloneState)

        controller.incrementFromParent()
        controller.incrementFromChild()

        assertEquals(2, parentState.count)
        assertEquals(0, standaloneState.count)
    }

    @Test
    fun standaloneUsesDifferentCounterScope() {
        val parentState = ParentCounterState()
        val standaloneState = ParentCounterState()
        val controller = SharedStateController(parentState, standaloneState)

        controller.incrementStandalone()

        assertEquals(0, parentState.count)
        assertEquals(1, standaloneState.count)
    }
}
