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

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<body>
<script type="text/javascript">
function check_form(){
	var RequestPasswordDto = {
		beforePassword : $("#beforePassword").val(),
		afterPassword : $("#afterPassword").val(),
		afterPasswordCheck : $("#afterPasswordCheck").val()
	}
	
	$.ajax({
		url:"/account/passwordChange",
		type:"post",
		contentType : "application/json; charset=UTF-8",
		data: JSON.stringify(RequestPasswordDto),
		success:function(data){
			alert("비밀번호 변경을 완료하였습니다. 다시 로그인해주세요.");
			logout();
		}, 
		error:function(request,status,error){
			jsonValue = jQuery.parseJSON(request.responseText);
			code = jsonValue.code;
			$(".error").empty();
			if(code =='A007'){  
				$("#error_passwordCheck").append(jsonValue.message);
			}
			if(code =='A009'){ 
				$("#error_passwordCheck").append(jsonValue.message);
			}
			if(code == 'C003'){
				for(var i in jsonValue.errors){
					$("#error_"+jsonValue.errors[i].field).append(jsonValue.errors[i].reason);
				}
			}
		}
	});
}

function logout(){
	$.ajax({
		url:"/logout",
		type:"post",
		success:function(data){
			location.href="/login";
		}, 
		error:function(request,status,error){
			jsonValue = jQuery.parseJSON(request.responseText);
			code = jsonValue.code;
			alert(jsonValue.message);
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
							비밀번호 변경
						</h5>
					</div>
					<div id="view">
						<form:form method="post" action="/account/info/password" class="form-signup form-user panel-body">
							<fieldset>
								<input type="password" class="form-control input-sm" id="beforePassword" name="beforePassword" placeholder="현재 비밀번호" maxlength="20" style="margin-top: 10px;">
								<small id="error_beforePassword" class="error"></small>
								
								<input type="password" class="form-control input-sm" id="afterPassword" name="afterPassword" placeholder="변경할 비밀번호" maxlength="20" style="margin-top: 10px;">
								<small id="error_afterPasswordCheck" class="error"></small>
								
								<input type="password" class="form-control input-sm" id="afterPasswordCheck" placeholder="변경할 비밀번호 재입력" maxlength="20" style="margin-top: 10px;">
								<small id="error_passwordCheck" class="error"></small>
							</fieldset>
							<input type="button" class="btn btn-primary btn-block" value="변경하기" style="margin-top: 10px;" onclick="check_form();">
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>