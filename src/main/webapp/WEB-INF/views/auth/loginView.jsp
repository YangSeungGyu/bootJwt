<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<script type="text/javascript" src="${contextPath}/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.form.js"></script>

<style>
	.loginDiv {
		width : 600px;
  		height : 400px;
  		background-color: gray;
  		margin: auto;
  		margin-top: 150px;
  		
	}
	
	input {
		 width : 200px;
		 height : 25px;
		 margin:10px 5px 0px 10px;
	}
	
	.span50 {
		display:inline-block;
		 width : 50px;
		 padding : 5px 5px 5px 5px;
	}
	
	.bt100pct{
		width : 100%;
		height :30px;
		padding : 5px 5px 5px 5px;
	}
	
	
</style>
<title>Insert title here</title>
</head>
<body>
	<div class="loginDiv" >
		<form id="loginFrm" method="post">
			<div style=" padding: 50px 0px 0px 0px;">	
				<span class="span50">ID</span><input type="text"  name="userId" value="admin"><br/>
				<span class="span50">PW</span><input type="password" name="userPwd"  value="1234"><br/>
				<button class="bt100pct" type="button" onclick="login()">로그인</button>
			</div>
		</form>
	</div>
</body>

<script type="text/javascript">
	function login(){
	
		$('#loginFrm').ajaxSubmit({
			url :'/auth/login',
			dataType: 'json',
			success: function(result){
				
				console.log(result.resultCode +" / " +result.resultMsg);
				if(result.resultCode == "S001"){
					let data = result.data;
					let token = data.token;
					alert(token);
				}else {
					alert(result.resultCode +" / " +result.resultMsg);
				} 
			},
			error: function(xhr){
				
			}
		});
	}
	
</script>
</html>