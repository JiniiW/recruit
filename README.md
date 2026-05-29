# 🕶️ 공범 - 참여자 모집 게시판

> 고급웹프로그래밍 팀 프로젝트

## 기술 스택
- Spring Boot
- JPA / H2
- Mustache
- Bootstrap 5

## 실행 방법
1. 저장소 클론
2. IntelliJ 또는 IDE에서 프로젝트 열기
3. `RecruitApplication` 실행
4. 브라우저에서 `http://localhost:8080/recruits?userId=닉네임` 접속

## 테스트 계정
| userId | 역할 |
|--------|------|
| kong | 1번 글 작성자 |
| luna | 2번 글 작성자 |
| momo | 3번 글 작성자 |
| ryu | 4번 글 작성자 |
| nova | 5번 글 작성자 |

## 주요 기능
- 모집글 CRUD
- 참여 신청/취소 및 인원 자동 상태 전환 (OPEN/FULL/CLOSED)
- 마감시간 자동 CLOSED 처리
- 댓글 작성/삭제 (본인 댓글만 삭제 가능)
- 작성자/참여자 여부에 따른 버튼 분기
- userId 쿼리스트링 방식으로 사용자 식별

## API 목록
| Method | URL | 설명 |
|--------|-----|------|
| GET | /recruits | 모집글 목록 |
| GET | /recruits/{id} | 모집글 상세 |
| GET | /recruits/new | 모집글 작성 폼 |
| GET | /recruits/{id}/edit | 모집글 수정 폼 |
| POST | /api/recruits | 모집글 생성 |
| PATCH | /api/recruits/{id} | 모집글 수정 |
| DELETE | /api/recruits/{id} | 모집글 삭제 |
| POST | /api/recruits/{id}/join | 참여 신청 |
| DELETE | /api/recruits/{id}/leave | 참여 취소 |
| POST | /api/recruits/{id}/comments | 댓글 작성 |
| DELETE | /api/recruits/{id}/comments/{commentId} | 댓글 삭제 |
