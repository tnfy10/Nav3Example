package com.example.nav3example.examples.booking

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.nav3example.examples.common.ExampleScreen
import com.example.nav3example.examples.common.InfoCard
import com.example.nav3example.examples.common.PrimaryAction
import com.example.nav3example.examples.common.SecondaryAction
import kotlinx.serialization.serializer

@Composable
fun BookingFlowExample(modifier: Modifier = Modifier) {
    val backStack = rememberBookingBackStack(BookingRoute.Search)
    val session = remember { BookingSession(isSignedIn = false) }
    val controller = remember(backStack, session) { BookingFlowController(backStack, session) }

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = controller::back,
        entryProvider = { key ->
            when (key) {
                BookingRoute.Search -> NavEntry(key) {
                    ExampleScreen(
                        title = "숙소 검색",
                        content = {
                            InfoCard("여행 예약 flow", "검색 > 상세 > 객실 > 로그인 gate > 결제 > 완료")
                        },
                        actions = {
                            PrimaryAction("호텔 10 보기") { controller.openHotel(10) }
                        }
                    )
                }

                is BookingRoute.HotelDetail -> NavEntry(key) {
                    ExampleScreen(
                        title = "호텔 ${key.hotelId}",
                        content = {
                            InfoCard("상세 화면", "예약 도메인의 중간 화면도 모두 route로 남습니다.")
                        },
                        actions = {
                            PrimaryAction("객실 선택") { controller.selectRoom(key.hotelId) }
                            SecondaryAction("뒤로 가기") { controller.back() }
                        }
                    )
                }

                is BookingRoute.RoomSelection -> NavEntry(key) {
                    ExampleScreen(
                        title = "객실 선택",
                        content = {
                            InfoCard("선택된 호텔", "호텔 ${key.hotelId}의 디럭스 룸")
                        },
                        actions = {
                            PrimaryAction("결제로 이동") { controller.continueToPayment() }
                            SecondaryAction("뒤로 가기") { controller.back() }
                        }
                    )
                }

                is BookingRoute.SignIn -> NavEntry(key) {
                    ExampleScreen(
                        title = "로그인",
                        content = {
                            InfoCard("중간 gate", "로그인 후 SignIn.next에 저장된 결제 route로 이어집니다.")
                        },
                        actions = {
                            PrimaryAction("로그인 완료") { controller.completeSignIn() }
                        }
                    )
                }

                is BookingRoute.Payment -> NavEntry(key) {
                    ExampleScreen(
                        title = "결제",
                        content = {
                            InfoCard("호텔 ${key.hotelId}", "결제가 끝나면 긴 flow를 검색 > 예약 완료로 축약합니다.")
                        },
                        actions = {
                            PrimaryAction("예약 확정") { controller.placeBooking() }
                            SecondaryAction("뒤로 가기") { controller.back() }
                        }
                    )
                }

                is BookingRoute.Receipt -> NavEntry(key) {
                    ExampleScreen(
                        title = "예약 완료",
                        content = {
                            InfoCard("예약 번호", "HOTEL-${key.hotelId}-NAV3")
                        },
                        actions = {
                            SecondaryAction("검색으로 돌아가기") { controller.back() }
                        }
                    )
                }
            }
        }
    )
}

@Composable
private fun rememberBookingBackStack(vararg elements: BookingRoute): NavBackStack<BookingRoute> {
    return rememberSerializable(serializer = serializer()) {
        NavBackStack(*elements)
    }
}
