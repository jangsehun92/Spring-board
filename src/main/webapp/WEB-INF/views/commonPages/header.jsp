<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta id="_csrf" name="_csrf" content="${_csrf.token}"/>
<meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}"/>
<title>Board Project</title>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.5.0.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	  $('.dropdown-toggle').dropdown();
});
$(function(){
	if($("#category").val().toUpperCase()=="NOTICE"){
		$("#main_category").append("<h3>공지사항</h3>");
		$("#notice").addClass("active");
	}
	
	if($("#category").val().toUpperCase()=="COMMUNITY"){
		$("#main_category").append("<h3>커뮤니티</h3>");
		$("#community").addClass("active");
	}
	
	if($("#category").val().toUpperCase()=="QUESTIONS"){
		$("#main_category").append("<h3>질문</h3>");
		$("#questions").addClass("active");
	}
});

</script>
<body>
	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/">게시판 프로젝트</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li id="notice"><a href="/articles/notice">공지사항</a></li>
					<li id="community"><a href="/articles/community">커뮤니티</a></li>
					<li id="questions" ><a href="/articles/questions">질문</a></li>
					<li><hr></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<sec:authorize access="isAuthenticated()">
						<sec:authentication property="principal" var="principal" />
						<li id="info"><a href="/account/info/${principal.id}">${principal.nickname }</a></li>
						<li><a href="javascript:;" onclick="document.getElementById('logout-form').submit();">로그아웃</a></li>
						<form method="POST" id="logout-form" action="/logout">
						   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						</form>
					</sec:authorize>
					
					<c:if test="${principal == null}">
						<div>
							<ul class="nav navbar-nav navbar-right">
								<li id="login"><a href="/login" >로그인</a></li>
								<li id="join"><a href="/account/join">회원가입</a></li>
							</ul>
						</div>
					</c:if>
				</ul>
			</div>
		</div>
	</nav>
</body>
</html>