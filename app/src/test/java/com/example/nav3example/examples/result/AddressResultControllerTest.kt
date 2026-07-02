package com.example.nav3example.examples.result

import org.junit.Assert.assertEquals
import org.junit.Test

class AddressResultControllerTest {
    @Test
    fun selectingAddressStoresResultAndReturnsToCheckout() {
        val backStack = mutableListOf<ResultRoute>(ResultRoute.Checkout, ResultRoute.AddressPicker)
        val checkoutState = CheckoutAddressState()
        val controller = AddressResultController(backStack, checkoutState)

        controller.selectAddress("서울시 강남구 Navigation 3로 42")

        assertEquals("서울시 강남구 Navigation 3로 42", checkoutState.selectedAddress)
        assertEquals(listOf(ResultRoute.Checkout), backStack)
    }

    @Test
    fun cancellingAddressPickerReturnsWithoutChangingResult() {
        val backStack = mutableListOf(ResultRoute.Checkout, ResultRoute.AddressPicker)
        val checkoutState = CheckoutAddressState(selectedAddress = "기존 주소")
        val controller = AddressResultController(backStack, checkoutState)

        controller.cancelAddressSelection()

        assertEquals("기존 주소", checkoutState.selectedAddress)
        assertEquals(listOf(ResultRoute.Checkout), backStack)
    }
}
