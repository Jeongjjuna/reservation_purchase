# 예약구매 프로젝트

🎯 특정 시간대에 많은 주문요청이 발생할 때 발생할 수 있는 문제를 고민해보고 실습한 프로젝트 입니다.(2024.02 ~ 2024.02)

### 기술스택
SpringBoot, Spring Gateway, MySQL, Redis, JPA, Junit5

---

## 2. 프로젝트 구조

![architecture.png](docs%2Farchitecture.png)


<details>
<summary>프로젝트 패키지 구조 자세히 보기</summary>

```
apigateway_service
├── src
    ├──main/**
    │     ├── config // 설정파일
    │     ├── filter // 필터
    │     ├── response // 예외/응답
    │     ├── utill // 유틸


module_product_service
├── src
    ├──main/**
    │     ├── common
    │     ├── product
    │           ├── application
    │           ├── domain
    │           ├── infrastructure
    │           ├── presentation
    │
    ├──test/**
          ├── domain
          ├── presentation


module_order_service
├── src
    ├──main/**
    │     ├── common
    │     ├── order
    │           ├── application
    │           ├── domain
    │           ├── infrastructure
    │           ├── presentation
    │
    ├──test/**
          ├── domain
          ├── presentation
          
          
module_payment_service
├── src
    ├──main/**
    │     ├── common
    │     ├── payment
    │           ├── application
    │           ├── domain
    │           ├── infrastructure
    │           ├── presentation
    │
    ├──test/**
          ├── domain
          ├── presentation
          

module_stock_service
├── src
    ├──main/**
    │     ├── common
    │     ├── config
    │     ├── stock
    │           ├── application
    │           ├── domain
    │           ├── infrastructure
    │           ├── presentation
    │
    ├──test/**
          ├── domain
          ├── presentation

```
</details>



### 📁 패키지 구조
- apigateway_service
  - gateway 공통 인증 및 라우팅 (port : 8083)
- module_product_service
  - 상품 도메인 서비스 (port : 8085)
- module_order_service
  - 주문 도메인 서비스 (port : 8086)
- module_payment_service
  - 결제 도메인 서비스 (port : 8087)
- module_stock_service
  - 재고 관리 도메인 서비스 (port : 8088)

---

## 3. API 명세서
### 📎  [API 명세서](https://topaz-raincoat-203.notion.site/API-5f8b47128646401a8161196ef6f13d24?pvs=4)

---

## 4. ERD 다이어그램

![erd.png](docs%2Ferd.png)

---

## 5. 학습 내용
- 프로젝트 회고록 📝 [블로그 이동](https://wlgns2305.tistory.com/entry/%ED%9A%8C%EA%B3%A0-%EC%98%88%EC%95%BD-%EC%83%81%ED%92%88-MSA-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8)
- syncronized 임계영역 최소화 하기 📝 [블로그 이동](https://wlgns2305.tistory.com/entry/%EC%83%81%ED%92%88-%EC%9E%AC%EA%B3%A0-%EC%84%9C%EB%B9%84%EC%8A%A4-%EC%BA%90%EC%8B%B1%ED%95%98%EA%B8%B0)
- 재고 정보를 캐싱할 때 고려사항 📝 [블로그 이동](https://wlgns2305.tistory.com/entry/%EB%B0%B1%EC%97%94%EB%93%9C-%EC%A7%81%EB%AC%B4%EC%BA%A0%ED%94%84-3%EC%A3%BC%EC%B0%A8-%ED%9B%84%EA%B8%B0)
- redis를 활용한 재고 수량 동시성 문제 📝 [블로그 이동](https://wlgns2305.tistory.com/entry/%EC%A7%81%EB%AC%B4%EB%B6%80%ED%8A%B8%EC%BA%A0%ED%94%84-4%EC%A3%BC%EC%B0%A8-%ED%9B%84%EA%B8%B0)