<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import = "member.MemberDTO" %>
<%

MemberDTO member = new MemberDTO();
String id = (String) session.getAttribute("id");

	if (id == null) {
%>
		로그인이 필요합니다.
		<p/><br> <a href = "http://localhost:8181/JSP_MY_PROJ"> 홈으로 </a>
<%
	return;
	}
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 새 글 등록 </title>
</head>
<body>

	<center>
	<h1> 새 글 등록 - 로그인 한 사용자만 등록 가능 </h1>
	<hr>
	
	<form method = "post" action = "insertBoard.do">
		<table border = "1" cellpadding = "10" cellspacing = "0">
		<tr>
			<td bgcolor = "#FFEAEA"> 제목 </td>
			<td> <input type = "text" name = "m_title"> </td>
		</tr>
		<tr>
			<td bgcolor = "#FFEAEA"> 작성자 </td>
			<td> <input type = "text" name = "m_write" size = "10"> </td>
		</tr>
		<tr>
			<td bgcolor = "#FFEAEA"> 내용 </td>
			<td> <textarea name = "m_cont" cols = "40" rows = "10"> </textarea> </td>
		</tr>
		<tr>
			<td colspan = "2" align = "center">
				<input type = "submit" value = "새 글 등록">
			</td>	
		</tr>
		</table>
	</form>
	
		<br> <a href = "http://localhost:8181/JSP_MY_PROJ"> 홈으로 </a>
		<p/> <a href = "getBoardList.do"> 글 목록 </a>
	
	</center>

</body>
</html>