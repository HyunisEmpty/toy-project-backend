# 📝 Memo App

JWT 기반 로그인과 메모 관리 기능을 구현한 Spring Boot 토이 프로젝트입니다.

단순 CRUD 구현에서 끝나는 것이 아니라 **Spring Security**, **JWT 인증**, **JPA 연관관계**, **N+1 문제와 Fetch Join을 통한 성능 개선**까지 경험하는 것을 목표로 개발하였습니다.

---

# 🚀 프로젝트 소개

사용자는 회원가입 및 로그인을 통해 인증을 수행하고, 자신만의 메모를 작성 및 조회할 수 있습니다.

추가적으로 전체 메모 게시판을 구현하여 다른 사용자의 메모도 조회할 수 있으며, 이 과정에서 발생하는 **JPA N+1 문제를 직접 재현하고 Fetch Join으로 해결**하였습니다.

---

# 🛠️ Tech Stack

### Backend

- Java 17
- Spring Boot 4
- Spring Security
- Spring Data JPA
- Hibernate
- JWT (jjwt)

### Database

- H2 Database

### Frontend

- HTML
- CSS
- JavaScript (Vanilla JS)

---

# 📂 프로젝트 구조

```
src
├── config
├── controller
├── domain
├── dto
├── jwt
├── repository
├── security
└── service
```

---

# 📌 주요 기능

## 회원

- 회원가입
- 로그인(JWT)
- 현재 로그인 사용자 조회

## 메모

- 메모 작성
- 내 메모 조회
- 전체 메모 조회

---

# 🗄️ ERD

```
User
-------------------------
id
username
password
created_at

        1
        │
        │
        │
        N

Memo
-------------------------
id
title
content
created_at
user_id (FK)
```

---

# 🔐 인증 방식

- JWT Access Token 기반 인증
- Spring Security Filter를 이용한 인증 처리
- SecurityContext에 인증 정보 저장

---

# 📡 API

| Method | URL | 설명 |
|---------|-----|------|
| POST | /api/auth/signup | 회원가입 |
| POST | /api/auth/login | 로그인 |
| GET | /api/users/me | 로그인 사용자 조회 |
| POST | /api/memos | 메모 작성 |
| GET | /api/memos | 내 메모 조회 |
| GET | /api/memos/all | 전체 메모 조회 |

---

# 🔥 트러블 슈팅

## JPA N+1 문제

### 문제

게시판 기능을 구현하면서 모든 메모와 작성자를 함께 조회하기 위해 아래와 같이 작성하였습니다.

```java
List<Memo> memos = memoRepository.findAll();

return memos.stream()
        .map(memo -> new MemoResponse(
                memo.getId(),
                memo.getTitle(),
                memo.getContent(),
                memo.getUser().getUsername(),
                memo.getCreatedAt()
        ))
        .toList();
```

Hibernate SQL 로그를 확인한 결과 Memo 조회 이후 User 조회가 반복적으로 발생하는 현상을 발견했습니다.

### 원인

`@ManyToOne(fetch = FetchType.LAZY)` 환경에서

```java
memo.getUser().getUsername()
```

를 호출하면서 Lazy Loading이 발생하여 N+1 문제가 발생했습니다.

### 해결

Repository에 Fetch Join을 적용하였습니다.

```java
@Query("""
    select m
    from Memo m
    join fetch m.user
""")
List<Memo> findAllWithUser();
```

Service에서는

```java
memoRepository.findAllWithUser();
```

를 호출하도록 변경하였습니다.

### 결과

#### Before

```
SELECT Memo

↓

SELECT User

↓

SELECT User

↓

SELECT User ...
```

#### After

```
SELECT Memo
JOIN User
```

조회 쿼리를 하나로 줄여 불필요한 SQL 실행을 제거하였습니다.

---

# 📖 프로젝트를 통해 배운 점

- Spring Security 인증 흐름
- JWT 기반 로그인 구현
- JPA Entity 설계
- 연관관계 매핑
- Lazy Loading
- N+1 문제 원인 분석
- Fetch Join을 통한 성능 개선
- Hibernate SQL 로그 분석

---

# 📅 개발 기간

2026.08

---

# 👨‍💻 개발자

박현빈
