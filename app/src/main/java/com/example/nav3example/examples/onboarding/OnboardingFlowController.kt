package com.example.nav3example.examples.onboarding

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface OnboardingRoute : NavKey {
    @Serializable
    data object Welcome : OnboardingRoute

    @Serializable
    data object Permission : OnboardingRoute

    @Serializable
    data object Main : OnboardingRoute
}

data class OnboardingState(
    var completedOnboarding: Boolean = false,
    var grantedNotificationPermission: Boolean = false
)

fun startRouteFor(state: OnboardingState): OnboardingRoute {
    return when {
        !state.completedOnboarding -> OnboardingRoute.Welcome
        !state.grantedNotificationPermission -> OnboardingRoute.Permission
        else -> OnboardingRoute.Main
    }
}

class OnboardingFlowController(
    private val backStack: MutableList<OnboardingRoute>,
    private val state: OnboardingState
) {
    fun completeOnboarding() {
        state.completedOnboarding = true
        replaceRoot(startRouteFor(state))
    }

    fun grantPermission() {
        state.grantedNotificationPermission = true
        replaceRoot(startRouteFor(state))
    }

    fun reset() {
        state.completedOnboarding = false
        state.grantedNotificationPermission = false
        replaceRoot(startRouteFor(state))
    }

    private fun replaceRoot(route: OnboardingRoute) {
        backStack.clear()
        backStack.add(route)
    }
}
