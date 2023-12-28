<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import = "member.MemberDTO" %>

<%
	//세션 변수의 값을 읽어옴
	String sessionId = (String) session.getAttribute("id");
	String sessionRole = (String) session.getAttribute("role");
%>

<%

MemberDTO member = new MemberDTO();
String id = (String) session.getAttribute("id");

	if (id != null) {
%>
		이미 로그인 되었습니다.
		<p/><br> <a href = "http://localhost:8181/JSP_MY_PROJ"> 홈으로 </a>
<%
	return;
	}
%>  

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 회원가입 </title>
</head>
<body>
	<h1> 회원가입 페이지 </h1>
	<hr>
	</p>
	
	<%
		if (sessionId == null) {		//로그인 되지 않은 상태
	%>
	<form method = "post" action = "insertMember.us">
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
				<td> 휴대폰 번호 </td>
				<td> <input type = "text" name = "phone"> </td>
			</tr>
			<tr>
				<td> 이메일 </td>
				<td> <input type = "text" name = "email"> </td>
			</tr>
			<tr>
				<td> 주소 </td>
				<td> <input type = "text" name = "addr"> </td>
			</tr>
			<tr>
				<td colspan = "2" align = "center"> <input type = "submit" value = "회원가입"> </td>
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