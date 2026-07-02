package com.example.nav3example.examples.adaptive

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.nav3example.examples.common.ExampleScreen
import com.example.nav3example.examples.common.InfoCard
import com.example.nav3example.examples.common.SecondaryAction
import com.example.nav3example.examples.common.popIfNotRoot
import kotlinx.serialization.Serializable

private const val LIST_DETAIL_WIDTH_DP = 840

enum class MailLayout {
    SinglePane,
    ListDetail
}

fun mailLayoutFor(widthDp: Int, hasSelectedDetail: Boolean): MailLayout {
    return if (widthDp >= LIST_DETAIL_WIDTH_DP && hasSelectedDetail) {
        MailLayout.ListDetail
    } else {
        MailLayout.SinglePane
    }
}

@Serializable
private sealed interface MailRoute : NavKey {
    @Serializable
    data object Inbox : MailRoute

    @Serializable
    data class ThreadDetail(val id: Int) : MailRoute
}

private data class MailThread(
    val id: Int,
    val sender: String,
    val subject: String,
    val preview: String
)

private val mailThreads = listOf(
    MailThread(1, "민아", "Navigation 3 정리", "백 스택을 상태처럼 다루는 예제입니다."),
    MailThread(2, "준", "태블릿 레이아웃", "넓은 화면에서는 목록과 상세를 함께 보여줍니다."),
    MailThread(3, "서연", "딥링크 확인", "다음 예제에서 특정 화면으로 바로 진입합니다.")
)

@Composable
fun AdaptiveMailExample(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(MailRoute.Inbox)

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val selectedThread = backStack.lastOrNull() as? MailRoute.ThreadDetail
        when (mailLayoutFor(maxWidth.value.toInt(), selectedThread != null)) {
            MailLayout.SinglePane -> {
                NavDisplay(
                    backStack = backStack,
                    onBack = { backStack.popIfNotRoot() },
                    entryProvider = { key ->
                        when (key) {
                            MailRoute.Inbox -> NavEntry<NavKey>(key) {
                                InboxPane(
                                    onThreadClick = { id ->
                                        backStack.add(MailRoute.ThreadDetail(id))
                                    }
                                )
                            }

                            is MailRoute.ThreadDetail -> NavEntry<NavKey>(key) {
                                ThreadDetailPane(
                                    threadId = key.id,
                                    showBack = true,
                                    onBack = { backStack.popIfNotRoot() }
                                )
                            }

                            else -> NavEntry<NavKey>(key) {
                                ExampleScreen(title = "알 수 없는 라우트")
                            }
                        }
                    }
                )
            }

            MailLayout.ListDetail -> {
                val detailRoute = selectedThread ?: return@BoxWithConstraints
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Card(modifier = Modifier.weight(0.42f).fillMaxSize()) {
                        InboxPane(
                            onThreadClick = { id ->
                                if (detailRoute.id != id) {
                                    backStack.add(MailRoute.ThreadDetail(id))
                                }
                            }
                        )
                    }
                    Card(modifier = Modifier.weight(0.58f).fillMaxSize()) {
                        ThreadDetailPane(
                            threadId = detailRoute.id,
                            showBack = false,
                            onBack = { backStack.popIfNotRoot() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InboxPane(onThreadClick: (Int) -> Unit) {
    ExampleScreen(
        title = "메일함",
        content = {
            InfoCard(
                title = "Adaptive 목록-상세",
                body = "좁은 화면에서는 화면 전환, 넓은 화면에서는 목록과 상세를 동시에 보여줍니다."
            )
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(mailThreads) { thread ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onThreadClick(thread.id) }
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(thread.sender, style = MaterialTheme.typography.labelLarge)
                            Text(thread.subject, style = MaterialTheme.typography.titleMedium)
                            Text(thread.preview, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun ThreadDetailPane(
    threadId: Int,
    showBack: Boolean,
    onBack: () -> Unit
) {
    val thread = mailThreads.first { it.id == threadId }
    ExampleScreen(
        title = thread.subject,
        content = {
            InfoCard(
                title = "${thread.sender}의 메일",
                body = "${thread.preview}\n\n선택된 상세 route: ThreadDetail(${thread.id})"
            )
        },
        actions = {
            if (showBack) {
                SecondaryAction("메일함으로 돌아가기", onClick = onBack)
            }
        }
    )
}
