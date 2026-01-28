# 📝 Anonymous Board (익명 게시판)

누구나 자유롭게 글을 쓰고 댓글을 달 수 있는 **익명 게시판** 프로젝트입니다.
별도의 회원가입 절차 없이 접근성을 높였으며, 게시글과 댓글의 기본적인 CRUD(생성, 조회, 수정, 삭제) 기능을 구현하는 데 집중했습니다.

## 🛠 Tech Stack
* **Java 17 / Spring Boot 3.x**
* **Spring Data JPA / H2 Database**
* **Thymeleaf / HTML / CSS / JS**

## 🚀 Key Features
1. **게시글 관리**
   - 익명으로 제목과 내용을 작성하여 게시글 등록
   - 전체 게시글 목록 조회 및 상세 조회
   - 게시글 수정 및 삭제
2. **댓글 시스템**
   - 게시글에 대한 댓글 작성 및 조회
   - 비동기 통신(Fetch API)을 이용한 댓글 로딩

## 📂 API Reference
| Method | URL | Description |
| :--- | :--- | :--- |
| GET | `/board/list` | 전체 게시글 목록 |
| POST | `/board/write` | 게시글 작성 |
| GET | `/api/comment/list` | 댓글 목록 조회 |
| POST | `/api/comment/create` | 댓글 작성 |
