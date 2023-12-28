<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import = "m_board.BoardDTO" %>

<%
	//session 변수에 담긴 값을 불러옴: 서버의 RAM
	BoardDTO board = new BoardDTO();

	//session 변수의 값을 가지고 올 때 다운 캐스팅이 필요하다.
	board = (BoardDTO) session.getAttribute("board");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 글 상세 페이지 </title>
</head>
<body>
	<center>
	<h1> 글 상세 페이지(수정 가능하도록) </h1>
	<hr>
	<br> <br>
	<form method = "post" action = "updateBoard.do">
		
		<!-- 글 수정 시 조건을 처리할 컬럼 -->
		<input type = "hidden" name = "id" value = "<%= board.getId() %>">
			
		
		<table border = "1" width = "700px" cellpadding = "5px">
		<tr>
			<td bgcolor = "#FFEAEA" align = "center"> 제목 </td>
			<td> <input type = "text" name = "m_title" value = "<%= board.getM_title() %>"> </td>
		</tr>
		<tr>
			<td bgcolor = "#FFEAEA" align = "center"> 작성자 </td>
			<td> <input type = "text" name = "m_write" value = "<%= board.getM_write() %>"> </td>
		</tr>
		<tr>
			<td bgcolor = "#FFEAEA" align = "center"> 내용 </td>
			<td> <textarea name = "m_cont" rows="10" cols="70"> <%= board.getM_cont() %></textarea> </td>
		</tr>
		<tr>
			<td bgcolor = "#FFEAEA" align = "center"> 등록일 </td>
			<td> <%= board.getRegdate() %> </td>
		</tr>
		<tr>
			<td bgcolor = "#FFEAEA" align = "center"> 조회수</td>
			<td> <%= board.getCnt() %></td>
		</tr>
		<tr>
			<td colspan = "2" align = "center"> <input type = "submit" value = "글 수정하기"> </td>
		</tr>
		
		</table>
	</form>
	
	<br> <br>
	<a href = "deleteBoard.do?id=<%= board.getId() %>">
		글 삭제
	</a>
	
	<p/> <a href = "http://localhost:8181/JSP_MY_PROJ"> 홈으로 </a>
	<p/> <a href = "getBoardList.do"> 글 목록 </a>
	<p/> <a href = "insertBoard.jsp"> 새 글 쓰기</a>
	
	
	</center>
	
	

</body>
</html>