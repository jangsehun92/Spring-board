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
	<title>게시글</title>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.5.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ajax_header.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	replyList("${responseDto.id}");
});

function articleDeleteConfirm(id){
	if(confirm("글을 삭제하시겠습니까?")){
		articleDelete(id);
	}else{
		return;
	}
}
function articleDelete(id){
	var requestArticleDeleteDto = {
			articleId : id,
			accountId : "${principal.id}"
	}
	
	$.ajax({
		url:"/article/"+id,
		type:"delete",
		contentType : "application/json; charset=UTF-8",
		data: JSON.stringify(requestArticleDeleteDto), 
		success:function(data){
			history.back();
		},
		error:function(request,status,error){
			jsonValue = jQuery.parseJSON(request.responseText);
			code = jsonValue.code;
			console.log("errorCode : " + code);
			alert(jsonValue.message);
		}
	});
}

function articleUpdate(id){
		$.ajax({
			url:"/article/edit/${responseDto.id}?id="+id+"&accountId=${responseDto.accountId}",
			type:"get",
			success:function(data){
				$("#container").html(data);
			},
			error:function(request,status,error){
				jsonValue = jQuery.parseJSON(request.responseText);
				code = jsonValue.code;
				alert(jsonValue.message);
			}
		});
}

function replyList(id){
	$.ajax({
		url:"/replys/${responseDto.id}",
		type:"GET",
		contentType : "application/json; charset=UTF-8",
		dataType: "JSON",
		success:function(data){
			$("#replyList").empty();
			$("#replyContent").val("");
			var html = "";
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
					html += "<li class='list-group-item'>"+
							 	"<div style='position: relative; height: 100%'>"+
							  		"<div>"+
										"<div>"+
											"<span>"+value.nickname+"</span><span class='text-muted'> | <small>"+uxin_timestamp(value.regdate)+" 작성</small></span>";
									if(value.modifyDate != null){
										html +="<span class='text-muted'><small> ᛫ "+uxin_timestamp(value.modifyDate)+" 수정</small></span>";
									}
									if(value.enabled != 0){
										html +=	"<div id='dropdownForm-"+value.id+"' style='float: right;'>"+
														"<a onClick='replyUpdateForm("+value.id+")'>수정</a> ᛫ "+
														"<a onClick='deleteConfirm("+value.id+")'>삭제</a>";
									}
									html += "</div>"+
												"<div id='replyForm-"+value.id+"' style='white-space : pre-wrap;height: 100%'>"+
													"<p id='reply-"+value.id+"'>"+value.content+"</p>"+
												"</div>"+
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
							"</li>";
				}else if('${principal.authorities}' == "[ROLE_ADMIN]"){
					html += "<li class='list-group-item'>"+
				 	"<div style='position: relative; height: 100%'>"+
				  		"<div>"+
							"<div>"+
								"<span>"+value.nickname+"</span><span class='text-muted'> | <small>"+uxin_timestamp(value.regdate)+" 작성</small></span>";
						if(value.modifyDate != null){
							html +="<span class='text-muted'><small> ᛫ "+uxin_timestamp(value.modifyDate)+" 수정</small></span>";
						}
						if(value.enabled != 0){
							html +=	"<div id='dropdownForm-"+value.id+"' style='float: right;'>"+
											"<a onClick='deleteConfirm("+value.id+")'>삭제</a>";
						}
						html += "</div>"+
									"<div id='replyForm-"+value.id+"' style='white-space : pre-wrap;height: 100%'>"+
										"<p id='reply-"+value.id+"'>"+value.content+"</p>"+
									"</div>"+
								"</div>"+
						"</div>"+
					"</div>"+
				"</li>";
				}
				
				else{
					html +="<li class='list-group-item'><span>"+value.nickname+"</span><span class='text-muted'> | <small>"+uxin_timestamp(value.regdate)+" 작성</small></span>";
					if(value.modifyDate != null){
						html +="<span class='text-muted'><small> ᛫ "+uxin_timestamp(value.modifyDate)+" 수정</small></span>";
					}
					html +=			"<div style='white-space : pre-wrap;height: 100%'>"+value.content+"</div>"+
							"</li>";
				}
				document.getElementById('replyList').innerHTML = html;
			});
		},
		error:function(request,status,error){
			alert("code:"+request.status+"\n\n"+"message:"+request.responseText+"\n\n"+"error:"+error);
		}
	});
	return false;
}

