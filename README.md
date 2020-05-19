# Spring-board
Spring Framework를 사용하여 게시판을 구현한다.

## Front-end
* Bootstrop3
* jQuery3

## Back-end
* Language : JAVA 1.8
* WEB Server : Apache
* WAS Server : Tomcat 8.5
* Framework : Spring 5.1.3
* build tool : Maven 3.5.1
* ORM : mybatis 3.4.6
* DB : Oracle 11g

# 기능 목록
공통 사항
  - spring security를 적용한다.
  - 발생할 수 있는 예외케이스에 대한 처리를 한다.
  - 목적에 맞는 request/response DTO 객체를 사용한다.
  - 에러 발생시 공통된 ErrorResponse를 리턴한다.
  - 권한에 따라 접근을 제한한다.
  - 유효성 검사는 서버에서 검사한다.
  - client 단에서 ErrorReponse 내의 errorCode에 따라 처리한다.
  
## 계정
회원가입 조건
1. 동일한 이메일을 사용할 수 없다.
2. 이메일을 제외한 나머지 정보는 중복 될 수 있다.
3. 이메일 서식에 맞아야 한다.
4. 비밀번호는 8자 이상의 문자,숫자,특수문자를 포함한다.
5. 비밀번호 재입력을 통해 비밀번호를 체크한다.
6. 생년월일(주민번호 앞자리) 서식에 맞게 입력해야한다.
7. 조건에 맞지 않을 경우 사용자에게 알려준다.


  * **회원가입**
    - 이메일 중복 여부를 확인한다.
    - 회원정보를 DB에 저장 후 회원가입 인증 이메일을 발송 한다.
    - 인증 링크 클릭 시 인증 테이블에서 인증키를 확인 후 계정을 활성화 한다.
  
  * **로그인/로그아웃**
    - 계정 존재 여부를 확인한다.
    - 비밀번호를 확인한다.
    - 이메일 인증 여부를 확인한다.
    - 계정 잠김 여부를 확인한다.
    - 로그인 실패가 3회가 되면 계정을 잠군다.
    - 로그인 실패 3회 전에 로그인에 성공하면 실패 카운트를 초기화한다.
    - 계정 상태(비활성화, 잠김)에 따른 인증 이메일을 발송/재발송 할 수 있다.

  * **아이디 찾기**
    - 입력한 이름, 생년월일(주민번호 앞자리)로 가입한 계정을 확인한다.
    - 가입날짜를 표시한다.
    
  * **비밀번호 찾기**
    - 계정을 잠군 후 비밀번호 재설정 이메일을 발송한다.
    - 계정이 잠김(Lock)상태인 경우 비밀번호 초기화를 통해 비밀번호를 재설정한다.
    - 비밀번호 초기화를 위한 이메일 인증을 발송한다.
    - 인증키와 옵션이 맞다면 비밀번호 재설정 페이지로 이동한다.
    - 비밀번호 재설정 후 계정 잠김 상태를 바꾼다.

  * **회원정보 보기**
    - 계정의 고유 번호를 통해 해당 사용자 정보를 가져온다.
    - 해당 유저가 쓴 글을 볼 수 있다.
    - 해당 유저가 쓴 글을 페이징처리하여 표시한다.
  
  * **회원정보 수정**
    - 회원정보(닉네임)가 수정되면 인증객체를 다시 생성한다.
    - 비밀번호를 변경할 수 있다.
    - 비밀번호 변경 후 로그아웃 처리 한다.
    
  * **인증 이메일 발송/재발송**
    - 회원가입/비밀번호 초기화 요청에 따라 인증 이메일을 보낸다.
    - 해당 요청에 관한 인증키로 인증처리를 한다.
    - 인증 이메일 재발송 처리 전 이미 인증을 완료한 상태라면 로그인 화면으로 돌아간다.
    - 인증키와 옵션이 다르거나 인증을 완료한 상태라면 유효하지 않은 요청으로 처리한다. 
    
## 게시판
공통 사항
  - Oracle + Mybatis환경 에서 Date값을 다루기 위해 CumstomDateHandler 등록
  - 게시글의 카테고리, 정렬, 검색, 페이징처리를 위한 값들을 받아오기위한 Dto를 사용한다.

* **게시글 목록 조회**
    - 중요한 공지사항의 경우 게시글 상단에 항상 표시되게 한다.
    - 일정 범위(중요한 공지사항을 포함한 갯수)의 게시글을 가져온다.
    - 페이징 처리를 한다.
    - 최신순,추천순,댓글순,조회순으로 정렬할 수 있다.
    - 작성날짜를 분단위까지 표시한다.
    
* **게시글 조회**
  
* **게시글 작성**
  
* **게시글 수정**
  
* **게시글 삭제**


## 댓글 
공통사항
  - 해당 게시글을 불러온 후 비동기 통신을 통해 댓글을 받아와 표시한다.

* **댓글 보기**

* **댓글 작성**
* **댓글 수정**
* **댓글 삭제**
* **추천 기능**
* **검색 기능**




--------------
(java.sql.Date 타입은 org.apache.ibatis.type.SqlDateTypeHandler를 통해 date 값을 yyyy-mm-dd로 반환 > json타입으로 받을경우 unixTime으로 받아와서 시,분,초를 표시할 수 없다. [해결 방법 : DateTypeHandler를 cumstom하여 등록한다.])

[ 참고 : https://taetaetae.github.io/2017/03/23/oracle-mybatis-date/ ]
