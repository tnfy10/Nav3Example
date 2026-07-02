package com.example.nav3example.examples.sharedstate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.setValue
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
fun SharedStateExample(modifier: Modifier = Modifier) {
    val backStack = rememberSharedBackStack(SharedStateRoute.Parent)
    var parentCount by remember { mutableIntStateOf(0) }
    var standaloneCount by remember { mutableIntStateOf(0) }

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.popIfNotRoot() },
        entryProvider = { key ->
            when (key) {
                SharedStateRoute.Parent -> NavEntry(key) {
                    ExampleScreen(
                        title = "부모 화면",
                        content = {
                            InfoCard("부모 scope count", "$parentCount")
                        },
                        actions = {
                            PrimaryAction("부모에서 증가") { parentCount += 1 }
                            PrimaryAction("자식 화면 열기") { backStack.add(SharedStateRoute.Child) }
                            PrimaryAction("독립 화면 열기") { backStack.add(SharedStateRoute.Standalone) }
                        }
                    )
                }

                SharedStateRoute.Child -> NavEntry(key) {
                    ExampleScreen(
                        title = "자식 화면",
                        content = {
                            InfoCard("공유된 부모 count", "$parentCount")
                            InfoCard("학습 포인트", "공식 shared ViewModel 패턴처럼 부모 scope의 상태를 자식이 함께 사용합니다.")
                        },
                        actions = {
                            PrimaryAction("자식에서 증가") { parentCount += 1 }
                            SecondaryAction("부모로 돌아가기") { backStack.popIfNotRoot() }
                        }
                    )
                }

                SharedStateRoute.Standalone -> NavEntry(key) {
                    ExampleScreen(
                        title = "독립 화면",
                        content = {
                            InfoCard("독립 count", "$standaloneCount")
                        },
                        actions = {
                            PrimaryAction("독립 상태 증가") { standaloneCount += 1 }
                            SecondaryAction("부모로 돌아가기") { backStack.popIfNotRoot() }
                        }
                    )
                }
            }
        }
    )
}

@Composable
private fun rememberSharedBackStack(vararg elements: SharedStateRoute): NavBackStack<SharedStateRoute> {
    return rememberSerializable(serializer = serializer()) {
        NavBackStack(*elements)
    }
}
