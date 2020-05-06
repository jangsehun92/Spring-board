<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/views/commonPages/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>계정 찾기</title>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.5.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ajax_header.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<body>
<script type="text/javascript">
function check_form(){
	var name = $("#name").val().replace(/\s|/gi,'');
	var birth = $("#birth").val().replace(/\s|/gi,'');
	
	/* if(name=="") {
		alert("이름을 입력해주세요.");
		$("#password").focus();
		return false;
	}
	
	if(birth==""){
		alert("생년월일을 입력해주세요.");
		$("#passwordCheck").focus();
		return false;
	} */
	
	var AccountFindRequestDto = {
		name : $("#name").val(),
		birth : $("#birth").val(),
	}
	
	$.ajax({
		url:"/account/find-email",
		type:"post",
		contentType : "application/json; charset=UTF-8",
		dataType : "JSON",
		data: JSON.stringify(AccountFindRequestDto),
		success:function(data){
			$("#main").empty();
				$("#main").append(
				"<h1>가입한 계정</h1>"+
				"<table class='col-md-6 table table-hover'>"+
					"<thead class='thead-dark'>"+
							"<tr>"+
								"<td class='col-md-5'>가입한 Email</td>"+
								"<td class='col-md-1'>가입날짜</td>"+
							"</tr>"+
						"</thead>"+
						"<tbody id='accountList'>"+
						"</tbody>"+
				"</table>"+
				"<input type='button' class='btn btn-primary btn-block' value='비밀번호 찾기' style='margin-top: 10px;' onclick='find_password();'>"
				);
			$.each(data, function(index, value) {
				$("#accountList").append(
					"<tr>"+
						"<td>"+value.email+"</td>"+
						"<td>"+unix_timestamp(value.regdate)+"</td>"+
					"</tr>"
				);
			});
		}, 
		error:function(request,status,error){
			jsonValue = jQuery.parseJSON(request.responseText);
			code = jsonValue.code;
			$(".error").empty();
			if(code == 'A004'){
				$("#error_account").append(jsonValue.message);
			}
				
			if(code == 'C003'){
				for(var i in jsonValue.errors){
					$("#error_"+jsonValue.errors[i].field).append(jsonValue.errors[i].reason);
				}
			}
		}
	});
}

function unix_timestamp(time){
	var date = new Date(time);
	var year = date.getFullYear();
	var month = "0" + (date.getMonth()+1);
	var day = "0" + date.getDate();
	//var hour = "0" + date.getHours();
	//var minute = "0" + date.getMinutes();
	//var second = "0" + date.getSeconds();
	return year + "-" + month.substr(-2) + "-" + day.substr(-2);// + " " + hour.substr(-2) + ":" + minute.substr(-2);
}

function find_password(){
	location.href = "/account/find-password";
}
</script>
	<div class="container" style="margin-top: 80px;">
		<div id="main">
			<div class="col-md-6 main-block-left" style="align-items: center;">
				<div class="panel panel-default" style="color: #ddd;" >
					<div class="panel-heading">
						<h5 class="panel-header" style="text-align: center;">
							가입한 이메일 찾기
						</h5>
					</div>
					<div id="view">
						<form:form method="post" action="/account/find-email" class="form-signup form-user panel-body">
							<fieldset>
								<input type="text" class="form-control input-sm" id="name" name="name" placeholder="이름" maxlength="10" style="margin-top: 10px;">
								<small id="error_name" class="error"></small>
								<input type="text" class="form-control input-sm" id="birth" name="birth" placeholder="생년월일(ex:920409)" maxlength="6" style="margin-top: 10px;">
								<small id="error_birth" class="error"></small>
							</fieldset>
							<input type="button" class="btn btn-primary btn-block" value="이메일 찾기" style="margin-top: 10px;" onclick="return check_form();">
							<small id="error_account" class="error"></small>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>