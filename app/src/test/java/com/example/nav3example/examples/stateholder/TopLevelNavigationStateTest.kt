package com.example.nav3example.examples.stateholder

import org.junit.Assert.assertEquals
import org.junit.Test

class TopLevelNavigationStateTest {
    @Test
    fun topLevelNavigationSwitchesSelectedStackWithoutClearingHistory() {
        val state = TopLevelNavigationState(
            startRoute = TopLevelRoute.Home,
            backStacks = linkedMapOf(
                TopLevelRoute.Home to mutableListOf(AppRoute.Home),
                TopLevelRoute.Messages to mutableListOf(AppRoute.Messages),
                TopLevelRoute.Settings to mutableListOf(AppRoute.Settings)
            )
        )
        val navigator = TopLevelNavigator(state)

        navigator.navigate(AppRoute.HomeDetail(1))
        navigator.navigate(AppRoute.Messages)
        navigator.navigate(AppRoute.Thread(42))
        navigator.navigate(AppRoute.Home)

        assertEquals(TopLevelRoute.Home, state.selectedTopLevel)
        assertEquals(listOf(AppRoute.Home, AppRoute.HomeDetail(1)), state.currentStack)
        assertEquals(listOf(AppRoute.Messages, AppRoute.Thread(42)), state.backStacks[TopLevelRoute.Messages])
    }

    @Test
    fun backAtTopLevelReturnsToStartRoute() {
        val state = TopLevelNavigationState(
            startRoute = TopLevelRoute.Home,
            selectedTopLevel = TopLevelRoute.Messages,
            backStacks = linkedMapOf(
                TopLevelRoute.Home to mutableListOf(AppRoute.Home),
                TopLevelRoute.Messages to mutableListOf(AppRoute.Messages),
                TopLevelRoute.Settings to mutableListOf(AppRoute.Settings)
            )
        )
        val navigator = TopLevelNavigator(state)

        navigator.goBack()

        assertEquals(TopLevelRoute.Home, state.selectedTopLevel)
    }
}
