package com.example.nav3example.examples.deeplink

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.nav3example.examples.common.ExampleScreen
import com.example.nav3example.examples.common.InfoCard
import com.example.nav3example.examples.common.PrimaryAction
import com.example.nav3example.examples.common.SecondaryAction
import com.example.nav3example.examples.common.popIfNotRoot

@Composable
fun DeepLinkExample(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(DeepLinkRoute.Home)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.popIfNotRoot() },
        entryProvider = { key ->
            when (key) {
                DeepLinkRoute.Home -> NavEntry<NavKey>(key) {
                    ExampleScreen(
                        title = "딥링크 홈",
                        content = {
                            InfoCard(
                                title = "외부 입력을 백 스택으로 변환",
                                body = "딥링크는 단일 화면이 아니라 앱이 자연스럽게 복원할 백 스택 목록으로 해석할 수 있습니다."
                            )
                        },
                        actions = {
                            PrimaryAction("nav3://product/42 열기") {
                                backStack.replaceWith(routesForDeepLink("nav3://product/42"))
                            }
                            PrimaryAction("nav3://search/navigation 열기") {
                                backStack.replaceWith(routesForDeepLink("nav3://search/navigation"))
                            }
                            PrimaryAction("알 수 없는 링크 열기") {
                                backStack.replaceWith(routesForDeepLink("nav3://unknown/value"))
                            }
                        }
                    )
                }

                DeepLinkRoute.Catalog -> NavEntry<NavKey>(key) {
                    ExampleScreen(
                        title = "상품 목록",
                        content = {
                            InfoCard(
                                title = "중간 route",
                                body = "상품 상세 딥링크는 Home > Catalog > Product 백 스택을 만듭니다."
                            )
                        },
                        actions = {
                            PrimaryAction("상품 7 열기") {
                                backStack.add(DeepLinkRoute.Product(7))
                            }
                            SecondaryAction("홈으로 돌아가기") {
                                backStack.popIfNotRoot()
                            }
                        }
                    )
                }

                is DeepLinkRoute.Product -> NavEntry<NavKey>(key) {
                    ExampleScreen(
                        title = "상품 ${key.id}",
                        content = {
                            InfoCard(
                                title = "딥링크 상세 화면",
                                body = "뒤로 가기를 누르면 딥링크가 만든 Catalog route로 돌아갑니다."
                            )
                        },
                        actions = {
                            SecondaryAction("뒤로 가기") {
                                backStack.popIfNotRoot()
                            }
                        }
                    )
                }

                is DeepLinkRoute.Search -> NavEntry<NavKey>(key) {
                    ExampleScreen(
                        title = "\"${key.query}\" 검색 결과",
                        content = {
                            InfoCard(
                                title = "검색 딥링크",
                                body = "검색어는 Search route의 인자로 저장됩니다."
                            )
                        },
                        actions = {
                            SecondaryAction("홈으로 돌아가기") {
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

private fun NavBackStack<NavKey>.replaceWith(routes: List<DeepLinkRoute>) {
    clear()
    addAll(routes)
}
