package com.example.nav3example.examples.onboarding

import org.junit.Assert.assertEquals
import org.junit.Test

class OnboardingFlowControllerTest {
    @Test
    fun firstLaunchStartsAtWelcome() {
        val state = OnboardingState(
            completedOnboarding = false,
            grantedNotificationPermission = false
        )

        assertEquals(OnboardingRoute.Welcome, startRouteFor(state))
    }

    @Test
    fun completingOnboardingMovesToPermissionBeforeMain() {
        val backStack = mutableListOf<OnboardingRoute>(OnboardingRoute.Welcome)
        val state = OnboardingState(completedOnboarding = false)
        val controller = OnboardingFlowController(backStack, state)

        controller.completeOnboarding()

        assertEquals(true, state.completedOnboarding)
        assertEquals(listOf(OnboardingRoute.Permission), backStack)
    }

    @Test
    fun grantingPermissionMovesToMain() {
        val backStack = mutableListOf<OnboardingRoute>(OnboardingRoute.Permission)
        val state = OnboardingState(
            completedOnboarding = true,
            grantedNotificationPermission = false
        )
        val controller = OnboardingFlowController(backStack, state)

        controller.grantPermission()

        assertEquals(true, state.grantedNotificationPermission)
        assertEquals(listOf(OnboardingRoute.Main), backStack)
    }
}
