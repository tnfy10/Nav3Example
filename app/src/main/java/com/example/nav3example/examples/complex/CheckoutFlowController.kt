package com.example.nav3example.examples.complex

import androidx.navigation3.runtime.NavKey
import com.example.nav3example.examples.common.popIfNotRoot
import kotlinx.serialization.Serializable

@Serializable
sealed interface ShopRoute : NavKey {
    @Serializable
    data object Catalog : ShopRoute

    @Serializable
    data class Product(val id: Int, val name: String) : ShopRoute

    @Serializable
    data object Cart : ShopRoute

    @Serializable
    data class SignIn(val next: ShopRoute) : ShopRoute

    @Serializable
    data object CheckoutAddress : ShopRoute

    @Serializable
    data object CheckoutPayment : ShopRoute

    @Serializable
    data object Receipt : ShopRoute
}

data class ShoppingSession(var isSignedIn: Boolean)

class CheckoutFlowController(
    private val backStack: MutableList<ShopRoute>,
    private val session: ShoppingSession
) {
    fun openProduct(id: Int, name: String) {
        backStack.add(ShopRoute.Product(id, name))
    }

    fun openCart() {
        backStack.add(ShopRoute.Cart)
    }

    fun startCheckout() {
        if (session.isSignedIn) {
            backStack.add(ShopRoute.CheckoutAddress)
        } else {
            backStack.add(ShopRoute.SignIn(next = ShopRoute.CheckoutAddress))
        }
    }

    fun completeSignIn() {
        val signIn = backStack.lastOrNull() as? ShopRoute.SignIn ?: return
        session.isSignedIn = true
        backStack.removeAt(backStack.lastIndex)
        backStack.add(signIn.next)
    }

    fun continueToPayment() {
        backStack.add(ShopRoute.CheckoutPayment)
    }

    fun placeOrder() {
        backStack.add(ShopRoute.Receipt)
    }

    fun pop() {
        backStack.popIfNotRoot()
    }
}
