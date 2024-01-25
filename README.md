# 예약구매 프로젝트

## 도커 컴포즈 실행 명령어
```bash
docker-compose up -d
```


---
## 요구사항 정리

1. **유저 관리**:
    - 회원가입 기능을 통해 사용자는 계정을 생성할 수 있습니다.
        - [x]  회원가입 기능을 만든다.(이메일 인증 추후 진행)
        - [x]  비밀번호는 암호화하여 저장한다.
        - [x]  필수 요소: 이름, 프로필 이미지, 인사말
    - 로그인 및 로그아웃 기능을 통해 사용자는 편리하게 서비스를 이용할 수 있습니다.
        - [x]  jwt 토큰을 활용한 로그인 기능을 만든다.
        - [ ]  로그아웃 기능을 제공한다.(추후 진행)
    - 프로필 관리를 통해 사용자는 자신의 정보를 업데이트할 수 있습니다.
        - [x]  이름, 프로필 이미지, 인사말을 업데이트 할 수 있다.
        - [x]  비밀번호를 업데이트 할 수 있다.
    - 사용자는 자신의 회원정보를 조회할 수 있다.
        - [x]  회원 정보를 단건으로 조회할수 있다. 
2. **팔로우 관계:**
    - 사용자는 다른 사용자를 팔로우 할 수 있습니다.
        - [ ]  기능 구현 완료
    - 다른 사용자를 팔로우 하면 해당 사용자의 활동을 더 쉽게 확인할 수 있습니다.
        - [ ]  팔로우한 사용자의 활동이 나의 뉴스피드에 떠야 한다.
            - [ ]  포스트
            - [ ]  댓글 ex. B님이 C님의 글에 댓글을 남겼습니다.
            - [ ]  상호작용 ex. B님이 C님의 글을 좋아합니다.
            - [ ]  팔로우 활동 ex. B님이 C님을 팔로우 합니다.
3. **포스트 및 뉴스피드:**
    - 사용자는 텍스트 기반의 포스트를 작성할 수 있습니다.
        - [ ]  기능 구현 완료
    - 뉴스피드는 팔로우한 사용자들의 포스트 및 소식을 보여주어 사용자가 소식을 신속하게 받을 수 있습니다.
        - [ ]  뉴스피드를 최신 순으로 보여준다.
        - [ ]  팔로우한 사용자들의 활동 (2. 팔로우 관계 섹션 참고)
        - [ ]  나의 팔로우 활동
            - [ ]  나를 팔로우 하는 사용자 소식
        - [ ]  나의 포스트
            - [ ]  나의 포스트에 남겨진 댓글 ex. B님이 OO 포스트에 댓글을 남겼습니다.
            - [ ]  나의 포스트의 상호작용 ex. B 님이 OO 포스트를 좋아합니다.
4. **댓글 및 상호 작용:**
    - 포스트에 댓글을 작성할 수 있습니다.
        - [ ]  기능 구현 완료
    - 포스트 및 댓글에 좋아요를 누를 수 있습니다.
        - [ ]  기능 구현 완료