package com.example.nav3example.examples.result

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.nav3example.examples.common.ExampleScreen
import com.example.nav3example.examples.common.InfoCard
import com.example.nav3example.examples.common.PrimaryAction
import com.example.nav3example.examples.common.SecondaryAction
import com.example.nav3example.examples.common.popIfNotRoot
import kotlinx.serialization.serializer

@Composable
fun ResultPassingExample(modifier: Modifier = Modifier) {
    val backStack = rememberResultBackStack(ResultRoute.Checkout)
    var selectedAddress by remember { mutableStateOf<String?>(null) }
    val checkoutState = remember {
        object {
            var value: String?
                get() = selectedAddress
                set(newValue) {
                    selectedAddress = newValue
                }
        }
    }

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.popIfNotRoot() },
        entryProvider = { key ->
            when (key) {
                ResultRoute.Checkout -> NavEntry<ResultRoute>(key) {
                    CheckoutPane(
                        selectedAddress = selectedAddress,
                        onPickAddress = {
                            backStack.add(ResultRoute.AddressPicker)
                        }
                    )
                }

                ResultRoute.AddressPicker -> NavEntry<ResultRoute>(key) {
                    AddressPickerPane(
                        onSelect = { address ->
                            checkoutState.value = address
                            backStack.popIfNotRoot()
                        },
                        onCancel = {
                            backStack.popIfNotRoot()
                        }
                    )
                }

            }
        }
    )
}

@Composable
private fun CheckoutPane(
    selectedAddress: String?,
    onPickAddress: () -> Unit
) {
    ExampleScreen(
        title = "체크아웃",
        content = {
            InfoCard(
                title = "선택된 배송지",
                body = selectedAddress ?: "아직 선택된 배송지가 없습니다."
            )
            InfoCard(
                title = "결과 전달",
                body = "주소 선택 화면은 새 route로 열리고, 선택 결과를 부모 상태에 기록한 뒤 자신을 pop합니다."
            )
        },
        actions = {
            PrimaryAction("배송지 선택") {
                onPickAddress()
            }
        }
    )
}

@Composable
private fun AddressPickerPane(
    onSelect: (String) -> Unit,
    onCancel: () -> Unit
) {
    ExampleScreen(
        title = "배송지 선택",
        content = {
            InfoCard(
                title = "주소 후보",
                body = "선택 버튼을 누르면 결과가 체크아웃 화면으로 전달됩니다."
            )
        },
        actions = {
            PrimaryAction("서울시 강남구 Navigation 3로 42 선택") {
                onSelect("서울시 강남구 Navigation 3로 42")
            }
            PrimaryAction("부산시 해운대구 Compose길 7 선택") {
                onSelect("부산시 해운대구 Compose길 7")
            }
            SecondaryAction("취소") {
                onCancel()
            }
        }
    )
}

@Composable
private fun rememberResultBackStack(vararg elements: ResultRoute): NavBackStack<ResultRoute> {
    return rememberSerializable(serializer = serializer()) {
        NavBackStack(*elements)
    }
}
