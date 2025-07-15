## ✅ 프로젝트 개요
배포 주소: https://wordbattle.co.kr 
아직 개발 중으로 배포만 되어 있는 상태입니다.

**WordBattle**은 실시간 채팅, 게임, 퀴즈, 방 생성 및 참여 기능을 제공하는 **멀티 플레이어 단어 배틀 게임**입니다.

- 실시간 상호작용을 위한 WebSocket 기반 통신
- JWT 기반 인증 시스템 + Redis로 RefreshToken 관리
- 방 생성, 게임 시작, 퀴즈 제출 등 도메인 중심 아키텍처
- Docker/Nginx 기반 배포 환경 구성

---

## 🛠 기술 스택

- **Language**: Java 21
- **Framework**: Spring Boot, Spring Security
- **Real-time**: WebSocket (Stomp)
- **Auth**: JWT, Redis (RefreshToken & Blacklist)
- **Infra/Deploy**: Docker, Nginx (Reverse Proxy), AWS EC2
- **Database**: PostgreSQL (JPA/Hibernate)
- **Documentation**: Swagger
- **Test**: JUnit

---

## 🔧 주요 기능

### 1. 사용자 인증 및 관리

- JWT 기반 자체 로그인 구현
- CustomUserDetailsService로 사용자 정보 커스터마이징
- 로그인 시 AccessToken/RefreshToken 발급 및 Redis 저장
- 로그아웃 시 AccessToken 블랙리스트 등록

### 2. 게임 시스템

- GameService: 게임 시작/진행/종료, 점수 집계
- WebSocket으로 실시간 게임 상태 동기화
- GameController: REST API 제공

### 3. 채팅 시스템

- WebSocket 기반 실시간 채팅 구현
- ChatService: 채팅 저장 및 정답 체크

### 4. 퀴즈 시스템

- QuizService: 퀴즈 랜덤 출제, 정답 검증
- QuizController: 퀴즈 API 제공

### 5. 방 시스템

- RoomService: 방 생성/입장/조회
- RoomController: 방 관련 REST API 제공

### 6. 공통 인프라 및 예외 처리

- GlobalExceptionHandler, ErrorCode 기반 일관된 예외 처리
- JwtAuthenticationFilter로 인증 필터링
- WebSocketConfig로 WebSocket 엔드포인트 설정
- Swagger UI 자동 문서화

---

## 🧱 폴더 구조 및 아키텍처

- chat/ → 채팅 도메인
- game/ → 게임 도메인
- quiz/ → 퀴즈 도메인
- room/ → 방 도메인
- user/ → 사용자 도메인
- common/ → 전역 설정, 예외, 유틸 등

> 설계 패턴: 헥사고날 아키텍처 기반 도메인 중심 설계 (Adapter/Application/Domain 계층 분리)
> 

---

## ✅ 주요 성과

- **JWT + Redis 기반 인증/보안 처리**
    - RefreshToken 저장 + AccessToken 블랙리스트 적용으로 보안성 강화
- **도메인 기반 설계 (DDD 지향)**
    - 명확한 책임 분리, 유연한 유지보수/확장 구조
- **단위 테스트 작성 (JUnit)**
    - 서비스 로직 단위 테스트 도입으로 안정성 확보
- **Docker 기반 배포 & Nginx 연동**
    - CI/CD를 염두에 둔 Dockerfile 구성 및 운영 환경 설정

---

## 📈 향후 개선 계획

- 게임 로직 개선 및 유저 간 랭킹 시스템 도입
- OAuth2 로그인 (Google, Kakao 등) 추가
