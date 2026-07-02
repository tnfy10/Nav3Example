package com.example.nav3example.examples.booking

import androidx.navigation3.runtime.NavKey
import com.example.nav3example.examples.common.popIfNotRoot
import kotlinx.serialization.Serializable

@Serializable
sealed interface BookingRoute : NavKey {
    @Serializable
    data object Search : BookingRoute

    @Serializable
    data class HotelDetail(val hotelId: Int) : BookingRoute

    @Serializable
    data class RoomSelection(val hotelId: Int) : BookingRoute

    @Serializable
    data class SignIn(val next: BookingRoute) : BookingRoute

    @Serializable
    data class Payment(val hotelId: Int) : BookingRoute

    @Serializable
    data class Receipt(val hotelId: Int) : BookingRoute
}

data class BookingSession(var isSignedIn: Boolean)

class BookingFlowController(
    private val backStack: MutableList<BookingRoute>,
    private val session: BookingSession
) {
    fun openHotel(hotelId: Int) {
        backStack.add(BookingRoute.HotelDetail(hotelId))
    }

    fun selectRoom(hotelId: Int) {
        backStack.add(BookingRoute.RoomSelection(hotelId))
    }

    fun continueToPayment() {
        val hotelId = currentHotelId() ?: return
        val payment = BookingRoute.Payment(hotelId)
        if (session.isSignedIn) {
            backStack.add(payment)
        } else {
            backStack.add(BookingRoute.SignIn(next = payment))
        }
    }

    fun completeSignIn() {
        val signIn = backStack.lastOrNull() as? BookingRoute.SignIn ?: return
        session.isSignedIn = true
        backStack.removeAt(backStack.lastIndex)
        backStack.add(signIn.next)
    }

    fun placeBooking() {
        val hotelId = currentHotelId() ?: return
        backStack.clear()
        backStack.add(BookingRoute.Search)
        backStack.add(BookingRoute.Receipt(hotelId))
    }

    fun back() {
        backStack.popIfNotRoot()
    }

    private fun currentHotelId(): Int? {
        return backStack.asReversed().firstNotNullOfOrNull { route ->
            when (route) {
                is BookingRoute.HotelDetail -> route.hotelId
                is BookingRoute.RoomSelection -> route.hotelId
                is BookingRoute.Payment -> route.hotelId
                is BookingRoute.Receipt -> route.hotelId
                is BookingRoute.SignIn -> (route.next as? BookingRoute.Payment)?.hotelId
                BookingRoute.Search -> null
            }
        }
    }
}
