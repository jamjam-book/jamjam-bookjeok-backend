<div align="center">

# "책으로 Book적, 이야기로 Book적"

<br>

<img src="readme-images/Book적Book적_logo.png" alt="Book적Book적 로고" />

</div>

## 📚 목차
- [👥 팀원 소개](#-팀원-소개)
- [📌 프로젝트 소개](#-프로젝트-소개)
  - [1. 프로젝트 개요](#1-프로젝트-개요)
  - [2. 주요 기능](#2-주요-기능)
  - [3. 타 서비스와의 차별점](#3-타-서비스와의-차별점)
- [📄 화면 명세서 ](#-화면-명세서)
  - [1. 화면 셀계도](#1-화면-설계도)
  - [2. 기능 명세서 ](#2-기능-명세서)
- [📁 Vue 폴더 구조 ](#-Vue-폴더-구조)
- [🧪 테스트 결과 보고서](#-테스트-결과-보고서)
- [🔫 트러블 슈팅](#-트러블-슈팅)
- [💬 팀원 회고](#-팀원-회고)
<br>

## 👥 **팀원 소개**

---
| <img width="120" src="https://github.com/wishbornDev.png" /> | <img width="120" src="https://github.com/Jayboo816.png" /> | <img width="120" src="https://github.com/memory-h.png" /> | <img width="120" src="https://github.com/devyujinjeong.png" /> |
|:---:|:---:|:---:|:---:|
| **김소원**<br>[github.com/wishbornDev](https://github.com/wishbornDev) | **부재녕**<br>[github.com/Jayboo816](https://github.com/Jayboo816) | **장현영**<br>[github.com/memory-h](https://github.com/memory-h) | **정유진**<br>[github.com/devyujinjeong](https://github.com/devyujinjeong) |
<br>

## 📌 **프로젝트 소개**

---
### 1. 프로젝트 개요

#### 1.1 서비스 개발 동기
[책으로 Book적, 이야기로 Book적]

> ***‘텍스트 힙’ 이란 글자(text)와 멋지다(hip)를 결합한 말***로, 독서가 멋진 유행으로 자리 잡고 있음을 뜻하는 신조어입니다. 최근 MZ 세대를 중심으로 이러한 문화가 유행하며 독서를 취미로 즐기는 사람들이 늘어나고 있습니다.

종이책의 감성을 선호하거나, 인스타그램·블로그 등을 통해 **자신의 독서 기록을 공유**하는 문화도 함께 확산되고 있습니다.

하지만 **도서를 구매하는 플랫폼과 독서 내용을 나누는 공간이 분리**되어 있어, 독서 활동이 자연스럽게 이어지기 어려운 점이 있습니다.

이에 따라, <span style="background-color: #fff3b0"><u><b>도서 구매부터 독서 공유까지 한 번에 이어지는 플랫폼</b></u></span>
을 만들고자 **Book적Book적** 서비스를 기획하게 되었습니다.

![1_Book적Book적 기획 의도.png](readme-images/1_Book%EC%A0%81Book%EC%A0%81%20%EA%B8%B0%ED%9A%8D%20%EC%9D%98%EB%8F%84.png)
![2_Book적Book적 기획의도.png](readme-images/2_Book%EC%A0%81Book%EC%A0%81%20%EA%B8%B0%ED%9A%8D%EC%9D%98%EB%8F%84.png)

#### 1.2 서비스 설명

> **Book적Book적**은 책을 중심으로 한 웹 기반 도서 서비스입니다.

사용자는 다양한 도서를 검색하고 상세 정보를 확인하거나 구매할 수 있으며, **커뮤니티 기능을 통해 책에 대한 생각과 경험을 자유롭게 공유**할 수 있습니다.

또한, 팔로우 기능을 통해 다른 사용자의  커뮤니티 활동을 확인하며, 책을 매개로 소통하며 **자연스럽게 독서 습관을 형성**해 나갈 수 있습니다.

#### 1.3 서비스 목표

✅ 사용자의 **독서 경험을 풍부하게** 하고, **자연스러운 독서 습관 형성**을 지원합니다.

✅ 책을 좋아하는 사람들이 **서로 소통하고 의견을 나눌 수 있는 커뮤니티 공간**을 마련합니다.

✅ **팔로우 기능을 통한 네트워크 형성**으로, 개인의 독서 활동이 더 확장될 수 있도록 유도합니다.

### 2. 주요 기능

---

#### 2.1 회원 관리

- **회원가입 및 로그인**
  - 회원가입과 로그인을 할 수 있습니다.
  - 비밀번호 찾기 기능을 제공합니다.
- **회원 정보 수정**
  - 마이페이지에서 회원 정보와 활동 내역을 확인하고 수정할 수 있습니다.
  - 사용자는 관심 도서와 관심 작가를 등록하거나 삭제할 수 있습니다.

#### 2.2 도서 정보 조회 및 구매

- **도서 검색 및 조회**
  - 도서 API를 연동하여 도서 목록을 확인할 수 있습니다.
  - 도서명, 저자명, 출판사명 등의 키워드를 기반으로 검색할 수 있습니다.
  - 도서 상세 페이지에서 책 소개, 저자 및 출판사 정보, 구매자 리뷰를 확인할 수 있습니다.
- **장바구니 및 결제**
  - 도서를 장바구니에 담아 한 번에 결제할 수 있습니다.
  - 결제가 완료되면 주문 완료 내역을 확인할 수 있습니다.
  - 주문 내역과 주문 상세 내역은 마이페이지에서 확인할 수 있습니다.
  - 결제 정보는 주문 상세 내역에서 확인할 수 있습니다.

#### 2.3 소셜 기능

- **팔로우/팔로워 기능**
  - 관심 있는 사용자의 독서 활동을 팔로우할 수 있습니다.
  - 팔로우한 사용자의 게시글을 피드를 통해 확인할 수 있습니다.
  - 마이페이지에서 팔로우 목록과 활동 내역을 관리할 수 있습니다.
- **독서 커뮤니티**
  - 누구나 게시글을 올릴 수 있는 자유 게시판 형태의 커뮤니티를 운영합니다.
  - 독서 감상, 추천 도서, 책에 대한 질문 등을 자유롭게 공유할 수 있습니다.
  - 댓글을 통해 사용자 간 소통이 가능합니다.
  - 커뮤니티 활동 내역은 마이페이지에서 확인할 수 있습니다.
<br>

## 📄 **명세서**

---

| 이름     | 링크                                                                                                                                         |
|--------|--------------------------------------------------------------------------------------------------------------------------------------------|
| 화면 설계서 | 🌈 [Figma](https://www.figma.com/design/wtXTmoVEQePLz7LMrVaG3H/bookjeok?node-id=0-1&p=f&t=PKx14lhvixRJlw3S-0)                              |
| 기능 명세서 | 📑 [Google Sheets](https://docs.google.com/spreadsheets/d/1CgDkMdtn4kikCn2f16_u4lIwumk31a9M5Qa3kMu1yj0/edit?gid=1152653603#gid=1152653603) |

### 1. 화면 설계서 with.Figma
![화면 설계도.png](readme-images/%ED%99%94%EB%A9%B4%20%EC%84%A4%EA%B3%84%EB%8F%84.png)

### 2. 기능 명세서
![기능 명세서 1.png](readme-images/%EA%B8%B0%EB%8A%A5%20%EB%AA%85%EC%84%B8%EC%84%9C%201.png)
![기능 명세서 2.png](readme-images/%EA%B8%B0%EB%8A%A5%20%EB%AA%85%EC%84%B8%EC%84%9C%202.png)
<br>

## 📁 **Vue 폴더 구조**

---
```text
src/
├── assets/                       # 정적 자원 (이미지, 아이콘, 스타일시트 등)
│   ├── css/                     # CSS 파일들
│   ├── icons/                   # 아이콘 파일들
│   └── images/                  # 이미지 파일들
│
├── components/                  # 공통 컴포넌트 모음
│   ├── common/                  # 재사용 UI (버튼, 모달, 검색 바 등)
│   └── layout/                  # 레이아웃 구성 요소 (헤더, 푸터 등)
│
├── features/                    # 기능별 도메인 폴더
│   ├── admin/                   # 관리자 관련 기능
│   │   ├── components/          # 관리자 전용 컴포넌트들
│   │   ├── views/               # 관리자 화면 페이지들
│   │   ├── api                  # 관리자 관련 API 모듈
│   │   └── router.js            # 관리자 관련 라우터 설정
│   │
│   ├── book/                    # 도서 관련 기능
│   │   ├── components/          # 도서 관련 컴포넌트들
│   │   ├── views/               # 도서 관련 페이지들
│   │   ├── api                  # 도서 관련 API 모듈
│   │   └── router.js            # 도서 관련 라우터 설정
│   │
│   ├── cart/                    # 장바구니 관련 기능
│   │   ├── components/          # 장바구니 컴포넌트들
│   │   ├── views/               # 장바구니 페이지들
│   │   ├── api                  # 장바구니 관련 API 모듈
│   │   └── router.js            # 장바구니 관련 라우터 설정
│   │
│   ├── main/                    # 메인 페이지 관련 기능
│   │   ├── components/          # 메인 페이지 컴포넌트들
│   │   ├── views/               # 메인 페이지들
│   │   └── router.js            # 메인 페이지 라우터 설정
│   │
│   ├── member/                  # 회원 관련 기능
│   │   ├── components/          # 회원 전용 컴포넌트들
│   │   ├── views/               # 회원 페이지들
│   │   ├── api                  # 회원 관련 API 모듈
│   │   └── router.js            # 회원 관련 라우터 설정
│   │
│   ├── order/                   # 주문 관련 기능
│   │   ├── components/          # 주문 관련 컴포넌트들
│   │   ├── views/               # 주문 페이지들
│   │   ├── api                  # 주문 관련 API 모듈
│   │   └── router.js            # 주문 관련 라우터 설정
│   │
│   ├── post/                    # 게시글 관련 기능
│   │   ├── components/          # 게시글 컴포넌트들
│   │   ├── views/               # 게시글 페이지들
│   │   ├── api                  # 게시글 관련 API 모듈
│   │   └── router.js            # 게시글 관련 라우터 설정
│   │
│   └── question/                # 질문 게시판 관련 기능
│       ├── components/          # 질문 게시판 컴포넌트들
│       ├── views/               # 질문 게시판 페이지들
│       ├── api                  # 질문 게시판 관련 API 모듈
│       └── router.js            # 질문 게시판 관련 라우터 설정
│
├── lib/                         # 라이브러리 및 유틸리티 파일들
│   └── axios.js                 # Axios 설정 파일
│
├── router/                      # 전역 라우터 설정
│   └── index.js                 # 전체 라우터를 모아서 Vue Router 생성
│
├── stores/                      # 상태 관리 (Pinia)
│   └── auth/                    # 인증 관련 스토어
│
├── App.vue                      # 루트 컴포넌트
└── main.js                      # 앱 초기 진입점, 루트 인스턴스 생성
```
<br>

## 🧪 **테스트 결과 보고서**

---

### 1. 메인 페이지
<details>
  <summary>메인 페이지</summary>
  <img src="readme-images/메인 페이지/메인페이지.gif" alt="메인 페이지" />
</details>

### 2. 로그인 / 회원가입 / 비밀번호 찾기
<details>
  <summary>로그인</summary>
</details>
<details>
  <summary>회원가입</summary>
</details>
<details>
  <summary>비밀번호 찾기</summary>
  <details>
    <summary>비밀번호 재설정 링크 요청</summary>
    <img src="readme-images/회원 정보/비밀번호 재설정 링크 요청.gif" alt="비밀번호 재설정 링크 요청" />
  </details>
  <details>
    <summary>비밀번호 재설정</summary>
    <img src="readme-images/회원 정보/비밀번호 재설정 하기.gif" alt="비밀번호 재설정" />
  </details>
</details>

### 3. 마이 페이지
<details>
  <summary>프로필 관리</summary>
</details>
<details>
  <summary>비밀번호 변경</summary>
</details>
<details>
  <summary>팔로잉 목록</summary>
  <img src="readme-images/관심 및 팔로잉/팔로잉 목록 조회 및 삭제.gif" alt="팔로잉 목록" />
</details>
<details>
  <summary>주문 내역</summary>
  <img src="readme-images/주문, 장바구니/주문 상세 내역.gif" alt="주문 내역" />
</details>
<details>
  <summary>관심 목록</summary>
  <img src="readme-images/관심 및 팔로잉/관심 목록 조회 및 삭제.gif" alt="관심 목록" />
</details>
<details>
  <summary>문의사항</summary>
</details>

### 4. 도서 페이지
<details>
  <summary>도서 Top10</summary>
  <img src="readme-images/도서/도서 top 10.gif" alt="도서 Top10" />
</details>
<details>
  <summary>도서 목록 조회 및 검색</summary>
  <img src="readme-images/도서/도서 검색.gif" alt="도서 목록" />
</details>
<details>
  <summary>도서 상세</summary>
  <img src="readme-images/도서/도서 상세 정보.gif" alt="도서 상세" />
</details>

### 5. 주문 / 결제 페이지
<details>
  <summary>장바구니</summary>
  <details>
    <summary>도서 추가</summary>
    <img src="readme-images/주문, 장바구니/장바구니 도서 추가.gif" alt="장바구니 도서 추가" />
  </details>
  <details>
    <summary>도서 수량 수정</summary>
    <img src="readme-images/주문, 장바구니/장바구니 도서 수량 수정.gif" alt="장바구니 도서 수량 수정" />
  </details>
  <details>
    <summary>도서 삭제</summary>
    <img src="readme-images/주문, 장바구니/장바구니 도서 삭제.gif" alt="장바구니 도서 삭제" />
  </details>
</details>
<details>
  <summary>주문 / 결제</summary>
  <details>
    <summary>장바구니 주문</summary>
    <img src="readme-images/주문, 장바구니/장바구니 주문.gif" alt="장바구니 주문" />
  </details>
  <details>
    <summary>주문 내역 조회</summary>
    <img src="readme-images/주문, 장바구니/주문 내역 조회.gif" alt="주문 내역 조회" />
  </details>
</details>

### 6. 게시글
<details>
  <summary>게시글 목록 조회</summary>
  <img src="readme-images/게시글/게시글 목록 조회.gif" alt="게시글 목록 조회" />
</details>

### 7. 관리자 페이지
<details>
  <summary>회원 관리</summary>
  <details>
    <summary>회원 조회</summary>
    <img src="readme-images/관리자/회원 정보 조회.gif" alt="회원 정보 조회" />
  </details>
  <details>
    <summary>회원 수정</summary>
    <img src="readme-images/관리자/회원 정보 수정.gif" alt="회원 정보 수정" />
  </details>
</details>
<details>
  <summary>도서 관리</summary>
  <details>
    <summary>도서 조회, 등록, 수정</summary>
    <img src="readme-images/관리자/도서 조회, 등록, 수정.gif" alt="도서 조회, 등록, 수정" />
  </details>
</details>

<br>

## 🔫 **트러블 슈팅**
### 1. 새로고침 시 발생하는 문제 
- **문제 상황** <br>
  - 관리자 페이지는 header와 footer가 없는 페이지인데, 새로고침 시 header와 footer가 다시 노출되는 문제 발생
  - 로그를 찍어 확인하니 새로고침 시 /admin/members 경로에서 isAdminRoute가 false로 나와 admin 경로로 인식되지 않음.
![트러블 슈팅 1 - 문제 상황.png](readme-images/%ED%8A%B8%EB%9F%AC%EB%B8%94%20%EC%8A%88%ED%8C%85%201%20-%20%EB%AC%B8%EC%A0%9C%20%EC%83%81%ED%99%A9.png)
- **원인**
  - `const isAdminRoute = route.path.startsWith('/admin')` 처럼 일반 변수로 정의하면, 해당 코드는 컴포넌트가 마운트될 때 한 번만 실행됨.
  - 이 시점에서 route.path가 아직 제대로 초기화되지 않으면 isAdminRoute가 false로 고정되고, 이후 경로가 바뀌어도 반영되지 않음
- **해결 방법**
  - computed는 반응형으로 route.path에 의존하여 경로 값이 변경되면 자동으로 isAdminRoute를 갱신함.
  - `computed(() => route.path.startsWith('/admin'))`처럼 정의하면 route.path가 업데이트될 때마다 isAdminRoute가 실시간으로 반영됨.

### 2. 부모-자식 컴포넌트 간 전파 문제
- **문제 상황**
- **원인**
- **해결 방법**

## 💬 **팀원 회고**

---
| **팀원**  | **프로젝트 회고**                                                                                                                                                                                                                                                                                        |
|---------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **김소원** ||
| **부재녕** ||
| **장현영** ||
| **정유진** ||
