package com.example.nav3example.examples.advanced

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.nav3example.examples.common.ExampleScreen
import com.example.nav3example.examples.common.InfoCard
import com.example.nav3example.examples.common.PrimaryAction
import com.example.nav3example.examples.common.SecondaryAction
import com.example.nav3example.examples.common.popIfNotRoot

private enum class TopTab(val label: String) {
    Feed("피드"),
    Search("검색"),
    Profile("프로필")
}

private data object FeedHome
private data class FeedPost(val id: Int)
private data object SearchHome
private data class SearchResult(val query: String)
private data object ProfileHome
private data object ProfileSettings

@Composable
fun AdvancedTabsExample(modifier: Modifier = Modifier) {
    var selectedTab by remember { mutableStateOf(TopTab.Feed) }
    val feedStack = remember { mutableStateListOf<Any>(FeedHome) }
    val searchStack = remember { mutableStateListOf<Any>(SearchHome) }
    val profileStack = remember { mutableStateListOf<Any>(ProfileHome) }
    val currentStack = when (selectedTab) {
        TopTab.Feed -> feedStack
        TopTab.Search -> searchStack
        TopTab.Profile -> profileStack
    }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar {
                TopTab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        label = { Text(tab.label) },
                        icon = { Text(tab.label.take(1)) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier.padding(innerPadding),
            backStack = currentStack,
            onBack = { currentStack.popIfPossible() },
            entryProvider = { key ->
                when (key) {
                    FeedHome -> NavEntry(key) {
                        ExampleScreen(
                            title = "피드",
                            content = {
                                InfoCard("탭 상태", "글을 열고 다른 탭으로 이동한 뒤 다시 돌아와 보세요.")
                            },
                            actions = {
                                PrimaryAction("글 7 열기") { feedStack.add(FeedPost(7)) }
                            }
                        )
                    }

                    is FeedPost -> NavEntry(key) {
                        ExampleScreen(
                            title = "글 ${key.id}",
                            actions = {
                                SecondaryAction("피드로 돌아가기") { feedStack.popIfPossible() }
                            }
                        )
                    }

                    SearchHome -> NavEntry(key) {
                        ExampleScreen(
                            title = "검색",
                            actions = {
                                PrimaryAction("Navigation 3 검색") {
                                    searchStack.add(SearchResult("Navigation 3"))
                                }
                            }
                        )
                    }

                    is SearchResult -> NavEntry(key) {
                        ExampleScreen(
                            title = key.query,
                            content = {
                                InfoCard("검색 결과", "이 결과 화면은 검색 탭의 백 스택에만 속합니다.")
                            },
                            actions = {
                                SecondaryAction("검색으로 돌아가기") { searchStack.popIfPossible() }
                            }
                        )
                    }

                    ProfileHome -> NavEntry(key) {
                        ExampleScreen(
                            title = "프로필",
                            actions = {
                                PrimaryAction("설정 열기") {
                                    profileStack.add(ProfileSettings)
                                }
                            }
                        )
                    }

                    ProfileSettings -> NavEntry(key) {
                        ExampleScreen(
                            title = "설정",
                            actions = {
                                SecondaryAction("프로필로 돌아가기") { profileStack.popIfPossible() }
                            }
                        )
                    }

                    else -> NavEntry(Unit) { ExampleScreen(title = "알 수 없는 라우트") }
                }
            }
        )
    }
}

private fun SnapshotStateList<Any>.popIfPossible() {
    popIfNotRoot()
}
