# Spring-board
Spring Framework를 사용하여 게시판을 구현한다.

## Front-end
* Bootstrop3
* jQuery3

## Back-end
* Language : JAVA 1.8
* WEB Server : Apache
* WAS Server : Tomcat 8
* Framework : Spring 5.1.3
* build tool : Maven 3.5.1
* ORM : mybatis 3.4.6
* DB : Oracle 11g

# 기능 목록
공통 사항
  - 발생할 수 있는 예외케이스에 대한 처리를 한다.
  - 공통된 ErrorResponse를 리턴한다.
  
## 계정
회원가입 조건
1. 동일한 이메일을 사용할 수 없다.
2. 이메일 서식에 맞아야 한다.
3. 비밀번호는 8자 이상의 문자,숫자,특수문자를 포함한다.
4. 비밀번호 재입력을 통해 비밀번호를 확인한다.
5. 조건에 맞지 않을 경우 사용자에게 알려준다.
6. 인증 이메일이 발송되어야 한다.

  * **[spring security5] 회원가입**
    - 회원가입 페이지에서 POST요청 전에 이메일 서식에 맞는지, 비밀번호 조건이 맞는지 확인한다.
    - 비밀번호가 한글이면 영어로 치환한다.
    - Ajax를 이용하여 이메일 중복 여부를 확인한다.
    - 정보를 DB에 저장 후 인증 이메일을 발송 한다.
    - 인증 테이블에서 인증키를 확인 후 계정을 활성화 한다.
  
  * **[spring security5] 로그인/로그아웃**
    - 계정 존재 여부를 확인한다.
    - 비밀번호를 확인한다.
    - 이메일 인증 여부를 확인한다.
    - 계정 잠김 여부를 확인한다.
    - 로그인 실패가 3회가 되면 계정을 잠군다.
    - 로그인 실패 3회 전에 로그인에 성공하면 실패 카운트를 초기화한다.
    - 계정 상태(비활성화, 잠김)에 따른 인증 이메일을 재발송 할 수 있다.

  * **아이디 찾기**
    - 입력한 이름, 생년월일로 가입한 계정을 확인한다.
    - 가입날짜를 표시한다.
    
  * **비밀번호 재설정**
    - 계정이 잠김(Lock)상태인 경우 비밀번호 초기화를 통해 비밀번호를 재설정한다.
    - 비밀번호 초기화를 위한 이메일 인증을 발송한다.
    - 비밀번호 초기화를 진행 할 때, 인증키와 인증옵션을 같이 보내 처리한다.
    - 로그인 상태에서 비밀번호 재설정을 할 수 있다.
    - 로그인 상태에서 비밀번호 재설정 후 로그아웃한다.
    
## 게시판
공통 사항
  - 처음 페이지 이동을 제외한 모든 요청은 Ajax를 이용하여 통신하며 필요에 따라 json타입으로 받아와 처리한다.
  
  * **게시글 목록 조회**
    - 페이징 처리를 한다.
  * **게시글 조회**
  * **게시글 작성**
  * **게시글 수정**
  * **게시글 삭제**


* 댓글 CRUD
* 추천 기능
* 검색 기능
