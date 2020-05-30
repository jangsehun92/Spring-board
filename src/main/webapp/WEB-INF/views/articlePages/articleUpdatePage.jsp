<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/commonPages/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.5.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ajax_header.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/summernote/summernote-lite.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/summernote/lang/summernote-ko-KR.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/summernote/summernote-lite.css">
<style>
textarea {min-height: 50px;}
</style>
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
	  $('#content').summernote('code', '${responseArticleUpdateDto.content}');
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
			$(editor).summernote('insertImage',data);
		}
	});
}

function check_form(){
	var inputForm_content = $("#content").val().replace(/\s|/gi,'');
	
	if(inputForm_content==""){
		alert("내용을 입력해주세요.");
		$("#content").val("");
		$("#content").focus();
		return false;
	}
	
	var requestArticleUpdateDto = {
			id : "${responseArticleUpdateDto.id}",
			accountId : "${principal.id}",
			category : "${category }",
			title : $("#title").val(),
			content : $("#content").val(),
		}
	
	$.ajax({
		url:"/article/${responseArticleUpdateDto.id}",
		type:"patch",
		contentType : "application/json; charset=UTF-8",
		data: JSON.stringify(requestArticleUpdateDto),
		success:function(data){
			location.href = "/article/${responseArticleUpdateDto.id}";
		},
		error:function(request,status,error){
			jsonValue = jQuery.parseJSON(request.responseText);
			code = jsonValue.code;
			alert(jsonValue.errors[0].reason);
			if(code == 'C003'){
				for(var i in jsonValue.errors){
					console.log(code +" : "+jsonValue.errors[i].reason);
				}
			}
		}
	});
}
</script>

<body>
<div class="container" style="margin-top: 50px">
	<div class="form">
		<h2>게시글 수정</h2>
			<table class="table">
				<tr>
					<td><input id="category" type="text" class="form-control" readonly="readonly" value="${responseArticleUpdateDto.category }"/></td>
				<tr>
				<tr>
					<td><input id="title" name="title" type="text" class="form-control" placeholder="제목" maxlength="50" value="${responseArticleUpdateDto.title }"></td>
				</tr>
				<tr>
					<td><textarea id="content" name="content" class="form-control" placeholder="내용" onkeydown="resize(this)"></textarea>
				</tr>
			</table>
		<a href="/" class="btn btn-primary">목록</a>
		<input type="button" class="btn btn-primary" value="완료" onclick="check_form();">
	</div>
</div>
</body>
</html>