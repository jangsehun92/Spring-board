<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<title>Home</title>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.5.0.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script type="text/javascript">
function test(){
	$.ajax({
		url:"/account/test/2",
		type:"get",
		beforeSend: function(xhr){
			xhr.setRequestHeader("ajax-call","true");	
		},
		success:function(data){
			alert("성공.");
		},
		error:function(request,status,error){
			jsonValue = jQuery.parseJSON(request.responseText);
			code = jsonValue.code;
			alert(jsonValue.message);
			
		}
	});
}


</script>
<body>
	<h1>Hello world!</h1>

	<P>The time on the server is ${serverTime}.</P>
	
	<sec:authorize access="isAuthenticated()">
    	<sec:authentication property="principal" var="principal" /> 
        <div id="nickname">
        	<p>${principal.nickname }님 안녕하세요.</p>
        	<p>${principal.id }
        	<p>${principal.password }
        	<p>${principal.name }
        	<p>${principal.username }
         </div>
    </sec:authorize>
    
	
	<sec:authorize access="hasRole('ROLE_USER')">
		일반 유저입니다.
	</sec:authorize>
	
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		관리자입니다.
	</sec:authorize>
	
	<a href="/admin">어드민 페이지</a>
	<input type="button" class="btn btn-primary btn-block" value="회원가입" style="margin-top: 10px;" onclick="test();">
	
</body>
</html>
