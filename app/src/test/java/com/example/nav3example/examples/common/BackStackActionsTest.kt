package com.example.nav3example.examples.common

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class BackStackActionsTest {
    @Test
    fun popIfNotRootKeepsRootEntry() {
        val backStack = mutableListOf("홈")

        val popped = backStack.popIfNotRoot()

        assertNull(popped)
        assertEquals(listOf("홈"), backStack)
    }

    @Test
    fun popIfNotRootRemovesOnlyTopEntry() {
        val backStack = mutableListOf("홈", "상세")

        val popped = backStack.popIfNotRoot()

        assertEquals("상세", popped)
        assertEquals(listOf("홈"), backStack)
    }
}
