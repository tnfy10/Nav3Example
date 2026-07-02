package com.example.nav3example.examples.basic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.nav3example.examples.common.ExampleScreen
import com.example.nav3example.examples.common.InfoCard
import com.example.nav3example.examples.common.PrimaryAction
import com.example.nav3example.examples.common.SecondaryAction
import com.example.nav3example.examples.common.popIfNotRoot

private data object BasicHome
private data class BasicArticle(val id: Int, val title: String)

@Composable
fun BasicNavigationExample(modifier: Modifier = Modifier) {
    val backStack = remember { mutableStateListOf<Any>(BasicHome) }

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = {
            backStack.popIfNotRoot()
        },
        entryProvider = { key ->
            when (key) {
                BasicHome -> NavEntry(key) {
                    ExampleScreen(
                        title = "기초 홈",
                        content = {
                            InfoCard(
                                title = "핵심 아이디어",
                                body = "Navigation 3의 백 스택은 화면을 나타내는 키 목록입니다."
                            )
                        },
                        actions = {
                            PrimaryAction("글 1 열기") {
                                backStack.add(BasicArticle(1, "상태로 다루는 백 스택"))
                            }
                            PrimaryAction("글 2 열기") {
                                backStack.add(BasicArticle(2, "키를 화면으로 바꾸는 NavDisplay"))
                            }
                        }
                    )
                }

                is BasicArticle -> NavEntry(key) {
                    ExampleScreen(
                        title = key.title,
                        content = {
                            InfoCard(
                                title = "글 #${key.id}",
                                body = "이 화면은 backStack.add(...)로 백 스택에 추가되었습니다."
                            )
                        },
                        actions = {
                            SecondaryAction("뒤로 가기") {
                                backStack.popIfNotRoot()
                            }
                        }
                    )
                }

                else -> NavEntry(Unit) {
                    ExampleScreen(title = "알 수 없는 라우트")
                }
            }
        }
    )
}
