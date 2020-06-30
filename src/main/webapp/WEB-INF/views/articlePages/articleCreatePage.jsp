<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/commonPages/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 생성</title>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.5.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ajax_header.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/summernote/summernote-lite.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/summernote/lang/summernote-ko-KR.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/summernote/summernote-lite.css">
<script type="text/javascript">
$(document).ready(function() {
	  $('#content').summernote({
	    	placeholder: '내용',
	    	height: 300,
	        minHeight: null,
	        maxHeight: null,
	        focus: true, 
	        lang : 'ko-KR',
	        callbacks: {
	        	//이미지가 올라오면 여기로 온다.
	        	onImageUpload : function(files){
	        		for (var i = 0; i < files.length; i++) {
	        		//해당 파일을 서버에 보내 저장한 후 저장한다.
	        			uploadSummernoteImageFile(files[i],this);
	        		}
	        	}
	        }
	  });
});

function uploadSummernoteImageFile(file, editor) {
	data = new FormData();
	data.append("file", file);
	$.ajax({
		data : data,
		type : "POST",
		url : "/article/image",
		contentType : false,
		processData : false,
		success : function(data) {
        	//editor에 저장된 이미지를 가져와 보여줘야하기 때문에 항상 업로드된 파일의 url이 있어야 한다.
        	console.log(data);
			$(editor).summernote('insertImage',data);
		}
	});
}
function check_form(){
	var inputForm_content = $("#content").val().replace(/\s|/gi,'');
	var importance = $("#select_importance option:selected").val();
	
	if(importance==null){
		importance = "일반";
	}
	
	if(inputForm_content==""){
		alert("내용을 입력해주세요.");
		$("#content").val("");
		$("#content").focus();
		return false;
	}
	
	var requestArticleCreateDto = {
			accountId : "${principal.id}",
			category : $("#select_category option:selected").val(),
			importance : importance,
			title : $("#title").val(),
			content : $("#content").val(),
		}
	
	$.ajax({
		url:"/article",
		type:"post",
		contentType : "application/json; charset=UTF-8",
		data: JSON.stringify(requestArticleCreateDto),
		success:function(data){
			location.href = "/article/"+data;
		},
		error:function(request,status,error){
			jsonValue = jQuery.parseJSON(request.responseText);
			code = jsonValue.code;
			if(code =='C004'){
				console.log(code +" : "+jsonValue.message);
				alert(jsonValue.message);
				history.back();
			}
		}
	});
}
</script>

<body>
<div class="container" style="margin-top: 50px">
	<input type="hidden" id="category" value="${category }"/>
	<div class="form">
		<h2>게시글 작성</h2>
			<table class="table table-bordered">
				<tr>
					<td>
						<select id = "select_category" class="form-control">
							<c:forEach items="${categorys }" var="categorys">
								<option value="${categorys.key}"
									<c:if test="${category eq categorys.key }"> selected </c:if>>${categorys.value }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<!-- 게시글 중요도 List가 Null이 아니면서 ADMIN 계정일 경우에만 게시글 중요도 선택가능 -->
				<c:if test="${articleImportance != null}">
					<sec:authorize access="hasRole('ROLE_ADMIN')">
					<tr>
						<td>
							<select id="select_importance" class="form-control">
							<c:forEach items="${articleImportance }" var="importance">
								<option value="${importance.key}"
									<c:if test="${responseDto.importance eq importance.value }"> selected </c:if>>${importance.key }</option>
							</c:forEach>
							</select>
						</td>
					</tr>
					</sec:authorize>
				</c:if>
				<tr>
					<td><input id="title" name="title" type="text" class="form-control" placeholder="제목" maxlength="50"></td>
				</tr>
				<tr>
					<td><textarea id="content" name="content"></textarea>
				</tr>
			</table>
		<div style="float: right">
			<div class="btn-group">
				<input type="button" class="btn btn-primary" value="취소" onclick="location.href='/articles/${category}'">
				<input type="button" class="btn btn-primary" value="작성" onclick="check_form();">
			</div>
		</div>
	</div>
</div>
</body>
</html>