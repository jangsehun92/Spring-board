<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commonPages/header.jsp" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>edit</title>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.5.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ajax_header.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<body>
<script type="text/javascript">
//유효성 검사
function edit(){
	var nickname = $("#user_nickname").val().replace(/\s|/gi,'');
	
	var accountEditRequestDto = {
		nickname : nickname
	}
		
		$.ajax({
			url:"/account/"+${principal.id },
			type:"patch",
			contentType : "application/json; charset=UTF-8",
			data: JSON.stringify(accountEditRequestDto),
			success:function(data){
				alert("회원정보가 수정되었습니다.");
				location.href="/account/edit";
			},
			error:function(request,status,error){
				jsonValue = jQuery.parseJSON(request.responseText);
				code = jsonValue.code;
				if(code == 'C003'){
					$(".error").empty();
					for(var i in jsonValue.errors){
						$("#user_nickname").focus();
						$("#error_"+jsonValue.errors[i].field).append(jsonValue.errors[i].reason);
					}
				}
				if(code == 'C006'){
					alert(jsonValue.message);
					console.log("code : " + jsonValue.code + " message : " + jsonValue.message);
				}
				//alert(jsonValue.message);
			}
		});
}
</script>
<div class="container" style="margin-top: 80px;">
		<div class="col-md-6 main-block-left" style="align-items: center;">
			<div class="panel panel-default" style="color: #333;" >
				<div class="panel-heading">
					<h5 class="panel-header" style="text-align: center;">
						회원 정보 수정
					</h5>
				</div>
				<form method="post" action="/account/edit" class="form-signup form-user panel-body">
					<fieldset>	
						<p style="margin: 10px 0px 10px;">닉네임</p>
						<input type="text" class="form-control input-sm" id="user_nickname"  placeholder="닉네임" value="${principal.nickname }" maxlength="10" style="margin-top: 10px;">
						<small id="error_nickname" class="error"></small>
					</fieldset>
					<input type="button" class="btn btn-primary btn-block" value="정보 수정" style="margin-top: 10px;" onclick="edit()">
					<input type="button" class="btn btn-primary btn-block" value="비밀번호 변경" style="margin-top: 10px;" onclick="location.href='/account/passwordChange'">
				</form>
			</div>
		</div>
		
	</div>
</body>
</html>