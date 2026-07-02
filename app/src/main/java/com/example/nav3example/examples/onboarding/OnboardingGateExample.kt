package com.example.nav3example.examples.onboarding

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
fun OnboardingGateExample(modifier: Modifier = Modifier) {
    val state = remember { OnboardingState() }
    val backStack = rememberOnboardingBackStack(startRouteFor(state))
    val controller = remember(backStack, state) {
        OnboardingFlowController(backStack, state)
    }

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = {},
        entryProvider = { key ->
            when (key) {
                OnboardingRoute.Welcome -> NavEntry(key) {
                    ExampleScreen(
                        title = "온보딩",
                        content = {
                            InfoCard(
                                title = "Root flow 선택",
                                body = "첫 실행 상태에서는 메인 화면이 아니라 온보딩 route가 시작점이 됩니다."
                            )
                        },
                        actions = {
                            PrimaryAction("온보딩 완료") {
                                controller.completeOnboarding()
                            }
                        }
                    )
                }

                OnboardingRoute.Permission -> NavEntry(key) {
                    ExampleScreen(
                        title = "알림 권한 안내",
                        content = {
                            InfoCard(
                                title = "중간 gate",
                                body = "온보딩은 끝났지만 권한 안내가 필요해서 Main으로 바로 가지 않습니다."
                            )
                        },
                        actions = {
                            PrimaryAction("권한 허용") {
                                controller.grantPermission()
                            }
                            SecondaryAction("처음부터 다시 보기") {
                                controller.reset()
                            }
                        }
                    )
                }

                OnboardingRoute.Main -> NavEntry(key) {
                    ExampleScreen(
                        title = "메인 앱",
                        content = {
                            InfoCard(
                                title = "진입 완료",
                                body = "온보딩과 권한 gate를 모두 통과하면 앱의 메인 root로 교체됩니다."
                            )
                        },
                        actions = {
                            SecondaryAction("상태 초기화") {
                                controller.reset()
                            }
                        }
                    )
                }
            }
        }
    )
}

@Composable
private fun rememberOnboardingBackStack(
    vararg elements: OnboardingRoute
): NavBackStack<OnboardingRoute> {
    return rememberSerializable(serializer = serializer()) {
        NavBackStack(*elements)
    }
}
