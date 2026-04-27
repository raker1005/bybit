[README.md](https://github.com/user-attachments/files/27112930/README.md)
# Multi Exchange Crypto Monitor APK

Bybit / OKX 선물 급등 후보를 모니터링하는 Android WebView 앱입니다.

## 주요 기능

- Bybit / OKX 거래소 선택 드롭다운
- 자동 스캔 및 특정 코인 10분 상승/하락 분석
- 10분 상승 가능성 80% 이상 후보 표시
- 후보 등록 후 30초마다 확률 재분석
- 80% 아래로 내려가도 변화율 표시
- 고정추적 체크박스: 체크된 후보는 항상 상단 표시
- 최근 1분 상승률 / 체크 후 누적 상승률 표시
- 직전 1분/3분 하락, 긴 윗꼬리, VWAP 하회, 음봉 우세 등 단기 매도압력 필터 반영
- Android Native HTTP Bridge로 WebView CORS 문제 완화

## GitHub Actions로 APK 빌드하기

1. GitHub에서 새 Repository를 만듭니다.
2. 이 ZIP 파일을 압축 해제한 뒤 전체 파일을 Repository에 업로드합니다.
3. GitHub Repository 상단의 **Actions** 메뉴로 이동합니다.
4. **Build Android APK** 워크플로를 선택합니다.
5. **Run workflow** 버튼을 누릅니다.
6. 빌드가 끝나면 실행 결과 페이지 하단의 **Artifacts**에서 `multi-exchange-monitor-debug-apk`를 다운로드합니다.
7. 압축을 풀면 `app-debug.apk`가 있습니다.

## Android 설치

휴대폰으로 `app-debug.apk`를 옮겨 실행합니다. 설치가 막히면 Android 설정에서 “알 수 없는 앱 설치”를 허용하세요.

## 주의

앱에 표시되는 10분 상승 가능성 %는 실제 통계 승률이나 투자 수익 보장이 아닙니다. 거래량, OI, 펀딩비, 단기 캔들 상태를 점수화한 휴리스틱 추정값입니다. 실거래 전 반드시 차트, 호가, 손절 기준을 확인하세요.
