<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/views/commonPages/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>회원가입</title>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.5.0.min.js"></script>
<script src="lib/year-select.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ajax_header.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/inko@1.1.0/inko.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<body>
<script type="text/javascript">
var inko = new Inko();
function check_form(){
	var code;
	
	$.ajax({
		url:"/account/email?email="+$("#email").val(),
		type:"get",
		async : false,
		contentType : "application/json; charset=UTF-8",
		success:function(data){
			$(".ok").empty();
			$("#ok_email").append("사용가능한 이메일 주소 입니다.");
		},
		error:function(request,status,error){
			jsonValue = jQuery.parseJSON(request.responseText);
			code = jsonValue.code;
			$(".error").empty();
			$(".ok").empty();
			if(code == 'C001'){
				for(var i in jsonValue.errors){
					$("#error_"+jsonValue.errors[i].field).append(jsonValue.errors[i].reason);
				}
			}
			if(code == 'A001'){
				$("#error_email").append(jsonValue.message);
			}
		}
	});
	
	if(code == 'A001' || code == 'C001'){
		return false;
	} 
	
	var RequestAccountCreateDto = {
			email : $("#email").val(),
			password : inko.ko2en($("#password").val()),
			passwordCheck : inko.ko2en($("#passwordCheck").val()),
			name : $("#name").val(),
			birth : $("#birth").val(),
			nickname : $("#nickname").val()
		}
	
	$.ajax({
		url:"/account/join",
		type:"post",
		contentType : "application/json; charset=UTF-8",
		data: JSON.stringify(RequestAccountCreateDto),
		success:function(data){
			alert("인증 이메일이 발송 되었습니다.");
			location.href="/account/sendEmail?email="+$("#email").val();
		},
		error:function(request,status,error){
			jsonValue = jQuery.parseJSON(request.responseText);
			code = jsonValue.code;
			$(".error").empty();
			if(code == 'C004'){
				alert("인증 이메일 발송에 실패하였습니다. (" + jsonValue.message + ")");
			}
			if(code == 'C003'){
				for(var i in jsonValue.errors){
					$("#error_"+jsonValue.errors[i].field).append(jsonValue.errors[i].reason);
				}
			}
			if(code =='A009'){
				$("#error_passwordCheck").append(jsonValue.message);
			}
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
				<form:form class="form-signup form-user panel-body" method="post" >
					<fieldset>
						<input type="text" class="form-control input-sm" id="email" name="email" placeholder="이메일" maxlength="30" style="margin-top: 10px;">
						<small id="error_email" class="error"></small>
						<small id="ok_email" class="ok"></small>
						
						<input type="password" class="form-control input-sm" id="password" name="password" placeholder="비밀번호" style="margin-top: 10px;" onkeyup="this.value=this.value.replace(/[^a-zA-Z0-9!@#$%^&*()-_]/g,'');">
						<small id="error_password" class="error"></small>
						
						<input type="password" class="form-control input-sm" id="passwordCheck" placeholder="비밀번호 재입력" style="margin-top: 10px;" onkeyup="this.value=this.value.replace(/[^a-zA-Z0-9!@#$%^&*()-_]/g,'');">
						<small id="error_passwordCheck" class="error"></small>
						
						<input type="text" class="form-control input-sm" id="name" name="name" placeholder="이름" maxlength="10" style="margin-top: 10px;">
						<small id="error_name" class="error"></small>
						
						<input type="text" class="form-control input-sm" id="birth" name="birth" placeholder="주민번호 앞자리" maxlength="6" style="margin-top: 10px;">
						<small id="error_birth" class="error"></small>
						
						<input type="text" class="form-control input-sm" id="nickname" name="nickname" placeholder="닉네임" maxlength="10" style="margin-top: 10px;">
						<small id="error_nickname" class="error"></small>
					</fieldset>
					<input type="button" class="btn btn-primary btn-block" value="회원가입" style="margin-top: 10px;" onclick="check_form();">
				</form:form>
			</div>
		</div>
		
	</div>
</body>
</html>