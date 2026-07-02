package com.example.nav3example.examples.stateholder

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
enum class TopLevelRoute {
    Home,
    Messages,
    Settings
}

@Serializable
sealed interface AppRoute : NavKey {
    @Serializable
    data object Home : AppRoute

    @Serializable
    data class HomeDetail(val id: Int) : AppRoute

    @Serializable
    data object Messages : AppRoute

    @Serializable
    data class Thread(val id: Int) : AppRoute

    @Serializable
    data object Settings : AppRoute
}

class TopLevelNavigationState(
    val startRoute: TopLevelRoute,
    selectedTopLevel: TopLevelRoute = startRoute,
    val backStacks: MutableMap<TopLevelRoute, MutableList<AppRoute>>
) {
    var selectedTopLevel: TopLevelRoute = selectedTopLevel

    val currentStack: MutableList<AppRoute>
        get() = backStacks.getValue(selectedTopLevel)
}

class TopLevelNavigator(private val state: TopLevelNavigationState) {
    fun navigate(route: AppRoute) {
        val topLevel = route.toTopLevelRoute()
        if (topLevel != null) {
            state.selectedTopLevel = topLevel
        } else {
            state.currentStack.add(route)
        }
    }

    fun goBack() {
        if (state.currentStack.size > 1) {
            state.currentStack.removeAt(state.currentStack.lastIndex)
        } else if (state.selectedTopLevel != state.startRoute) {
            state.selectedTopLevel = state.startRoute
        }
    }

    private fun AppRoute.toTopLevelRoute(): TopLevelRoute? {
        return when (this) {
            AppRoute.Home -> TopLevelRoute.Home
            AppRoute.Messages -> TopLevelRoute.Messages
            AppRoute.Settings -> TopLevelRoute.Settings
            else -> null
        }
    }
}
