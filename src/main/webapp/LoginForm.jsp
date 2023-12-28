<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<%
	//세션 변수의 값을 읽어옴
	String sessionId = (String) session.getAttribute("id");
	String sessionRole = (String) session.getAttribute("role");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<center>
	<h1> 로그인 페이지 </h1>
	<hr>
	</p>
	
	<%
		if (sessionId == null) {		//로그인 되지 않은 상태
	%>
	<form method = "post" action = "login.us">
		<table>
			<tr>
				<td> ID </td>
				<td> <input type = "text" name = "id"> </td>
			</tr>
			<tr>
				<td> PASSWORD </td>
				<td> <input type = "password" name = "password"> </td>
			</tr>
			<tr>
				<td colspan = "2" align = "center"> <input type = "submit" value = "로그인"> </td>
			</tr>
		</table>
	</form>
	
	<p/> <a href = "http://localhost:8181/JSP_MY_PROJ"> 홈으로 </a>

	<%
		} else {	//로그인 된 상태
	%>
	
		<%= sessionId %>님 로그인 성공되었습니다. <p/>
		당신의 권한은 <%= sessionRole %>입니다. <p/>
		
		<a href = "http://localhost:8181/JSP_MY_PROJ"> 홈으로 </a> <p/>
		<a href = "logout.us"> 로그아웃 </a>
	
	<%
		}
	%>
	</center>
</body>
</html>