<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/commonPages/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>error</title>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.5.0.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<body>
<script type="text/javascript">
function resendEmail(){
	$.ajax({
		url:"/account/resend?email=${email}",
		type:"get",
		success:function(data){
			alert("인증 이메일이 재발송 되었습니다.");
		},
		error:function(request,status,error){
			jsonValue = jQuery.parseJSON(request.responseText);
			code = jsonValue.code;
			if(code == 'C004'){
				alert("인증 이메일 발송에 실패하였습니다. (" + jsonValue.message +")");
			}else{
				alert(jsonValue.message);
				location.href="/login";
			}
			
		}
	});
}


</script>

	<div class="container" style="margin-top: 80px; align-items: center;">
		<div class="row">
			<div class="form-group">
				<c:if test="${errorResponse.code eq 'A005'}">
					<div class="number font-red"> 
						<h1 style="color: red;">${errorResponse.message }</h1> 
					</div>
					
					<div class="details">
						<h3>이메일 정보 : ${email }</h3>
						<p>
							<input class="btn btn-primary" type="button" value="재발송" onclick="resendEmail()">
							<input class="btn btn-primary" type="button" value="로그인" onclick="location.href='/login'">
						</p>
					</div>
				</c:if>
				
				<c:if test="${errorResponse.code eq 'A006' }">
					<div class="number font-red"> 
						<h1 style="color: red;">${errorResponse.message }</h1> 
					</div>
					
					<div class="details">
						<h3>이메일 정보 : ${email }</h3>
						<p>
							<input class="btn btn-primary" type="button" value="비밀번호 재설정" onclick="location.href='/account/find-password'">
						</p>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>