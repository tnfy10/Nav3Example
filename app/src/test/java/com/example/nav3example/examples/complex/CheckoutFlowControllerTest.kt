package com.example.nav3example.examples.complex

import org.junit.Assert.assertEquals
import org.junit.Test

class CheckoutFlowControllerTest {
    @Test
    fun checkoutRequiresSignInAndThenContinuesToAddress() {
        val backStack = mutableListOf<ShopRoute>(ShopRoute.Catalog)
        val session = ShoppingSession(isSignedIn = false)
        val controller = CheckoutFlowController(backStack, session)

        controller.openCart()
        controller.startCheckout()

        assertEquals(
            listOf(
                ShopRoute.Catalog,
                ShopRoute.Cart,
                ShopRoute.SignIn(next = ShopRoute.CheckoutAddress)
            ),
            backStack
        )

        controller.completeSignIn()

        assertEquals(
            listOf(
                ShopRoute.Catalog,
                ShopRoute.Cart,
                ShopRoute.CheckoutAddress
            ),
            backStack
        )
        assertEquals(true, session.isSignedIn)
    }

    @Test
    fun signedInCheckoutGoesStraightToAddress() {
        val backStack = mutableListOf<ShopRoute>(ShopRoute.Catalog)
        val session = ShoppingSession(isSignedIn = true)
        val controller = CheckoutFlowController(backStack, session)

        controller.openCart()
        controller.startCheckout()

        assertEquals(
            listOf(
                ShopRoute.Catalog,
                ShopRoute.Cart,
                ShopRoute.CheckoutAddress
            ),
            backStack
        )
    }

    @Test
    fun popKeepsCatalogWhenAlreadyAtRoot() {
        val backStack = mutableListOf<ShopRoute>(ShopRoute.Catalog)
        val session = ShoppingSession(isSignedIn = true)
        val controller = CheckoutFlowController(backStack, session)

        controller.pop()
        controller.pop()

        assertEquals(listOf(ShopRoute.Catalog), backStack)
    }
}
