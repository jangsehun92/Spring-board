<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/WEB-INF/views/commonPages/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>profile</title>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.5.0.min.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/member-pagination.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<body>
	<input type="hidden" id="id" value="${accountInfoDto.accountResponseDto.id }">
	<%-- <input type="hidden" id="totalPage" value="${resultMap.pagination.totalPage }">
	<input type="hidden" id="startPage" value="${resultMap.pagination.startPage }">
	<input type="hidden" id="endPage" value="${resultMap.pagination.endPage }">
	<input type="hidden" id="page" value="${resultMap.pagination.page }"> --%>

	<div class="container" style="margin-top: 80px;">

		<div class="main-block-center" style="align-items: center;">
			<div class="panel panel-default"
				style="color: #2e6da4; margin-bottom: 0px">
				<div class="panel-heading">
					<div class="row">
						<div class="com-md-6">
							<h5 class="panel-header" style="text-align: center;">
								${accountInfoDto.accountResponseDto.nickname }</h5>
						</div>
					</div>
				</div>
				<div class="panel-body" style="text-align: left;">
					
					<c:choose>
						<c:when test="${!empty accountInfoDto.articles}">
							<span>작성한 글 </span>
							<div>
								<div style="margin-top: 40px">
									<span>작성한 글 내역</span>
								</div>
			
								<table class="table table-hover">
									<thead>
										<tr>
											<td class="col-md-3"><b>제목</b></td>
											<td class="col-md-6" align="right"><b>작성자</b></td>
											<td class="col-md-1" align="right"><b>작성 날짜</b></td>
										</tr>
									</thead>
									<c:forEach items="${accountInfoDto.articles }" var="article">
										<tr>
											<td><a href="/article/${article.id }">${article.title }</a></td>
											<td align="right">${article.writer }</td>
											<td align="right">${article.regDate }</td>
										</tr>
									</c:forEach>
								</table>
							</div>
							<div>
								<nav aria-label="..." style="text-align: center;">
									<ul class="pagination" id="pagination">
									</ul>
								</nav>
							</div>
						</c:when>
						<c:otherwise>
							<p>작성한 글이 없습니다.</p>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
</body>
</html>