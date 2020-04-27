<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
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
		dataType : "text",
		success:function(data){
			alert("인증 이메일이 재발송 되었습니다.");
		},
		error:function(request,status,error){
			jsonValue = jQuery.parseJSON(request.responseText);
			code = jsonValue.code;
			alert(jsonValue.message);
			location.href="/login";
		}
	});
}


</script>

	<div class="container" style="margin-top: 80px; align-items: center;">
		<div class="row">
		<div class="form-group">
			<div class="number font-red"> 
				<h1 style="color: red;">이메일 인증을 완료해주세요!</h1> 
			</div>
			
			<div class="details">
				<h3>이메일 정보 : ${email }</h3>
				<p>
					<input class="btn btn-primary" type="button" value="재발송" onclick="resendEmail()">
					<input class="btn btn-primary" type="button" value="로그인" onclick="location.href='/login'">
				</p>
			</div>
		</div>
	</div>
	</div>
</body>
</html>