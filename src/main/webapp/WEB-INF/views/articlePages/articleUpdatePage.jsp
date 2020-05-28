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

<style>
textarea {min-height: 50px;}
</style>
<script type="text/javascript">
/* jQuery.fn.serializeObject = function() { 
    var obj = null; 
    try { 
        if(this[0].tagName && this[0].tagName.toUpperCase() == "FORM" ) { 
            var arr = this.serializeArray(); 
            if(arr){ obj = {}; 
            jQuery.each(arr, function() { 
                obj[this.name] = this.value; }); 
            } 
        } 
    }catch(e) { 
        alert(e.message); 
    }finally {} 
    return obj; 
} */
//textarea 입력값에 따라 height 조절
function resize(obj){
	obj.style.height = "1px";
	obj.style.height = (12+obj.scrollHeight)+"px";
}
function check_form(){
	//replace 로 공백 제거
	/* var inputForm_title = $("#title").val().replace(/\s|/gi,'');
	var inputForm_content = $("#content").val().replace(/\s|/gi,'');
	
	if(inputForm_title==""){
		alert("제목을 입력해주세요.");
		$("#title").val("");
		$("#title").focus();
		return false;
	}
	
	if(inputForm_writer==""){
		alert("작성자를 입력해주세요.");
		$("#writer").val("");
		$("#writer").focus();
		return false;
	}
	
	if(inputForm_content==""){
		alert("내용을 입력해주세요.");
		$("#content").val("");
		$("#content").focus();
		return false;
	} */
	
	//var articleCreateRequest = $("form[name=articleCreateForm]").serializeObject();
	var requestArticleUpdateDto = {
			title : $("#title").val(),
			content : $("#content").val(),
		}
	
	$.ajax({
		url:"/article",
		type:"patch",
		contentType : "application/json; charset=UTF-8",
		data: JSON.stringify(requestArticleUpdateDto),
		success:function(data){
			alert(data);
			location.href = "/article/"+data;
		},
		error:function(request,status,error){
			jsonValue = jQuery.parseJSON(request.responseText);
			code = jsonValue.code;
			if(code == 'C003'){
				$(".error").empty();
				for(var i in jsonValue.errors){
					$("#user_nickname").focus();
					$("#error_"+jsonValue.errors[i].field).append(jsonValue.errors[i].reason);
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
					<td><input id="category" type="text" class="form-control" readonly="readonly" value="${category }"/></td>
				<tr>
				<tr>
					<td><input id="title" name="title" type="text" class="form-control" placeholder="제목" maxlength="50"></td>
					<td><small id="error_title" class="error"></small></td>
				</tr>
				<tr>
					<td><textarea id="content" name="content" class="form-control" placeholder="내용" onkeydown="resize(this)"></textarea>
					<td><small id="error_content" class="error"></small></td>
				</tr>
			</table>
		<a href="/" class="btn btn-primary">목록</a>
		<input type="button" class="btn btn-primary" value="완료" onclick="check_form();">
	</div>
</div>
</body>
</html>