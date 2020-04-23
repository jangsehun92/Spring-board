<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.5.0.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/inko@1.1.0/inko.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<body>
<script type="text/javascript">
//한 > 영 & 영 > 한 변환 자바스크립트 오픈소스 라이브러리
var inko = new Inko();
function check_form(){
	var email = $("#email").val().replace(/\s|/gi,'');
	var emailCheck = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
	var password = inko.ko2en($("#password").val().replace(/\s|/gi,''));
	var passwordCheck = inko.ko2en($("#password").val().replace(/\s|/gi,''));
	var name = $("#name").val().replace(/\s|/gi,'');
	var birth = $("#birth").val().replace(/\s|/gi,'');
	var nickname = $("#nickname").val().replace(/\s|/gi,'');
	
	if(email=="") {
		alert("이메일을 입력해주세요.");
		$("#email").focus();
		return false;
	}
	
	if (!emailCheck.test(email)) {
		alert("email 형식에 맞지않습니다.");
		return false;
	}
	
	if(password=="") {
		alert("비밀번호를 입력해주세요.");
		$("#password").focus();
		return false;
	}
	
	if(!/^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$/.test(password)) { 
        alert('비밀번호는 숫자/영문자/특수문자 조합으로 8~20자를 사용해야 합니다.'); 
        return false;
    }
	if(passwordCheck==""){
		alert("비밀번호를 재입력해주세요.");
		$("#passwordCheck").focus();
		return false;
	}
	
	if(password != passwordCheck){
		alert("비밀번호가 일치하지 않습니다.");
		$("#password").focus();
		return false;
	}
	
	if(nickname==""){
		alert("닉네임을 입력해주세요.");
		$("#nickname").focus();
		return false;
	}
	
	var code;
	$.ajax({
		url:"/account/email?email="+email,
		type:"get",
		async : false,
		contentType : "application/json; charset=UTF-8",
		dataType : "text",
		success:function(data){
			
		},
		error:function(request,status,error){
			jsonValue = jQuery.parseJSON(request.responseText);
			code = jsonValue.code;
			alert(jsonValue.message);
		}
	});
	
	if(code == 'A001'){
		return false;
	} 
	
	var accountCreateDto = {
		email : $("#email").val(),
		password : $("#password").val(),
		name : $("#name").val(),
		birth : $("#birth").val(),
		nickname : $("#nickname").val()
	}
	
	$.ajax({
		url:"/account/join",
		type:"post",
		contentType : "application/json; charset=UTF-8",
		data: JSON.stringify(accountCreateDto),
		success:function(data){
			alert("인증 이메일이 발송 되었습니다.");
			location.href="/account/sendEmail?email="+$("#email").val();
		},
		error:function(request,status,error){
			alert("회원가입에 실패하였습니다. 잠시 후 다시 시도해 주세요.");
			jsonValue = jQuery.parseJSON(request.responseText);
			code = jsonValue.code;
			alert(jsonValue.message);
			//alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}
</script>
<div class="container" style="margin-top: 80px;">
		<div class="col-md-6 main-block-left" style="align-items: center;">
			<div class="panel panel-default" style="color: #ddd;" >
				<div class="panel-heading">
					<h5 class="panel-header" style="text-align: center;">
						회원가입
					</h5>
				</div>
				<!-- <form method="post" action="/account/join" class="form-signup form-user panel-body" onsubmit="return check_form();"> -->
				<form:form method="post" action="/account/join" class="form-signup form-user panel-body">
					<fieldset>
						<input type="text" class="form-control input-sm" id="email" name="email" placeholder="이메일" maxlength="30" style="margin-top: 10px;">
						
						<input type="password" class="form-control input-sm" id="password" name="password" placeholder="비밀번호" style="margin-top: 10px;">
						
						<input type="password" class="form-control input-sm" id="passwordCheck" placeholder="비밀번호 확인" style="margin-top: 10px;">
						
						<input type="text" class="form-control input-sm" id="name" name="name" placeholder="이름" maxlength="10" style="margin-top: 10px;">
						
						<input type="text" class="form-control input-sm" id="birth" name="birth" placeholder="생년월일(ex:920409)" maxlength="6" style="margin-top: 10px;">
						
						<input type="text" class="form-control input-sm" id="nickname" name="nickname" placeholder="닉네임" maxlength="10" style="margin-top: 10px;">
					</fieldset>
					<!-- <input type="submit" class="btn btn-primary btn-block" value="회원가입" style="margin-top: 10px;"> -->
					<input type="button" class="btn btn-primary btn-block" value="회원가입" style="margin-top: 10px;" onclick="return check_form();">
				</form:form>
			</div>
		</div>
		
	</div>
</body>
</html>