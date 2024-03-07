# 예약구매 프로젝트

특정 시간대에 몰리는 주문요청에 대해 발생할 수 있는 문제를 고민해보고 실습한 프로젝트 입니다.(2024.02 ~ 2024.02)

SpringBoot, Spring Gateway, MySQL, Redis, JPA, Junit5

## 1. Project Structure
```
apigateway_service
├── src
    ├──main/**
    │     ├── config // 설정파일
    │     ├── filter // 필터
    │     ├── response // 예외/응답
    │     ├── utill // 유틸


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


module_user_service
├── src
    ├──main/**
    │     ├── auth
    │     ├── common
    │     ├── config
    │     ├── member
    │            ├── application
    │            ├── domain
    │            ├── infrastructure
    │            ├── presentation
    │
    ├──test/**
          ├── domain
          ├── presentation

```

### 프로젝트 패키지 구조


### main
- apigateway_service
  - gateway 공통 인증 및 라우팅
- module_order_service
  - 주문 도메인 서비스(서버)
- module_payment_service
  - 결제 도메인 서비스(서버)
- module_product_service
  - 상품 도메인 서비스(서버)
- module_user_service
  - 유저 도메인 서비스(서버)