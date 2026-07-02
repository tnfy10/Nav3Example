# Navigation 3 학습 경로

이 앱은 Navigation 3 예제를 단계별 패키지로 나누어 둡니다. 기초부터 복잡한 사례까지 순서대로 코드를 읽으면서 학습할 수 있습니다.

## 1. 기초

파일: `app/src/main/java/com/example/nav3example/examples/basic/BasicNavigationExample.kt`

`NavDisplay`, `NavEntry`, 변경 가능한 백 스택을 중심으로 봅니다. 라우트는 단순 Kotlin 객체로 두어, Navigation 3의 가장 기본 모델이 "키 목록"이라는 점을 보여줍니다.

## 2. 중급

파일: `app/src/main/java/com/example/nav3example/examples/intermediate/IntermediateSavedStateExample.kt`

`NavKey`, `@Serializable`, 라우트 인자, `rememberNavBackStack`을 중심으로 봅니다. 저장 가능한 내비게이션 상태를 만들 때 실제로 사용하게 될 라우트 모델링 방식입니다.

## 3. 응용

파일: `app/src/main/java/com/example/nav3example/examples/advanced/AdvancedTabsExample.kt`

최상위 목적지마다 독립적인 백 스택을 유지하는 방법을 봅니다. 한 탭에서 하위 화면을 연 뒤 다른 탭으로 이동하고 다시 돌아와, 각 탭의 이동 기록이 따로 유지되는지 확인해 보세요.

## 4. 복잡한 사례

파일:

- `app/src/main/java/com/example/nav3example/examples/complex/CheckoutFlowController.kt`
- `app/src/main/java/com/example/nav3example/examples/complex/ComplexCheckoutExample.kt`
- `app/src/test/java/com/example/nav3example/examples/complex/CheckoutFlowControllerTest.kt`

실제 앱에 가까운 흐름을 다룹니다. 로그인하지 않은 상태에서 체크아웃을 시작하면 로그인 화면을 거친 뒤, 원래 이어가려던 체크아웃 단계로 계속 진행합니다.

## 5. Adaptive 목록-상세

파일:

- `app/src/main/java/com/example/nav3example/examples/adaptive/AdaptiveMailExample.kt`
- `app/src/test/java/com/example/nav3example/examples/adaptive/AdaptiveMailStateTest.kt`

메일 앱처럼 좁은 화면에서는 목록과 상세를 각각 한 화면씩 보여주고, 넓은 화면에서는 목록과 상세를 동시에 보여줍니다. Navigation 3의 백 스택은 그대로 유지하면서 레이아웃만 화면 폭에 따라 다르게 해석하는 예제입니다.

## 6. 딥링크 진입

파일:

- `app/src/main/java/com/example/nav3example/examples/deeplink/DeepLinkRoutes.kt`
- `app/src/main/java/com/example/nav3example/examples/deeplink/DeepLinkExample.kt`
- `app/src/test/java/com/example/nav3example/examples/deeplink/DeepLinkRouteParserTest.kt`

`nav3://product/42` 같은 외부 입력을 단일 화면이 아니라 `Home > Catalog > Product(42)` 백 스택으로 변환합니다. 사용자가 뒤로 가기를 눌렀을 때 앱의 자연스러운 중간 화면으로 돌아가게 만드는 패턴입니다.

## 7. 결과 전달

파일:

- `app/src/main/java/com/example/nav3example/examples/result/AddressResultController.kt`
- `app/src/main/java/com/example/nav3example/examples/result/ResultPassingExample.kt`
- `app/src/test/java/com/example/nav3example/examples/result/AddressResultControllerTest.kt`

체크아웃 화면에서 배송지 선택 화면을 열고, 사용자가 주소를 고르면 부모 화면의 상태에 결과를 저장한 뒤 배송지 선택 route를 제거합니다. Navigation 3에서 결과 전달을 별도 전역 이벤트가 아니라 명시적인 상태 변경과 백 스택 조작으로 표현하는 예제입니다.

## 8. 온보딩 Gate

파일:

- `app/src/main/java/com/example/nav3example/examples/onboarding/OnboardingFlowController.kt`
- `app/src/main/java/com/example/nav3example/examples/onboarding/OnboardingGateExample.kt`
- `app/src/test/java/com/example/nav3example/examples/onboarding/OnboardingFlowControllerTest.kt`

첫 실행, 권한 안내, 메인 앱 진입을 각각 root flow로 다룹니다. 화면을 계속 push하는 대신 현재 앱 상태에 맞는 시작 route로 백 스택을 교체하는 패턴입니다.

## 9. 모달 Route

파일:

- `app/src/main/java/com/example/nav3example/examples/modal/ModalRouteController.kt`
- `app/src/main/java/com/example/nav3example/examples/modal/ModalRouteExample.kt`
- `app/src/test/java/com/example/nav3example/examples/modal/ModalRouteControllerTest.kt`

필터, 사진 미리보기, 삭제 확인처럼 임시로 보이는 UI도 route로 모델링합니다. 이렇게 하면 뒤로 가기, 상태 복원, 취소/확인 처리를 일반 화면과 같은 규칙으로 다룰 수 있습니다.

## 10. 여행 예약 Flow

파일:

- `app/src/main/java/com/example/nav3example/examples/booking/BookingFlowController.kt`
- `app/src/main/java/com/example/nav3example/examples/booking/BookingFlowExample.kt`
- `app/src/test/java/com/example/nav3example/examples/booking/BookingFlowControllerTest.kt`

검색, 호텔 상세, 객실 선택, 로그인 gate, 결제, 예약 완료까지 이어지는 긴 도메인 flow입니다. 결제 전에 로그인 상태를 확인하고, 예약 완료 시 긴 중간 스택을 `Search > Receipt` 형태로 축약합니다.

## 11. 부모-자식 공유 상태

파일:

- `app/src/main/java/com/example/nav3example/examples/sharedstate/SharedStateController.kt`
- `app/src/main/java/com/example/nav3example/examples/sharedstate/SharedStateExample.kt`
- `app/src/test/java/com/example/nav3example/examples/sharedstate/SharedStateControllerTest.kt`

공식 shared ViewModel 패턴을 학습용으로 단순화한 예제입니다. 부모 route의 상태를 자식 route가 함께 변경하고, 독립 route는 별도 상태를 사용해 scope 차이를 보여줍니다.

## 현재 의존성 선택

- `androidx.navigation3:navigation3-runtime:1.1.4`
- `androidx.navigation3:navigation3-ui:1.1.4`
- `androidx.lifecycle:lifecycle-viewmodel-navigation3:2.11.0`
- `org.jetbrains.kotlinx:kotlinx-serialization-core:1.11.0`

이 프로젝트는 최신 alpha 채널 대신 Navigation 3의 최신 stable 릴리스를 사용합니다.
