package com.example.nav3example.examples.booking

import org.junit.Assert.assertEquals
import org.junit.Test

class BookingFlowControllerTest {
    @Test
    fun anonymousPaymentRequiresSignInAndThenContinuesToPayment() {
        val backStack = mutableListOf<BookingRoute>(
            BookingRoute.Search,
            BookingRoute.HotelDetail(10),
            BookingRoute.RoomSelection(10)
        )
        val session = BookingSession(isSignedIn = false)
        val controller = BookingFlowController(backStack, session)

        controller.continueToPayment()

        assertEquals(
            listOf(
                BookingRoute.Search,
                BookingRoute.HotelDetail(10),
                BookingRoute.RoomSelection(10),
                BookingRoute.SignIn(next = BookingRoute.Payment(10))
            ),
            backStack
        )

        controller.completeSignIn()

        assertEquals(
            listOf(
                BookingRoute.Search,
                BookingRoute.HotelDetail(10),
                BookingRoute.RoomSelection(10),
                BookingRoute.Payment(10)
            ),
            backStack
        )
    }

    @Test
    fun placingOrderCollapsesFlowToReceipt() {
        val backStack = mutableListOf<BookingRoute>(
            BookingRoute.Search,
            BookingRoute.HotelDetail(10),
            BookingRoute.RoomSelection(10),
            BookingRoute.Payment(10)
        )
        val controller = BookingFlowController(backStack, BookingSession(isSignedIn = true))

        controller.placeBooking()

        assertEquals(listOf(BookingRoute.Search, BookingRoute.Receipt(10)), backStack)
    }
}
