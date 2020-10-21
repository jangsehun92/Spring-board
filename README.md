# 게시판 프로젝트 소개
단순 CRUD 게시판이 아닌, 게시판과 관련된 여러 기능을 구현한다. 

## Front-end
* JavaScript
* Bootstrop3
* jQuery3

## Back-end
* Language : JAVA 1.8
* WEB Server : Apache
* WAS Server : Tomcat 8.5
* Framework : Spring 5.1.3
* View : JSP
* build tool : Maven 3.5.1
* ORM : mybatis 3.4.6
* DB : Oracle 11g
* Security : spring security 5.2.2

# 기능 목록
프로젝트 공통 사항
  - spring security를 적용한다.
    1. 권한/접근 처리를 한다.
  - csrf를 활성화 한다. 
    1. 공통적으로 사용하는 header 페이지 내에 meta 태그를 사용하여 csrf 값을 셋팅한다.
    2. 비동기 요청인 경우 ajax_header.js 내에서 ajaxSend를 통해 csrf token값을 셋팅한다.
  - 목적에 맞는 request/response DTO 객체를 사용한다.
  - 예외를 한곳에서 일관성 있게 처리하기 위해 @ControllerAdvice 어노테이션을 적용한 GlobalExceptionHandler클래스에서 exception을 처리한다.(https://github.com/jangsehun92/Spring-board/blob/master/src/main/java/jsh/project/board/global/error/GlobalExceptionHandler.java)
    1. 비동기 요청은 ErrorResponse를 리턴한다.
    2. 비동기 요청이 아닐 경우 Error코드와 메시지를 출력하고, 메인페이지로 redirect 한다.
    
    (참고 : https://github.com/cheese10yun/spring-guide/blob/master/docs/exception-guide.md)
  - 유효성 검사는 서버에서 검사한다.
  - @transactional을 적용하여 Unchecked Exception이 발생하면 관련 작업(DB)을 롤백한다.
  - client 단에서 ErrorReponse 내의 errorCode에 따라 처리한다.
  - Mockito를 이용하여 테스트 코드를 작성한다.
-------
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
    - 인증 이메일 내의 링크 클릭 시 인증정보 확인 후 계정을 활성화 한다.
  
  * **로그인/로그아웃**
    - 계정 존재 여부를 확인한다.
    - 비밀번호를 확인한다.
    - 이메일 인증 여부를 확인한다.
    - 계정 잠김 여부를 확인한다.
    - 로그인 실패가 3회가 되면 계정을 잠군다.
    - 로그인 실패 3회 전에 로그인에 성공하면 로그인 실패 카운트를 초기화한다.
    - 계정 상태(비활성화, 잠김)에 따라 관련 페이지로 이동시킨다.
    - 로그아웃에 성공시 메인페이지로 이동한다.
    - 로그아웃시 쿠키와 세션을 지운다.

  * **아이디 찾기**
    - 가입한 이름, 생년월일(주민번호 앞자리)로 가입한 계정을 확인한다.
    - 가입날짜를 표시한다.
    - 비밀번호 찾기 버튼을 표시한다.
    
  * **비밀번호 찾기**
    - 가입한 email로 비밀번호를 찾을 수 있다.
    - 해당 계정을 잠군 후 비밀번호 재설정 이메일을 발송한다.
    - 인증정보 확인 후 비밀번호 재설정 페이지로 이동한다.
    - 비밀번호 재설정 완료 후 계정 잠김 상태를 바꾼다.

  * **회원정보 보기**
    - 계정의 고유 번호를 통해 해당 사용자 정보를 가져온다.
    - 해당 유저가 쓴 글을 페이징 처리하여 표시한다.
  
  * **회원정보 수정**
    - 닉네임을 변경 할 수 있다.
    - 닉네임이 변경 되면 인증객체를 다시 생성한다.
    - 비밀번호를 변경할 수 있다.
    - 비밀번호 변경 후 로그아웃 처리 한다.
    
  * **인증 이메일 발송/재발송**
    - 회원가입/계정 잠김(비밀번호 재설정 요청)에 따라 인증 이메일을 보낸다.
    - 해당 요청에 관한 인증키로 인증처리를 한다.
    - 인증 이메일 재발송 하기 전 인증 여부를 확인하고 인증을 완료한 계정이라면 로그인 화면으로 돌아간다.
    - 인증키와 옵션이 다르거나 이미 인증을 완료한 상태라면 유효하지 않은 요청으로 처리한다. 

  * **계정 관련 API 명세서**
  
| Action | API | Parameter | Body | Success Response | Fail Response |
|--------|-----|-----------|------|------------------|---------------|
| 이메일 중복 확인 |GET /account/email?email={}|email=[String]|N/A|Status 200 OK|<ul><li> Invalid Input Value - 400 { code: "C001" errors: [{field: "email", value: "", reason: "이메일을 입력해주세요. "}], message: " 유효하지 않은 요청입니다.", status: 400}</li><li> Email Aready Used - 400 { code: "A001", errors: [], message: " 이미 사용중인 이메일입니다.", status: 400 } </li></ul>|
| 인증 이메일 재발송 |GET /account/resend?email={}|email=[String]|N/A|Status 200 OK|<ul><li> Bad Request - 400 { "message":"잘못된 요청입니다.", "status":400, "code":"C006", "errors":[]} </li></ul>|
|회원가입|POST /account/join| N/A |{ "email" : "user@email.com", "password" : "password1", "passwordCheck" : "password2", "name" : "userName", "birth" : "920409", "nickname" : "nickname"|Status 200 OK|<ul><li> Entity Not Found - 400 { code: "C003", errors: [{field: "password", value: "", reason: "최소 8자리의 소문자,대문자,숫자,특수문자가 포함되어야합니다. "},{field: "passwordCheck", value: "", reason: "비밀번호를 재입력해주세요."}], message: " Entity Not Found", status: 400]</li></ul>|
|회원 정보 가져오기|GET /account/{id}|id=[Integer]| N/A | Status 200 OK { "id" : 1, "nickname" : "userNick" } | <ul><li>Account Info Not Found - 400 { code: "A010", errors: [], message: " 계정 정보를 찾을 수 없습니다.", status: 400</li></ul>
|계정 정보 변경|PATCH /account/{id}|id=[Integer]|{ "nickname" : "newNick" }|Status 200 OK|<ul><li>Entity Not Found - 400 { code: "C003", errors: [{field: "nickname", value: "", reason: "닉네임을 입력해주세요."}], message: " Entity Not Found", status: 400</li></ul>|
| 계정 찾기 |POST /account/find-email|N/A|{ "name" : "name", "birth" : "birth" }|Status 200 OK [{"email" : "email" , "date" : " data"}]|<ul><li>Entity Not Fount - 400 {"message":" Entity Not Found", "status":400, "code":"C003", "errors":[{"field":"birth","value":"","reason":"생년월일을 입력해주세요."},{"field":"name", "value":"", "reason":"이름을 입력해주세요."}]</li><li>Account Not Found - 400 {"message":" 계정을 찾을 수 없습니다.", "status":400, "code":"A004", "errors":[]}</li></ul>|
| 비밀번호 초기화 |POST /account/reset|N/A|{ "email" : "email", "name" : "name", "birth" : "birth" }|Status 200 OK|<ul><li>Entity Not Fount - 400 {"message":" Entity Not Found","status":400,"code":"C003","errors":[{"field":"name","value":"","reason":"이름을 입력해주세요."},{"field":"email","value":"","reason":"이메일을 입력해 주세요."},{"field":"birth","value":"","reason":"생년월일을 입력해주세요."}]}</li><li> Account Not Found Exception {"message":" 계정을 찾을 수 없습니다다, "status":400, "code":"A004", "errors":[]} </li><li> Find Account Bad Request {"message":" 계정 정보가 올바르지 않습니다.", "status":400, "code":"A008", "errors":[]} </li><li> Account Not EmailChecked {"message":" 계정이 활성화 되지 않았습니다. 이메일 인증을 완료해 주세요.", "status":400, "code":"A005", "errors":[]}</li></ul>|
| 비밀번호 재설정 |POST /account/resetPassword|N/A|{ "email" : "email", "password" : "password", "passwordCheck" : "passwordCheck", "authKey" : "authKey", "authOption" : "authOption" }|Status 200 OK|<ul><li>Entity Not Found - 400 {"message":" Entity Not Found", "status":400, "code":"C003", "errors":[{"field":"passwordCheck", "value":"", "reason":"비밀번호를 재입력해주세요."},{"field":"password", "value":"", "reason":"최소 8자리의 소문자,대문자,숫자,특수문자가 포함되어야합니다. "}]}</li><li>Bad Auth Request - 400 {"message":" 유효하지 않은 인증 요청 입니다.", "status":400, "code":"A002", "errors":[]}</li></ul>|
| 비밀번호 변경 |POST /account/passwordChange|N/A|{ "beforePassword" : "beforePassword", "afterPassword" : "afterPassword", "afterPasswordCheck":"afterPasswordCheck" }|Status 200 OK|<ul><li>Entity Not Found - 400 {"message":" Entity Not Found","status":400,"code":"C003","errors":[{"field":"afterPasswordCheck","value":"","reason":"바꿀 비밀번호를 재입력해주세요."},{"field":"beforePassword","value":"","reason":"이전 비밀번호를 입력해주세요."},{"field":"afterPassword","value":"","reason":"최소 8자리의 소문자,대문자,숫자,특수문자가 포함되어야합니다. "}]}</li><li>Password Not Match - 400 {"message":" 기존 비밀번호가 맞지 않습니다.", "status":400, "code":"A007", "errors":[]}</li><li>Password Check Failed - 400 {"message":" 비밀번호가 서로 다릅니다.", "status":400, "code":"A009", "errors":[]}</li></ul>|
-------
## 게시판
게시판 조건
1. 카테고리를 기준으로 게시판을 나눈다.
2. 현재 어떤 카테고리의 게시판을 보고 있는지 표시한다.
3. 게시글의 카테고리, 정렬, 검색, 페이징처리를 위한 값들을 가질 수 있는 공통된 requestDto를 사용한다.
4. 허용 가능한 값(카테고리, 게시글 중요도)을 제한한다.
5. 게시글 작성은 로그인을 해야 작성할 수 있다.
6. 게시글 수정,삭제는 해당 글을 작성한 계정 또는 관리자 권한을 가지고 있어야한다.
7. 관리자는 게시글 작성,수정,삭제와 다른 유저가 작성한 게시글을 수정,삭제 할 수 있다.

* **게시글 목록 조회**
    - 중요한 공지사항의 경우 항상 게시글 리스트 상단에 표시되게 한다.
    - 일정 범위(중요한 공지사항을 포함한 갯수)의 게시글을 가져온다.
    - 게시글들의 댓글 수, 추천 수, 조회 수를 표시한다.
    - 페이징 처리를 한다.
    - 최신순,추천순,댓글순,조회순으로 정렬할 수 있다.
    - 작성날짜를 분단위까지 표시한다.
    
* **단일 게시글 조회**
    - 해당 게시글의 정보(제목, 작성자, 내용 ... 등)를 표시한다.
    - 단일 게시글 페이지가 준비되면 해당 게시글의 댓글을 비동기 통신을 통해 받아와 표시한다.
    - 로그인한 사용자의 해당 글 추천 여부에 따라 다르게 표시한다.
    - 게시글을 작성한 계정 또는 관리자 계정이면 수정 및 삭제 버튼을 표시한다.
    
* **게시글 작성**
    - summernote editor를 적용한다.
    - 이미지 업로드를 할 수 있다.
    - 이미지 업로드시 비동기 통신을 통해 이미지를 서버를 통해 저장한 뒤 저장된 URL을 리턴하여 표시한다.
    - 다중 이미지 업로드가 가능하다.
    - 업로드 한 파일은 날짜 별로 저장된다.
    - 글 작성에 성공하면 작성한 글을 바로 확인할 수 있다. 
    
* **게시글 수정**
    - 게시글의 고유번호를 기준으로 해당 글을 수정한다.
    - 글을 작성한 계정이나, 관리자만 수정할 수 있다.
    - 카테고리, 제목, 내용을 수정할 수 있다.
    - 게시글을 수정하면 작성날짜 옆에 수정날짜를 표시한다.
    
* **게시글 삭제**
    - 게시글을 고유번호를 기준으로 해당 글을 삭제한다.
    - 글을 작성한 계정이나, 관리자만 삭제할 수 있다.
    - 해당 게시글에 등록된 댓글, 추천을 함께 삭제한다.
    
* **게시글 추천**
    - 로그인한 유저는 게시글을 추천할 수 있다.
    - 중복 추천을 할 수 없다.
    - 해당 게시글 추천 여부에 따라 추천, 추천 취소를 한다.
    
* **게시글 검색**
    - 제목으로 검색한다.
    - 검색 결과 게시글목록를 페이징 처리하여 표시한다.
    - 검색 결과를 최신순,추천순,댓글순,조회순으로 정렬할 수 있다.
    - 검색 값을 표시하여 무엇을 검색했는지 확인 할 수 있다.

* **게시글 관련 API 명세서**
  
| Action | API | Parameter | Body | Success Response | Fail Response |
|--------|-----|-----------|------|------------------|---------------|
| 해당 유저 게시글 보기 | GET /articles/account/{id}|id=[Integer]|N/A|Status 200 OK {"articles":[{"id":241,"accountId":1,"category":"notice","title":"gg","nickname":"관리자", "viewCount":6, "replyCount":0, "likeCount":0, "regdate":1597746989000}], "pagination":{"countList":10, "countPage":5, "page":1, "totalPage":6, "startCount":1, "endCount":10, "startPage":1, "endPage":5, "noticeScope":{"startCount":1,"endCount":0}}, "category":"", "query":"", "sort":"id" }|<ul><li> Articles Not Found - 400 {"message":"작성한 글이 없습니다.", "status":400, "code":"B002", "errors":[]} </li></ul>|
| 게시글 추천 | POST /articles/like/{id}|id=[Integer]|{"articleId" : "articleId", "accountId" : "accountId" }|Status 200 OK|<ul><li>Article Not Found - 400 {"message":"해당 게시글을 찾을 수 없습니다.", "status":400, "code":"B001", "errors":[]}</li></ul>|
| 게시글 작성 | POST /article|N/A| {"articleId" : "articleId", "category" : "category", "importance" : "importance", "title" : "title", "content" : "content"}|Status 200 OK|<ul><li>Entity Not Fount - 400 {"message":" Entity Not Found", "status":400, "code":"C003", "errors":[{"field":"title","value":"","reason":"제목을 입력해주세요."},{"field":"content", "value":"", "reason":"내용을 입력해주세요."}]}</li></ul>|
| 게시글 수정 | PATCH /article/{id}|id=[Integer]|{"id" : "id", "articleId" : "articleId", "category" : "category", "importance" : "importance", "title" : "title", "content" : "content"}|Status 200 OK|<ul><li>Entity Not Fount - 400 {"message":" Entity Not Found", "status":400, "code":"C003", "errors":[{"field":"title","value":"","reason":"제목을 입력해주세요."},{"field":"content", "value":"", "reason":"내용을 입력해주세요."}]}</li><li> Article Not Found - 400 {"message":"해당 게시글을 찾을 수 없습니다.", "status":400, "code":"B001", "errors":[]}</li></ul>|
| 게시글 삭제 | DELETE /article/{id}|id=[Integer]| {"articleId" : "articleId", "accountId" : "accountId"} |Status 200 OK|<ul><li> Article Not Found - 400 {"message":"해당 게시글을 찾을 수 없습니다.", "status":400, "code":"B001", "errors":[]}</li></ul>|
-----
## 댓글/대댓글
댓글/대댓글 조건
1. 댓글과 관련된 모든 요청은 비통기 통신을 통해 이루어 진다.
2. 댓글 입력은 로그인을 하여야 가능하다.
3. 해당 댓글 수정,삭제 요청은 댓글을 작성한 계정 또는 관리자 권한을 가지고 있어야 한다.
4. 관리자는 댓글과 대댓글 작성,수정,삭제와 일반 사용자가 작성한 댓글을 삭제 할 수 있다.
  
* **댓글/대댓글 보기**
  - 해당 글의 댓글을 모두 보여준다.
  - 해당 댓글을 작성한 계정이라면 수정 및 삭제 버튼이 표시된다.
  - 해당 댓글의 대댓글은 댓글 밑에 일정 범위 만큼 여백을 준 후 표시된다.
  
* **댓글/대댓글 작성**
  - 댓글 작성에 성공하면 해당 게시글의 댓글 리스트를 다시 불러온다.
  
* **댓글/대댓글 수정**
  - 댓글 수정에 성공하면 해당 게시글의 댓글 리스트를 다시 불러온다.
  - 수정된 댓글은 작성날짜 옆에 수정날짜를 같이 표시한다.
  
* **댓글/대댓글 삭제(비활성화)**
  - 댓글을 삭제 요청하면 해당 댓글을 비활성화 시킨 후, '삭제된 댓글입니다.' 로 표시한다.
  - 비활성화 된 댓글은 수정을 할 수 없다.
  
* **댓글 관련 API 명세서**

| Action | API | Parameter | Body | Success Response | Fail Response |
|--------|-----|-----------|------|------------------|---------------|
| 댓글 가져오기 |GET /replys/{id}|id=[Integer]|N/A|Status 200 OK [{"id":41,"articleId":268,"accountId":1,"nickname":"관리자","replyGroup":1,"replyDepth":0,"content":"삭제된 댓글입니다.","regdate":1602655212000,"modifyDate":1602655224000,"enabled":false}]|<ul><li>Replys Not Nound - 400 {"message":"댓글이 없습니다.", "status":400, "code":"R001", "errors":[]}</li></ul>|
| 댓글 작성 |POST /reply|N/A| {"articleId" : "articleId", "accountId" : "accountId", "replyGroup" : "replyGroup", "content" : "content"}|Status 200 OK|<ul><li>Article Not Found - 400 {"message":"해당 게시글을 찾을 수 없습니다.", "status":400, "code":"B001", "errors":[]}</li><li>Entity Not Found - 400 {"message":" Entity Not Found","status":400,"code":"C003","errors":[{"field":"content","value":"","reason":"댓글을 입력해 주세요."}]}</li></ul>|
| 댓글 수정 |PATCH /reply/{id}|id=[Integer]| {"id" : "id", "articleId" : " articleId", "accountId" : " accountId", " content" : "content"}| Status 200 OK|<ul><li>Article Not Found - 400 {"message":"해당 게시글을 찾을 수 없습니다.", "status":400, "code":"B001", "errors":[]}</li><li>Reply Not Nound - 400 {"message":"해당 댓글이 존재하지 않습니다.", "status":400, "code":"R002", "errors":[]}</li><li>Entity Not Found - 400 {"message":" Entity Not Found","status":400,"code":"C003","errors":[{"field":"content", "value":"", "reason":"댓글을 입력해 주세요."}]}</li></ul>|
| 댓글 삭제 |DELETE /reply/{id}|id=[Integer]| {"id" : "id", "articleId" : "articleId", "accountId" : "accountId"} |Status 200 OK|<ul><li>Article Not Found - 400 {"message":"해당 게시글을 찾을 수 없습니다.", "status":400, "code":"B001", "errors":[]}</li><li>Reply Not Nound - 400 {"message":"해당 댓글이 존재하지 않습니다.", "status":400, "code":"R002", "errors":[]}</li></ul>|
