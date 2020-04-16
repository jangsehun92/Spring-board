<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
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
	var email = $("#email").val();//.replace(/\s|/gi,'');
	
	
	var accountEmail ={
			email : $("#email").val(),
	}
	var result;
	alert(email);
	alert(accountEmail);
	
	$.ajax({
		url:"/account/get?email="+email,
		type:"get",
		//async : false,
		contentType : "application/json; charset=UTF-8",
		dataType : "text",
		//data: JSON.stringify(accountEmail),
		success:function(data){
			//alert(data);
			result = data;
		},
		error:function(request,status,error){
			jsonValue = jQuery.parseJSON(request.responseText);
			alert(jsonValue.message);
			//"code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error
		}
	});
	
	return false;
}
</script>

	<div class="container" style="margin-top: 80px; align-items: center;">
		<div class="col-md-6 main-block-center">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h5 class="panel-header" style="text-align: center;">
						test 로그인
					</h5>
				</div>
				<form:form method="post" action="/account/post" class="form-signup form-user panel-body" onsubmit="return check_form();">
					<fieldset>
						<input type="text" class="form-control input-sm" id="email" name="email" placeholder="이메일" maxlength="30" style="margin-top: 10px;">
						
					</fieldset>
					<input type="submit" class="btn btn-primary btn-block" value="회원가입" style="margin-top: 10px;">
				</form:form>
			</div>
			<div>
			</div>
		</div>
	</div>
</body>
</html>