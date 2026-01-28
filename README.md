# 🔐 Anonymous Board (with Login System)

기존 익명 게시판에 **사용자 인증(회원가입/로그인) 시스템**을 도입하여 고도화한 프로젝트입니다.
`HttpSession`을 활용하여 로그인 상태를 관리하며, 인증된 사용자만이 글과 댓글을 작성할 수 있도록 권한을 분리했습니다.

## 🛠 Tech Stack
* **Backend:** Java 17, Spring Boot 3.x, Spring Data JPA
* **Database:** MySQL (or H2)
* **Frontend:** HTML5, CSS3, Vanilla JS (Fetch API)

## 🚀 Key Features
### 1. User (회원 관리)
- **회원가입:** 사용자의 아이디, 비밀번호, 닉네임을 받아 DB에 저장.
- **로그인/로그아웃:** 세션(Session) 기반의 인증 처리.
- **접근 제어:** 로그인하지 않은 사용자는 글 작성 및 수정/삭제 불가.

### 2. Comment (댓글 시스템)
- **작성자 식별:** 로그인한 유저 정보(User Entity)와 댓글을 연관 매핑.
- **권한 검증:** 댓글 삭제 시, 요청자와 작성자의 일치 여부를 서버에서 검증.
- **REST API:** `GET`, `POST`, `DELETE` 등의 메서드를 활용한 댓글 비동기 처리.

## 🚧 Future Improvements (개선 예정)
- [ ] **중복 검사:** 아이디 및 닉네임 중복 방지 로직 추가 예정.
- [ ] **유효성 검사:** 비밀번호 복잡도 등 입력값 검증 강화.
- [ ] **보안 강화:** 비밀번호 암호화(Spring Security) 적용.

## 📂 Project Structure
```text
src/main/java/com/example/anonymous_board
├── controller   # UserController, CommentController
├── entity       # User, Comment, Board
├── repository   # JPA Repository interfaces
└── service      # 비즈니스 로직 (UserService 등)
