<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/views/commonPages/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>articleDetail</title>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.5.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ajax_header.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script type="text/javascript">

function replyCreate(){
	var content = $("#replyContent").val().replace(/\s|/gi,'');
	
	if(content==""){
		alert("댓글을 입력해주세요.");
		$("#replyContent").val("");
		$("#replyContent").focus();
		return false;
	}
	
	var replyCreateRequest = {
			articleId : "${responseDto.id}",
			content : $("#replyContent").val(),
	}
	
	$.ajax({
		url:"/reply",
		type:"post",
		contentType : "application/json; charset=UTF-8",
		dataType : "text",
		data: JSON.stringify(replyCreateRequest), 
		
		success:function(data){
			alert("댓글이 입력되었습니다.");
			replyList();
		},
		error:function(request,status,error){
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
	return false;
}

function replyList(){
	$.ajax({
		url:"/reply/${responsDto.id}",
		type:"GET",
		contentType : "application/json; charset=UTF-8",
		dataType: "JSON",
		success:function(data){
			if($("#noComments").length){
				$("#noComments").empty();
			}
			$("#replyList").empty();
			$("#replyContent").val("");
			
			if(data.length == 0){
				$("#replyList").append(
					"<li class='list-group-item'>"+
						"<div>"+
							"<div>"+
								"<div>"+
									"<div id='replyForm'>"+
										"<span>댓글이 없습니다.</span>"+
									"</div>"+
								"</div>"+
							"</div>"+
						"</div>"+
					"</li>"
				);
			}
			$.each(data, function(index, value) {
				if(value.accountId == "${principal.id}"){
					$("#replyList").append(
					"<li class='list-group-item'>"+
						"<div style='position: relative; height: 100%'>"+
							"<div>"+
								"<div>"+
									"<span>"+value.nickname+"</span><span class='text-muted'> | <small>"+uxin_timestamp(value.regdate)+"</small></span>"+
										"<div id='dropdownForm-"+value.id+"' style='float: right;'>"+
											"<div class='btn-group'>"+
												"<button type='button' class='btn btn-default btn-xs dropdown-toggle' data-toggle='dropdown' aria-expanded='false'>"+
													"<span class='caret'></span>"+
												"</button>"+
												"<ul class='dropdown-menu' role='menu'>"+
													"<li><a onClick='replyUpdateForm("+value.id+")'>수정</a></li>"+
													"<li><a onClick='deleteConfirm("+value.id+")'>삭제</a></li>"+
												"</ul>"+
										"</div>"+
								"</div>"+
							"<div id='replyForm-"+value.id+"' style='white-space : pre-wrap;height: 100%'><p>"+value.content+
							"</p></div>"+
							"</div>"+
								"<div id='updateForm-"+value.id+"' style='display: none;'>"+
									"<form method='post' action='/reply/"+value.id+"' onsubmit='return replyUpdate("+value.id+");'>"+
										"<input type='hidden' name='_method' value='PUT'>"+
										"<textarea id='replyContent-"+value.id+"' name='content' class='form-control z-depth-1' rows='3' maxlength='1000' placeholder='댓글을 입력해주세요.'>"+value.content+"</textarea>"+
										"<input type='submit' style='width:50%' class='btn btn-success' value='수정'>"+
										"<input type='button' style='width:50%' class='btn btn-primary' value='취소' onclick='replyForm("+value.id+")'>"+
 									"</form>"+
								"</div>"+
							"</div>"+
						"</div>"+
					"</li>");
					$('.dropdown-toggle').dropdown();
				}else{
					$("#replyList").append("<li class='list-group-item'><span>"+value.nickname+"</span><span class='text-muted'> | <small>"+uxin_timestamp(value.regdate)+"</small></span>"+
											"<div style='white-space : pre-wrap;height: 100%'>"+value.content+"</div></li>");
				}
			});
		},
		error:function(request,status,error){
			alert("code:"+request.status+"\n\n"+"message:"+request.responseText+"\n\n"+"error:"+error);
		}
	});
	return false;
}
function replyUpdateForm(id){
	var dropdownForm = $("#dropdownForm-"+id);
	var replyForm = $("#replyForm-"+id);
	var updateForm = $("#updateForm-"+id);
	
	replyForm.hide();
	dropdownForm.hide();
	updateForm.show();
	$("#replyContent-"+id).focus();
}
function replyForm(id){
	var dropdownForm = $("#dropdownForm-"+id);
	var replyForm = $("#replyForm-"+id);
	var updateForm = $("#updateForm-"+id);
	
	
	replyForm.show();
	dropdownForm.show();
	updateForm.hide();
	$("#replyForm-"+id).focus();
}
function replyUpdate(id){
	var replyUpdateRequest = {
		content : $("#replyContent-"+id).val(),
	}
	
	$.ajax({
		url:"/reply/"+id,
		type:"patch",
		contentType : "application/json; charset=UTF-8",
		//dataType : "text",
		data: JSON.stringify(replyUpdateRequest), 
		
		success:function(data){
			alert("댓글이 수정되었습니다.");
			replyList();
		},
		error:function(request,status,error){
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
	return false;
}

function replyDelete(id){
	$.ajax({
		url:"/reply/"+id,
		type:"delete",
		success:function(data){
			alert("댓글이 삭제되었습니다.");
			replyList();
		},
		error:function(request,status,error){
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
	return false;
}

function uxin_timestamp(time){
	var date = new Date(time);
	var year = date.getFullYear();
	var month = "0" + (date.getMonth()+1);
	var day = "0" + date.getDate();
	var hour = "0" + date.getHours();
	var minute = "0" + date.getMinutes();
	//var second = "0" + date.getSeconds();
	return year + "-" + month.substr(-2) + "-" + day.substr(-2) + " " + hour.substr(-2) + ":" + minute.substr(-2);
}

function listConfirm(){
	if(confirm("새로고침 하시겠습니까?")){
		replyList();
	}else{
		return;
	}
}

function deleteConfirm(id){
	if(confirm("삭제하시겠습니까?")){
		replyDelete(id);
	}else{
		return;
	}
}

//추천
function like(accountId){
	$.ajax({
		url:"/article/like/${responseDto.id}",
		type:"post",
		success:function(data){
			var likeCount = Number($("#likeCount").text());
			if($("#likeCheck").val() == "true"){
				alert("추천을 취소하였습니다.");
				$("#likeCount").html(Number(likeCount)-1);
				$("#likeCheck").val(false);
				$("#like").val("추천 하기");
			}else{
				alert("추천하였습니다.");
				$("#likeCount").html(Number(likeCount)+1);
				$("#likeCheck").val(true);
				$("#like").val("추천 취소");
			}
		},
		error:function(request,status,error){
			jsonValue = jQuery.parseJSON(request.responseText);
			code = jsonValue.code;
			$(".error").empty();
			if(code == 'B001'){
				alert(jsonValue.message);
			}
		}
	});
}
function login(){
	if(confirm("로그인 하시겠습니까?")){
		location.href="/login";	
	}else{
		return;
	}
	return false;
}
</script>
<body>
<div class="container" style="margin-top: 50px">
	<input type="hidden" id ="category" value="${responseDto.category }">
	<input type="hidden" id ="likeCheck" value="${responseDto.likeCheck }">
	<div class="header">
		<h2>글보기</h2>
		<hr>
			<ul class="list-group">
				<li class="list-group-item">
					<div class="title">
						<h3>${responseDto.title }</h3>
					</div>
					<div class="row" >
						<div class="col-md-4" style="float: left">
							<span>${responseDto.nickname } </span> <span style="margin-left: 10px"><small><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${responseDto.regdate }"/></small></span>
						</div>
						<div style="float: right">
							<small><span style="margin-right: 10px">조회 ${responseDto.viewCount }</span></small> <small >댓글<span id = "replyCount" style="margin-right: 10px">${responseDto.replyCount }</span> </small><small >추천<span id = "likeCount" style="margin-right: 10px">${responseDto.likeCount }</span> </small>
						</div>
					</div>
				</li>
				<li class="list-group-item">
					<div>
						<div id="board_content" style="white-space : pre-wrap;height: 100%">${responseDto.content }</div>
					</div>
				</li>
			</ul>
			<div class="row" style="margin-left: 0px; margin-right: 0px">
					<div style="float: left">
					<a href="/articles/${responseDto.category }" class="btn btn-primary">목록</a>
					</div>
<!-- 글 수정,삭제 버튼 -->
					<div style="float: left">
						<c:if test="${principal.id eq responseDto.accountId}">
							<input type="button" class="btn btn-primary" value="수정" onclick="location.href='/article/edit/${responseDto.id}'">
							<div style="float: left">
								<form method="post" action="/article/${responseDto.id }">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
									<input type="hidden" name="_method" value="delete"/>
									<input type="submit" class="btn btn-primary" value="삭제">
								</form>
							</div>
						</c:if> 
					</div>
<!-- 추천버튼 -->
		<!-- if category값이 notice가 아니면 추천,댓글작성,댓글리스트를 볼수 있어야한다. -->
				<div style="float: right">
					<sec:authorize access="isAuthenticated()">
						<c:choose>
							<c:when test="${responseDto.likeCheck == 'true'}">
								<input type="button" class="btn btn-primary" id="like" value="추천 취소" onclick="like(${principal.id})">
							</c:when>
							<c:otherwise>
								<input type="button" class="btn btn-primary" id="like" value="추천 하기" onclick="like(${principal.id})">
							</c:otherwise>
						</c:choose>
					</sec:authorize>
					<sec:authorize access="isAnonymous()">
						<input type="button" class="btn btn-primary" id="like" value="추천 하기" onclick="login();">
					</sec:authorize>
				</div>
			</div>
			<hr >

<!-- 댓글 입력란 -->
			<div class="form-group shadow-textarea">
					<sec:authorize access="isAnonymous()">
						<span>로그인을 하시면 댓글을 등록할 수 있습니다.</span>
					</sec:authorize>
					<sec:authorize access="isAuthenticated()">
						<label>댓글</label>
						<div style="position: relative; height: 100%">
							<div>
							<form method="post" action="/reply" onsubmit="return replyCreate();">
								<textarea id="replyContent" name="content" class="form-control z-depth-1" rows="3" maxlength="1000" placeholder="댓글을 입력해주세요."></textarea>
								<input type="submit" class="btn btn-success" style="width:100%;" value="작성">
							</form>
							</div>
						</div>
					</sec:authorize>
			</div>
			
<!-- 새로고침(댓글) -->			
			<hr>
				<input type="button" class="btn btn-primary" value="새로고침" onclick="listConfirm();">
				
<!-- 댓글 리스트 -->
			<%-- <div>
				<ul class="list-group" id="replyList">
					
					<c:choose>
						<c:when test="${empty responseDto.replyList}">
							<li class="list-group-item" id="noComments">
								<div>
									<div>
										<div>	
											<div id="replyForm">
												<span>댓글이 없습니다.</span>
											</div>
										</div>
									</div>
								</div>
							</li>
						</c:when>
						
						<c:otherwise>
							<c:forEach items="${responseDto.replyList }" var="replyDto">
								<li class="list-group-item">
									<div style="position: relative; height: 100%">
										<div>
											<div>
												<span>${ replyDto.nickname}</span><span class="text-muted"> | <small><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${replyDto.regDate}"/></small></span>
													<c:if test="${member.id eq replyDto.memberId}">
													<div id="dropdownForm-${ replyDto.id}" style="float: right;">
														<div class="btn-group">
															<button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
																<span class="caret"></span>
															</button>
															<ul class="dropdown-menu" role="menu">
																<li><a onClick="replyUpdateForm(${ replyDto.id})">수정</a></li>
																<li><a onClick="deleteConfirm(${ replyDto.id})">삭제</a></li>
															</ul>
														</div>
													</div>
													</c:if>
										<div id="replyForm-${replyDto.id }" style="white-space : pre-wrap;height: 100%"><p>${replyDto.content }</p></div>
										</div>
											<div id="updateForm-${replyDto.id }" style='display: none;'>
												<form method="post" action="/reply/${replyDto.id }" onsubmit="return replyUpdate(${replyDto.id });">
													<input type="hidden" name="_method" value="PUT">
													<textarea id="replyContent-${replyDto.id }" name="content" class="form-control z-depth-1" rows="3" maxlength="1000" placeholder="댓글을 입력해주세요.">${replyDto.content }</textarea>
													<input type="submit" style="width:50%" class="btn btn-success" value="수정">
													<input type="button" style="width:50%; float: left;" class="btn btn-primary" value="취소" onclick="replyForm(${replyDto.id })">
			 									</form>
											</div>
										</div>
									</div>
								</li>		
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</ul>
			</div> --%>
	</div>
</div>
</body>
</html>