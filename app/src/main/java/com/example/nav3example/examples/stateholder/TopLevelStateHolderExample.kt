package com.example.nav3example.examples.stateholder

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.nav3example.examples.common.ExampleScreen
import com.example.nav3example.examples.common.InfoCard
import com.example.nav3example.examples.common.PrimaryAction
import com.example.nav3example.examples.common.SecondaryAction

@Composable
fun TopLevelStateHolderExample(modifier: Modifier = Modifier) {
    val selectedTopLevel = remember { mutableStateOf(TopLevelRoute.Home) }
    val stacks = remember {
        mutableStateMapOf(
            TopLevelRoute.Home to mutableListOf<AppRoute>(AppRoute.Home),
            TopLevelRoute.Messages to mutableListOf<AppRoute>(AppRoute.Messages),
            TopLevelRoute.Settings to mutableListOf<AppRoute>(AppRoute.Settings)
        )
    }
    val state = remember {
        object {
            val currentStack: MutableList<AppRoute>
                get() = stacks.getValue(selectedTopLevel.value)
        }
    }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar {
                TopLevelRoute.entries.forEach { route ->
                    NavigationBarItem(
                        selected = selectedTopLevel.value == route,
                        onClick = { selectedTopLevel.value = route },
                        label = { Text(route.label) },
                        icon = { Text(route.label.take(1)) }
                    )
                }
            }
        }
    ) { padding ->
        NavDisplay(
            modifier = Modifier.padding(padding),
            backStack = state.currentStack,
            onBack = {
                if (state.currentStack.size > 1) {
                    state.currentStack.removeAt(state.currentStack.lastIndex)
                } else {
                    selectedTopLevel.value = TopLevelRoute.Home
                }
            },
            entryProvider = { key ->
                when (key) {
                    AppRoute.Home -> NavEntry(key) {
                        ExampleScreen(
                            title = "홈",
                            modifier = Modifier,
                            content = {
                                InfoCard("State holder", "각 top-level route는 자기 백 스택을 따로 가집니다.")
                            },
                            actions = {
                                PrimaryAction("홈 상세 열기") {
                                    state.currentStack.add(AppRoute.HomeDetail(1))
                                }
                            }
                        )
                    }

                    is AppRoute.HomeDetail -> NavEntry(key) {
                        ExampleScreen(
                            title = "홈 상세 ${key.id}",
                            actions = {
                                SecondaryAction("뒤로 가기") {
                                    state.currentStack.removeAt(state.currentStack.lastIndex)
                                }
                            }
                        )
                    }

                    AppRoute.Messages -> NavEntry(key) {
                        ExampleScreen(
                            title = "메시지",
                            actions = {
                                PrimaryAction("스레드 42 열기") {
                                    state.currentStack.add(AppRoute.Thread(42))
                                }
                            }
                        )
                    }

                    is AppRoute.Thread -> NavEntry(key) {
                        ExampleScreen(
                            title = "스레드 ${key.id}",
                            content = {
                                InfoCard("독립 스택", "홈 탭으로 갔다가 돌아와도 메시지 스택이 유지됩니다.")
                            },
                            actions = {
                                SecondaryAction("뒤로 가기") {
                                    state.currentStack.removeAt(state.currentStack.lastIndex)
                                }
                            }
                        )
                    }

                    AppRoute.Settings -> NavEntry(key) {
                        ExampleScreen(title = "설정")
                    }
                }
            }
        )
    }
}

private val TopLevelRoute.label: String
    get() = when (this) {
        TopLevelRoute.Home -> "홈"
        TopLevelRoute.Messages -> "메시지"
        TopLevelRoute.Settings -> "설정"
    }
