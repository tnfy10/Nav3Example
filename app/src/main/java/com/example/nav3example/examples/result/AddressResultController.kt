package com.example.nav3example.examples.result

import androidx.navigation3.runtime.NavKey
import com.example.nav3example.examples.common.popIfNotRoot
import kotlinx.serialization.Serializable

@Serializable
sealed interface ResultRoute : NavKey {
    @Serializable
    data object Checkout : ResultRoute

    @Serializable
    data object AddressPicker : ResultRoute
}

data class CheckoutAddressState(
    var selectedAddress: String? = null
)

class AddressResultController(
    private val backStack: MutableList<ResultRoute>,
    private val checkoutState: CheckoutAddressState
) {
    fun openAddressPicker() {
        backStack.add(ResultRoute.AddressPicker)
    }

    fun selectAddress(address: String) {
        checkoutState.selectedAddress = address
        backStack.popIfNotRoot()
    }

    fun cancelAddressSelection() {
        backStack.popIfNotRoot()
    }
}
