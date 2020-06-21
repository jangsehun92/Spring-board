<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/views/commonPages/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>계정 찾기</title>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.5.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ajax_header.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/inko@1.1.0/inko.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<body>
<script type="text/javascript">
//한 > 영 & 영 > 한 변환 자바스크립트 오픈소스 라이브러리
var inko = new Inko();
function sendEmail(){
	var RequestAccountResetDto = {
		email : $("#email").val(),
		name : $("#name").val(),
		birth : $("#birth").val()
	}
	
	$.ajax({
		url:"/account/reset",
		type:"post",
		contentType : "application/json; charset=UTF-8",
		data: JSON.stringify(RequestAccountResetDto),
		success:function(data){
			alert("비밀번호 초기화 인증 이메일을 보냈습니다.");
			location.href="/account/sendEmail?email="+$("#email").val();
		}, 
		error:function(request,status,error){
			jsonValue = jQuery.parseJSON(request.responseText);
			code = jsonValue.code;
			$(".error").empty();
			
			if(code == 'A004'){
				$("#error_account").append(jsonValue.message);
			}
			
			if(code == 'A008'){
				$("#error_account").append(jsonValue.message);
			}
			
			if(code == 'A005'){
				alert(jsonValue.message);
				location.href="/account/sendEmail?email="+$("#email").val();
			}
			
			if(code == 'C003'){
				for(var i in jsonValue.errors){
					$("#error_"+jsonValue.errors[i].field).append(jsonValue.errors[i].reason);
				}
			}
			if(code == 'C004'){
				alert("인증이메일 발송에 실패하였습니다.("+jsonValue.message+")");
			}
		}
	});
}

function resetCofirm(){
	if(confirm("해당정보로 가입한 이메일로 인증이메일을 발송하시겠습니까?")){
		sendEmail();
	}else{
		return;
	}
}


</script>
	<div class="container" style="margin-top: 80px;">
		<div id="main">
			<div class="col-md-6 main-block-left" style="align-items: center;">
				<div class="panel panel-default" style="color: #ddd;" >
					<div class="panel-heading">
						<h5 class="panel-header" style="text-align: center;">
							비밀번호 찾기
						</h5>
					</div>
					<div id="view">
						<form:form method="post" action="/account/find-email" class="form-signup form-user panel-body">
							<fieldset>
								<input type="text" class="form-control input-sm" id="email" name="email" placeholder="email" maxlength="30" style="margin-top: 10px;">
								<small id="error_email" class="error"></small>
								<input type="text" class="form-control input-sm" id="name" name="name" placeholder="이름" maxlength="10" style="margin-top: 10px;">
								<small id="error_name" class="error"></small>
								<input type="text" class="form-control input-sm" id="birth" name="birth" placeholder="생년월일(ex:920409)" maxlength="6" style="margin-top: 10px;">
								<small id="error_birth" class="error"></small>
							</fieldset>
							<input type="button" class="btn btn-primary btn-block" value="찾기" style="margin-top: 10px;" onclick="return resetCofirm();">
							<small id="error_account" class="error"></small>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>