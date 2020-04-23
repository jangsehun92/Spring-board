<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>계정 찾기</title>
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
	var email = "${dto.email}";
	var authKey = "${dto.authKey}";
	var password = inko.ko2en($("#password").val().replace(/\s|/gi,''));
	var passwordCheck = inko.ko2en($("#password").val().replace(/\s|/gi,''));
	
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
	
	var AccountPasswordResetDto = {
		email : "${dto.email}",
		password : $("#password").val(),
		authKey : "${dto.authKey}",
		authOption : "${dto.authOption}"
	}
	
	$.ajax({
		url:"/account/resetPassword",
		type:"post",
		contentType : "application/json; charset=UTF-8",
		data: JSON.stringify(AccountPasswordResetDto),
		success:function(data){
			alert("비밀번호 재설정이 완료되었습니다.");
			location.href="/login";
		}, 
		error:function(request,status,error){
			jsonValue = jQuery.parseJSON(request.responseText);
			code = jsonValue.code;
			alert(jsonValue.message);
			//alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}


</script>
	<div class="container" style="margin-top: 80px;">
		<div id="main">
			<div class="col-md-6 main-block-left" style="align-items: center;">
				<div class="panel panel-default" style="color: #ddd;" >
					<div class="panel-heading">
						<h5 class="panel-header" style="text-align: center;">
							비밀번호 재설정
						</h5>
					</div>
					<div id="view">
						<form:form method="post" action="/account/find-email" class="form-signup form-user panel-body">
							
							<fieldset>
								<input type="password" class="form-control input-sm" id="password" name="password" placeholder="비밀번호" maxlength="20" style="margin-top: 10px;">
								
								<input type="password" class="form-control input-sm" id="passwordCheck" placeholder="비밀번호 재입력" maxlength="20" style="margin-top: 10px;">
							</fieldset>
							<input type="button" class="btn btn-primary btn-block" value="변경하기" style="margin-top: 10px;" onclick="return check_form();">
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>