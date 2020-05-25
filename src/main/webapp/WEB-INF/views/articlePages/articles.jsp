<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/WEB-INF/views/commonPages/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board</title>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.5.0.min.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/pagination.js"></script>
<script type="text/javascript">
window.onload = function() {
	var sort = '${responseArticlesDto.sort }';
	$("#sort-"+sort).css('fontWeight','bold');
};
</script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<body>
	<input type="hidden" id="category" value="${responseArticlesDto.category }">
	<input type="hidden" id="totalPage" value="${responseArticlesDto.pagination.totalPage }">
	<input type="hidden" id="startPage" value="${responseArticlesDto.pagination.startPage }">
	<input type="hidden" id="endPage" value="${responseArticlesDto.pagination.endPage }">
	<input type="hidden" id="page" value="${responseArticlesDto.pagination.page }">
	<input type="hidden" id="query" value="${responseArticlesDto.query }">
	<input type="hidden" id="sort" value="${responseArticlesDto.sort }">

	<div class="container" style="margin-top: 50px">
		<div id="main">
			<div id = "main_category"></div>
			<hr>
			<div>
				<c:if test="${responseArticlesDto.category != 'notice'}">
					<form class="form-search" method="get" action="/articles/${responseArticlesDto.category }">		
						<div class="input-append" style="float:left" align="center">
							<a id="sort-id" href="/articles/${responseArticlesDto.category }?sort=id&query=${responseArticlesDto.query}" style="font: italic bold;">최신순</a>
							<a id="sort-likeCount" href="/articles/${responseArticlesDto.category }?sort=likeCount&query=${responseArticlesDto.query}">추천순</a>
							<a id="sort-replyCount" href="/articles/${responseArticlesDto.category }?sort=replyCount&query=${responseArticlesDto.query}">댓글순</a>
							<a id="sort-viewCount" href="/articles/${responseArticlesDto.category }?sort=viewCount&query=${responseArticlesDto.query}">조회순</a>
						</div>
						<div class="input-append" style="float:right">
							<input type="hidden" id="sort" name="sort" value="${responseArticlesDto.sort }">
							<input type="text" id="query" name="query" class="span2 search-query" value="${responseArticlesDto.query }">
							<button type="submit" class="btn">검색</button>
						</div>
					</form>
				</c:if>
				
			</div>
			<table class="table table-hover">
				<thead>
					<tr>
						<td class="col-md-6"><b>제목</b></td>
						<td class="col-md-1" align="right"><b>추천</b></td>
						<td class="col-md-1" align="right"><b>조회</b></td>
						<td class="col-md-1" align="right"><b>작성자</b></td>
						<td class="col-md-1" align="right"><b>작성 날짜</b></td>
					</tr>
				</thead>
				<c:choose>
					<c:when test="${empty responseArticlesDto.articles}">
						<tr>
							<td colspan="4" align="center">--- 등록된 글이 없습니다 ---</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach items="${responseArticlesDto.articles }" var="article">
						<c:choose>
							<c:when test="${article.category  eq 'notice'}">
								<tr>
									<td><a href="/article/${article.id }"><b><font color="black">[공지사항] ${article.title }</font></b></a></td>
									<td align="right">${article.likeCount }</td>
									<td align="right">${article.viewCount }</td>
									<td align="right">${article.nickname }</td>
									<td align="right"><small><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${article.regdate }"/></small></td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<td><a href="/article/${article.id }">${article.title } (${article.replyCount })</a></td>
									<td align="right">${article.likeCount }</td>
									<td align="right">${article.viewCount }</td>
									<td align="right">${article.nickname }</td>
									<td align="right"><small><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${article.regdate }"/></small></td>
								</tr>
							</c:otherwise>
						</c:choose>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</table>
			<hr>
			<c:choose>
					<c:when test="${responseArticlesDto.category != 'notice'}">
						<sec:authorize access="isAuthenticated()">
							<div style="float: right">
								<a href="/articles/${category }/create" class="btn btn-primary">글쓰기</a>
							</div>
						</sec:authorize>
					</c:when>
					<c:otherwise>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<div style="float: right">
								<a href="/articles/${responseArticlesDto.category }/create" class="btn btn-primary">글쓰기</a>
							</div>
						</sec:authorize>
					</c:otherwise>
			</c:choose>
				
			<div>
				<nav aria-label="..." style="text-align: center;">
					<ul class="pagination" id="pagination">
					</ul>
				</nav>
			</div>
		</div>
	</div>
</body>
</html>