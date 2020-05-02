<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
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
	if($("#category").val()=="community"){
		$("#main").append("<h3>커뮤니티</h3>");
		$("#community").addClass("active");
	}
	
	if($("#category").val()=="questions"){
		$("#main").append("<h3>질문</h3>");
		$("#questions").addClass("active");
	}
});
</script>
<body>
<!--  <input type="hidden" name="category" id="category" value="${category }">-->
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
					<li id="community"><a href="/articles/community">커뮤니티</a></li>
					<li id="questions"><a href="/articles/questions">질문</a></li>
					<li><hr></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<sec:authorize access="isAuthenticated()">
						<sec:authentication property="principal" var="principal" />
						<li id="info"><a href="/account/info">${principal.nickname }</a></li>
						<li id="logout"><a href="/logout">로그아웃</a></li>
					</sec:authorize>
					
					<sec:authorize access="isAnonymous()">
						<div>
							<ul class="nav navbar-nav navbar-right">
								<li id="login"><a href="/login">로그인</a></li>
								<li id="join"><a href="/account/join">회원가입</a></li>
							</ul>
						</div>
					</sec:authorize>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</nav>
</body>
</html>