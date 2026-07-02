package com.example.nav3example.examples.modal

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
fun ModalRouteExample(modifier: Modifier = Modifier) {
    val backStack = rememberModalBackStack(ModalRoute.Gallery)
    val controller = remember(backStack) { ModalRouteController(backStack) }

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = controller::closeTop,
        entryProvider = { key ->
            when (key) {
                ModalRoute.Gallery -> NavEntry(key) {
                    ExampleScreen(
                        title = "사진 갤러리",
                        content = {
                            InfoCard(
                                title = "모달도 route",
                                body = "필터, 미리보기, 확인 화면을 모두 백 스택 키로 관리합니다."
                            )
                        },
                        actions = {
                            PrimaryAction("필터 열기") { controller.openFilter() }
                            PrimaryAction("사진 7 미리보기") { controller.openPhoto(7) }
                        }
                    )
                }

                ModalRoute.Filter -> NavEntry(key) {
                    ExampleScreen(
                        title = "필터",
                        content = {
                            InfoCard("임시 UI", "필터 화면도 뒤로 가기와 상태 복원이 같은 규칙을 따릅니다.")
                        },
                        actions = {
                            SecondaryAction("필터 닫기") { controller.closeTop() }
                        }
                    )
                }

                is ModalRoute.PhotoPreview -> NavEntry(key) {
                    ExampleScreen(
                        title = "사진 ${key.id}",
                        content = {
                            InfoCard("미리보기", "삭제 확인은 이 화면 위에 쌓이는 또 다른 route입니다.")
                        },
                        actions = {
                            PrimaryAction("삭제 확인 열기") { controller.requestDelete(key.id) }
                            SecondaryAction("닫기") { controller.closeTop() }
                        }
                    )
                }

                is ModalRoute.DeleteConfirm -> NavEntry(key) {
                    ExampleScreen(
                        title = "삭제 확인",
                        content = {
                            InfoCard("정말 삭제할까요?", "확인하면 삭제 확인 route와 사진 미리보기 route를 함께 제거합니다.")
                        },
                        actions = {
                            PrimaryAction("삭제") { controller.confirmDelete() }
                            SecondaryAction("취소") { controller.closeTop() }
                        }
                    )
                }
            }
        }
    )
}

@Composable
private fun rememberModalBackStack(vararg elements: ModalRoute): NavBackStack<ModalRoute> {
    return rememberSerializable(serializer = serializer()) {
        NavBackStack(*elements)
    }
}
