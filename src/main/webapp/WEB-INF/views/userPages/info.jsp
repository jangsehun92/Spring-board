<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commonPages/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>profile</title>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.5.0.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script type="text/javascript">
window.onload = function() {
	$.ajax({
		url:"/account/${id }",
		type:"get",
		success:function(data){
			$("#nickname").html(data.nickname);
			if("${principal.id }" == "${id}"){
				$("#account_btn").append(
					"<div style='float: right'>"+
						"<a href='/account/edit' class='btn btn-primary'>정보 수정</a>"+
						"<a href='/account/passwordChange' class='btn btn-primary'>비밀번호 변경</a>"+
					"</div>"
				);
			}
			accountArticleList("${id }", 1);
		},
		error:function(request,status,error){
			jsonValue = jQuery.parseJSON(request.responseText);
			code = jsonValue.code;
			if(code == 'A010'){
				alert(jsonValue.message);
				console.log("code : " + jsonValue.code + " message : " + jsonValue.message);
				location.href = "/";
			}
		}
	});
};

//회원정보 가져오기 성공 후 
function accountArticleList(id, page){
	$.ajax({
		url:"/articles/account/"+id+"?page="+page,
		type:"get",
		success:function(data){
			console.log("회원정보 글 가져오기 : " + id);
			$("#main").empty();
			if(data.articles != ""){
				$("#main").append(
					"<div>"+
						"<div style='margin-top: 40px'>"+
							"<span>작성한 글 내역</span>"+
						"</div>"+
						"<table class='table table-hover'>"+
							"<thead>"+
								"<tr>"+
									"<td class='col-md-6'><b>제목</b></td>"+
									"<td class='col-md-1' align='right'><b>추천</b></td>"+
									"<td class='col-md-1' align='right'><b>작성 날짜</b></td>"+
								"</tr>"+
							"</thead>"+
							"<tbody id='articles'>"+
							
							"</tbody>"+
						"</table>"+
					"</div>"+
					"<div>"+
						"<nav aria-label='...' style='text-align: center;'>"+
							"<ul class='pagination' id='pagination'>"+
							"</ul>"+
						"</nav>"+
					"</div>"
				);
				for(var i in data.articles){
					$("#articles").append(
						"<tr>"+
							"<td><a href='/article/"+data.articles[i].id+"'>"+data.articles[i].title+"</a></td>"+
							"<td align='right'>"+data.articles[i].likeCount+"</td>"+
							"<td align='right'>"+uxin_timestamp(data.articles[i].regdate)+"</td>"+
						"</tr>"
					);
				}
				if(data.pagination.startPage > 1){
					$("#pagination").append("<li class=''><a href='javascript:void(0)' onclick='accountArticleList("+id+",1)' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li>");
				}else{
					$("#pagination").append("<li class='disabled'><a href='javascript:void(0)' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li>");
				}
				
				if(data.pagination.page > 1){
					$("#pagination").append("<li class=''><a href='javascript:void(0)' onclick='accountArticleList("+id+","+(data.pagination.page-1)+")' aria-label='Previous'><span aria-hidden='true'>&lang;</span></a></li>");
				}else{
					$("#pagination").append("<li class='disabled'><a href='javascript:void(0)' aria-label='Previous'><span aria-hidden='true'>&lang;</span></a></li>");
				}
				
				for(var iCount = data.pagination.startPage; iCount <= data.pagination.endPage; iCount++) {
					if (iCount == data.pagination.page) {
				       $("#pagination").append("<li class='active'><a href='javascript:void(0)'>"+iCount+"<span class='sr-only'></span></a></li>");
				    } else {
				    	$("#pagination").append("<li class=''><a href='javascript:void(0)' onclick='accountArticleList("+id+","+iCount+")'>" + iCount + "<span class='sr-only'></span></a></li>");
				    }
				}
				
				if(data.pagination.page < data.pagination.totalPage){
					$("#pagination").append("<li class=''><a a href='javascript:void(0)' onclick='accountArticleList("+id+","+(data.pagination.page+1)+")' aria-label='Next'><span aria-hidden='true'>&rang;</span></a></li>");
				}else{
					$("#pagination").append("<li class='disabled'><a href='javascript:void(0)' aria-label='Next'><span aria-hidden='true'>&rang;</span></a></li>");
				}
				
				if(data.pagination.endPage < data.pagination.totalPage){
					$("#pagination").append("<li class=''><a href='javascript:void(0)' onclick='accountArticleList("+id+","+data.pagination.totalPage+")' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li>");
				}else{
					$("#pagination").append("<li class='disabled'><a href='javascript:void(0)' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li>");
				}
			}
			else{
				$("#articles").append("<p align='center'>작성한 글이 없습니다.</p>");
			}
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
			if(code == 'C006'){
				alert(jsonValue.message);
				console.log("code : " + jsonValue.code + " message : " + jsonValue.message);
			}
		}
	});
	
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
</script>
<body>
	<div class="container" style="margin-top: 80px;">
		<div class="panel panel-default"
			style="color: #2e6da4; margin-bottom: 0px">
			<div class="panel-heading">
				<div class="row">
					<div class="com-md-6">
						<h5 id="nickname" class="panel-header" style="text-align: center;"></h5>
					</div>
				</div>
			</div>
			<div class="panel-body" id="panel-body"style="text-align: left;">
				<div id="account_btn">
				
				
				</div>
				<div id="main">
					
				</div>
			</div>
		</div>
	</div>
</body>
</html>