package com.example.nav3example.examples.complex

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
fun ComplexCheckoutExample(modifier: Modifier = Modifier) {
    val backStack = rememberShopBackStack(ShopRoute.Catalog)
    val session = remember { ShoppingSession(isSignedIn = false) }
    val controller = remember(backStack, session) {
        CheckoutFlowController(backStack, session)
    }

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = controller::pop,
        entryProvider = { key ->
            when (key) {
                ShopRoute.Catalog -> NavEntry(key) {
                    ExampleScreen(
                        title = "상품 목록",
                        content = {
                            InfoCard(
                                title = "복잡한 흐름",
                                body = "체크아웃은 로그인 여부를 확인한 뒤 배송지와 결제 단계로 이어집니다."
                            )
                        },
                        actions = {
                            PrimaryAction("키보드 열기") {
                                controller.openProduct(100, "키보드")
                            }
                            PrimaryAction("장바구니 열기") {
                                controller.openCart()
                            }
                        }
                    )
                }

                is ShopRoute.Product -> NavEntry(key) {
                    ExampleScreen(
                        title = key.name,
                        content = {
                            InfoCard("상품 #${key.id}", "실제 앱의 라우트는 도메인 데이터를 함께 담을 수 있습니다.")
                        },
                        actions = {
                            PrimaryAction("장바구니에 담기") {
                                controller.openCart()
                            }
                            SecondaryAction("뒤로 가기") {
                                controller.pop()
                            }
                        }
                    )
                }

                ShopRoute.Cart -> NavEntry(key) {
                    ExampleScreen(
                        title = "장바구니",
                        content = {
                            InfoCard("담긴 상품", "키보드 x 1")
                        },
                        actions = {
                            PrimaryAction("체크아웃 시작") {
                                controller.startCheckout()
                            }
                            SecondaryAction("쇼핑 계속하기") {
                                controller.pop()
                            }
                        }
                    )
                }

                is ShopRoute.SignIn -> NavEntry(key) {
                    ExampleScreen(
                        title = "로그인",
                        content = {
                            InfoCard("로그인이 필요합니다", "다음 목적지는 SignIn.next 안에 보관됩니다.")
                        },
                        actions = {
                            PrimaryAction("로그인하고 계속하기") {
                                controller.completeSignIn()
                            }
                        }
                    )
                }

                ShopRoute.CheckoutAddress -> NavEntry(key) {
                    ExampleScreen(
                        title = "배송지",
                        actions = {
                            PrimaryAction("저장된 배송지 사용") {
                                controller.continueToPayment()
                            }
                            SecondaryAction("장바구니로 돌아가기") {
                                controller.pop()
                            }
                        }
                    )
                }

                ShopRoute.CheckoutPayment -> NavEntry(key) {
                    ExampleScreen(
                        title = "결제",
                        actions = {
                            PrimaryAction("주문하기") {
                                controller.placeOrder()
                            }
                            SecondaryAction("배송지로 돌아가기") {
                                controller.pop()
                            }
                        }
                    )
                }

                ShopRoute.Receipt -> NavEntry(key) {
                    ExampleScreen(
                        title = "주문 완료",
                        content = {
                            InfoCard("주문이 완료되었습니다", "전체 흐름은 라우트 키들의 목록으로 표현됩니다.")
                        },
                        actions = {
                            SecondaryAction("뒤로 가기") {
                                controller.pop()
                            }
                        }
                    )
                }
            }
        }
    )
}

@Composable
private fun rememberShopBackStack(vararg elements: ShopRoute): NavBackStack<ShopRoute> {
    return rememberSerializable(serializer = serializer()) {
        NavBackStack(*elements)
    }
}
