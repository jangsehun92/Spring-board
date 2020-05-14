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
		url:"/account/"+${id },
		type:"get",
		success:function(data){
			console.log("회원정보 가져오기 : " + ${id });
			$("#nickname").html(data.nickname);
			accountArticleList(${id });
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
};

//회원정보 가져오기 성공 후 
function accountArticleList(id){
	$.ajax({
		url:"/articles/"+id,
		type:"get",
		success:function(data){
			console.log("회원정보 글 가져오기 : " + id);
			if(data.articles != null){
				$("#articles").append(
					"<div>"+
						"<div style='margin-top: 40px'>"+
							"<span>작성한 글 내역</span>"+
						"</div>"+
						"<table class='table table-hover'>"+
							"<thead>"+
								"<tr>"+
									"<td class='col-md-3'><b>제목</b></td>"+
									"<td class='col-md-6' align='right'><b>작성자</b></td>"+
									"<td class='col-md-1' align='right'><b>작성 날짜</b></td>"+
								"</tr>"+
							"</thead>"+
							"<tbody id='article_list'>"+
							
								"<tr>"+
									"<td><a href='/article/'>제목</a></td>"+
									"<td align='right'>작성자</td>"+
									"<td align='right'>작성날짜</td>"+
								"</tr>"+
								
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
					$("#article_list").append(
							"<tr>"+
							"<td><a href='/article/'>제목</a></td>"+
							"<td align='right'>작성자</td>"+
							"<td align='right'>작성날짜</td>"+
						"</tr>"
					);
				}
			}
			if(data.articles == null){
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
			<div class="panel-body" style="text-align: left;">
				<div id="articles">
					
				</div>
			</div>
		</div>
	</div>
</body>
</html>