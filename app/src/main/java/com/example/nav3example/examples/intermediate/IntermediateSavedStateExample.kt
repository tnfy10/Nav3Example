package com.example.nav3example.examples.intermediate

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.nav3example.examples.common.ExampleScreen
import com.example.nav3example.examples.common.InfoCard
import com.example.nav3example.examples.common.PrimaryAction
import com.example.nav3example.examples.common.SecondaryAction
import com.example.nav3example.examples.common.popIfNotRoot
import kotlinx.serialization.Serializable

@Serializable
private sealed interface IntermediateRoute : NavKey {
    @Serializable
    data object Inbox : IntermediateRoute

    @Serializable
    data class Message(val id: Long, val sender: String) : IntermediateRoute
}

@Composable
fun IntermediateSavedStateExample(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(IntermediateRoute.Inbox)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = {
            backStack.popIfNotRoot()
        },
        entryProvider = { key ->
            when (key) {
                IntermediateRoute.Inbox -> NavEntry<NavKey>(key) {
                    ExampleScreen(
                        title = "받은 편지함",
                        content = {
                            InfoCard(
                                title = "저장 가능한 백 스택",
                                body = "라우트는 NavKey를 구현하고 @Serializable을 사용해 상태 저장을 지원합니다."
                            )
                        },
                        actions = {
                            PrimaryAction("민아의 메시지 열기") {
                                backStack.add(IntermediateRoute.Message(42, "민아"))
                            }
                            PrimaryAction("준의 메시지 열기") {
                                backStack.add(IntermediateRoute.Message(84, "준"))
                            }
                        }
                    )
                }

                is IntermediateRoute.Message -> NavEntry<NavKey>(key) {
                    ExampleScreen(
                        title = "메시지 ${key.id}",
                        content = {
                            InfoCard(
                                title = key.sender,
                                body = "보낸 사람과 메시지 id는 라우트 인자로 전달됩니다."
                            )
                        },
                        actions = {
                            SecondaryAction("받은 편지함으로 돌아가기") {
                                backStack.popIfNotRoot()
                            }
                        }
                    )
                }

                else -> NavEntry<NavKey>(key) {
                    ExampleScreen(title = "알 수 없는 라우트")
                }
            }
        }
    )
}