function replyCreate(){
	var content = $("#replyContent").val().replace(/\s|/gi,'');
	
	if(content==""){
		alert("댓글을 입력해주세요.");
		$("#replyContent").val("");
		$("#replyContent").focus();
		return false;
	}
	
	var requestReplyCreateDto = {
			articleId : "${responseDto.id}",
			accountId : "${principal.id}",
			content : $("#replyContent").val(),
	}
	
	$.ajax({
		url:"/reply",
		type:"post",
		contentType : "application/json; charset=UTF-8",
		data: JSON.stringify(requestReplyCreateDto), 
		
		success:function(data){
			replyList("${responseDto.id}");
		},
		error:function(request,status,error){
			jsonValue = jQuery.parseJSON(request.responseText);
			code = jsonValue.code;
			console.log("errorCode : " + code);
			alert(jsonValue.message);
		}
	});
	return false;
}

function replyUpdateForm(id){
	var dropdownForm = $("#dropdownForm-"+id);
	var replyForm = $("#replyForm-"+id);
	var updateForm = $("#updateForm-"+id);
	
	$("#replyContent-"+id).val($("#reply-"+id).text());
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
	
	var requestReplyUpdateDto = {
		id : id,
		articleId : "${responseDto.id}",	
		accountId : "${principal.id}",
		content : $("#replyContent-"+id).val()
	}
	
	$.ajax({
		url:"/reply/"+id,
		type:"patch",
		contentType : "application/json; charset=UTF-8",
		data: JSON.stringify(requestReplyUpdateDto), 
		success:function(data){
			alert("댓글이 수정되었습니다.");
			replyList("${responseDto.id}");
		},
		error:function(request,status,error){
			jsonValue = jQuery.parseJSON(request.responseText);
			code = jsonValue.code;
			console.log("errorCode : " + code);
			alert(jsonValue.message);
		}
	});
	return false;
}

function deleteConfirm(id){
	if(confirm("댓글을 삭제하시겠습니까?")){
		replyDelete(id);
	}else{
		return;
	}
}

function replyDelete(id){
	var requestReplyDeleteDto = {
		id : id,
		articleId : "${responseDto.id}",
		accountId : "${principal.id}",
	}
	
	$.ajax({
		url:"/reply/"+id,
		type:"delete",
		contentType : "application/json; charset=UTF-8",
		data: JSON.stringify(requestReplyDeleteDto),
		success:function(data){
			alert("댓글이 삭제되었습니다.");
			replyList("${responseDto.id}");
		},
		error:function(request,status,error){
			jsonValue = jQuery.parseJSON(request.responseText);
			code = jsonValue.code;
			console.log("errorCode : " + code);
			alert(jsonValue.message);
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

function listConfirm(id){
	if(confirm("새로고침 하시겠습니까?")){
		replyList(id);
	}else{
		return;
	}
}

//추천
function like(accountId){
	var requestLikeDto = {
		articleId : "${responseDto.id}",
		accountId : "${principal.id}",
	}
	
	$.ajax({
		url:"/article/like/${responseDto.id}",
		type:"post",
		contentType : "application/json; charset=UTF-8",
		data: JSON.stringify(requestLikeDto),
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
			alert(jsonValue.message);
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
<div id="container" class="container" style="margin-top: 50px">
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
							<span><a href="/account/info/${responseDto.accountId }">${responseDto.nickname }</a> </span> <span style="margin-left: 10px"><small><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${responseDto.regdate }"/> 작성
							<c:if test="${responseDto.modifyDate != null }">
								᛫ <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${responseDto.modifyDate }"/>수정
							</c:if>
							</small></span>
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
						<div class="btn-group">
							<a href="/articles/${responseDto.category }" class="btn btn-primary">목록 </a>
<!-- 글 수정,삭제 버튼 -->
							<c:if test="${principal.id eq responseDto.accountId}">
								<%-- <input type="button" class="btn btn-primary" value="수정" onclick="location.href='/article/edit/${responseDto.id}'"> --%>
								<input type="button" class="btn btn-primary" value="수정" onclick="articleUpdate(${responseDto.id});">
								<input type="button" class="btn btn-primary" value="삭제" onclick="articleDeleteConfirm(${responseDto.id});">
							</c:if> 
							<c:if test="${principal.id != responseDto.accountId}">
								<sec:authorize access="hasRole('ROLE_ADMIN')">
									<input type="button" class="btn btn-primary" value="수정" onclick="articleUpdate(${responseDto.id});">
									<input type="button" class="btn btn-primary" value="삭제" onclick="articleDeleteConfirm(${responseDto.id});">
								</sec:authorize>
							</c:if>
						</div>
					</div>

<!-- 추천버튼 -->
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
				<input type="button" class="btn btn-default" value="새로고침" onclick="listConfirm(${responseDto.id});">
<!-- 댓글 리스트 -->
			<div>
				<ul class="list-group" id="replyList">
					
				</ul>
			</div>
	</div>
</div>
</body>
</html>