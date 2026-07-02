package com.example.nav3example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nav3example.examples.advanced.AdvancedTabsExample
import com.example.nav3example.examples.adaptive.AdaptiveMailExample
import com.example.nav3example.examples.basic.BasicNavigationExample
import com.example.nav3example.examples.booking.BookingFlowExample
import com.example.nav3example.examples.complex.ComplexCheckoutExample
import com.example.nav3example.examples.deeplink.DeepLinkExample
import com.example.nav3example.examples.intermediate.IntermediateSavedStateExample
import com.example.nav3example.examples.modal.ModalRouteExample
import com.example.nav3example.examples.onboarding.OnboardingGateExample
import com.example.nav3example.examples.result.ResultPassingExample
import com.example.nav3example.examples.sharedstate.SharedStateExample
import com.example.nav3example.ui.theme.Nav3ExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Nav3ExampleTheme {
                Nav3LearningApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Nav3LearningApp() {
    var selectedExample by remember { mutableStateOf<LearningExample?>(null) }
    BackHandler(enabled = selectedExample != null) {
        selectedExample = null
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(selectedExample?.title ?: "Navigation 3 예제") }
            )
        }
    ) { innerPadding ->
        when (selectedExample) {
            LearningExample.Basic -> BasicNavigationExample(Modifier.padding(innerPadding))
            LearningExample.Intermediate -> IntermediateSavedStateExample(Modifier.padding(innerPadding))
            LearningExample.Advanced -> AdvancedTabsExample(Modifier.padding(innerPadding))
            LearningExample.Complex -> ComplexCheckoutExample(Modifier.padding(innerPadding))
            LearningExample.Adaptive -> AdaptiveMailExample(Modifier.padding(innerPadding))
            LearningExample.DeepLink -> DeepLinkExample(Modifier.padding(innerPadding))
            LearningExample.Result -> ResultPassingExample(Modifier.padding(innerPadding))
            LearningExample.Onboarding -> OnboardingGateExample(Modifier.padding(innerPadding))
            LearningExample.Modal -> ModalRouteExample(Modifier.padding(innerPadding))
            LearningExample.Booking -> BookingFlowExample(Modifier.padding(innerPadding))
            LearningExample.SharedState -> SharedStateExample(Modifier.padding(innerPadding))
            null -> ExampleLauncher(
                onOpen = { selectedExample = it },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
private fun ExampleLauncher(
    onOpen: (LearningExample) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(LearningExample.entries) { example ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpen(example) }
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(example.title, style = MaterialTheme.typography.titleMedium)
                    Text(example.summary, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

enum class LearningExample(
    val title: String,
    val summary: String
) {
    Basic(
        title = "기초: 직접 다루는 백 스택",
        summary = "NavDisplay, NavEntry, 키 추가/제거 흐름"
    ),
    Intermediate(
        title = "중급: 저장 가능한 타입 라우트",
        summary = "Serializable NavKey, rememberNavBackStack, 인자 전달"
    ),
    Advanced(
        title = "응용: 탭별 백 스택",
        summary = "최상위 목적지마다 독립적인 이동 기록 유지"
    ),
    Complex(
        title = "복잡한 사례: 쇼핑 체크아웃",
        summary = "로그인 관문과 여러 단계의 체크아웃 흐름"
    ),
    Adaptive(
        title = "복잡한 사례: Adaptive 목록-상세",
        summary = "화면 폭에 따라 단일 화면과 목록-상세 동시 표시 전환"
    ),
    DeepLink(
        title = "복잡한 사례: 딥링크 진입",
        summary = "외부 링크를 Navigation 3 백 스택으로 변환"
    ),
    Result(
        title = "복잡한 사례: 결과 전달",
        summary = "자식 화면의 선택 결과를 부모 화면 상태로 반환"
    ),
    Onboarding(
        title = "실무 사례: 온보딩 Gate",
        summary = "첫 실행, 권한 안내, 메인 앱 진입을 root flow로 전환"
    ),
    Modal(
        title = "실무 사례: 모달 Route",
        summary = "필터, 미리보기, 확인 화면을 백 스택으로 관리"
    ),
    Booking(
        title = "도메인 사례: 여행 예약 Flow",
        summary = "검색부터 결제 완료까지 긴 flow와 로그인 gate 처리"
    ),
    SharedState(
        title = "공식 패턴: 부모-자식 공유 상태",
        summary = "부모 route scope의 상태를 자식 route에서 함께 사용"
    )
}
