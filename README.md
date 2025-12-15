# Daybreak-FrontEnd

##  Git Convention

### TYPE
- [feat] : 새로운 기능 구현
- [fix] : 버그 및 오류 해결
- [UI] : UI 작업
- [docs] :  README나 WIKI 등의 문서 개정
- [refactor] : 리팩토링 (기능 변화 없음)


### COMMIT
&emsp;타입: 커밋메시지 (#이슈번호)

&emsp;→ ':' 앞은 띄어쓰기 없음, 뒤로 띄어쓰기 필수


### branch 이름
&emsp;브램치 이름 구조: <타입/기능명>

&emsp;→ 브랜치는 기능별로 나눠서 하기!

&emsp;ex) UI/홈


### Pull Request
- PR 생성 규칙
  - dev 브랜치로만 PR 생성
  - PR 제목은 커밋 메시지 규칙과 동일하게 작성
- PR 내용
  - 작업 내용 요약
  - 관련 이유 번호 명시
- 병합 규칙
  - 충돌 발생 시 PR 생성자가 해결


## Coding Convention

### 주석 작성
- 주석 처리 꼼꼼하게 하기
  ```
  // 친구 요청 API 호출 (서버 스펙상 POST 필요)
  requestFriend();
  ```
- 코드 설명용 주석 ❌
  ```
  ❌ // i에 1을 더한다
  i++;
  ```

### 네이밍
- 변수/함수
  - camelCase 사용
    ``` 
    userName
    getUserInfo()
    loadFriendList()
    ```
  - 의미 없는 이름 사용 금지
    ```
    ❌ a, b, temp, data
    ⭕ userList, friendCount
    ```
- 클래스/컴포넌트
  - PascalCase 사용
    ```
    LoginActivity
    HomeFragment
    UserService
    ```
- 상수
  - 대문자 + SNAKE_CASE
    ```
    static final int MAX_COUNT = 10;
    ```
 
### 파일 & 폴더 구조
- 파일명은 역할이 명확하게 드러나도록 작성
  ```
  LoginActivity
  LoginViewModel
  LoginRepository
  ```
- 기능 단위로 패키지 구성
  ```
  login/
  friend/
  calendar/
  ```

### 로그
- 디버그용 로그는 커밋 전 제거
 
  ### 코드 리뷰 기준
  - 네이밍이 직관적인가
  - 중복 코드는 없는가
  - 불필요한 주석/로그가 없는가
  - 컨벤션이 지켜졌는가
