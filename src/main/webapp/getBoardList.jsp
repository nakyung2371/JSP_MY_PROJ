<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import = "java.util.*" %>
<%@ page import = "m_board.BoardDTO" %>

<%
//Session 변수에 저장된 ArrayList를 가지고 옴
	List<BoardDTO> boardList = new ArrayList<>();

//세션에서 가져온 값은 Object 타입이어서 List 타입으로 변환
	try {
		boardList = (List<BoardDTO>) session.getAttribute("boardList");
		

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 글 목록 </title>
</head>
<body>
	<center>
	<h1> 글 목록 </h1>
	<hr>
	
	<table border = "1" width = "700">
		<tr>
			<th bgcolor="#FFEAEA" width="100px"> 번호 </th>
			<th bgcolor="#FFEAEA" width="200px"> 제목 </th>
			<th bgcolor="#FFEAEA" width="150px"> 작성자 </th>
			<th bgcolor="#FFEAEA" width="150px"> 등록일 </th>
			<th bgcolor="#FFEAEA" width="100px"> 조회수 </th>
		</tr>
		
		<!-- ArrayList의 BoardDTO를 꺼내서 출력: loop 돌리면서 출력 -->
		
		<%
			for (BoardDTO k : boardList) {
		%>
		
		<tr>
			<td align = "center"> <%= k.getId() %></td>
			<td> <a href = "getBoard.do?id=<%= k.getId()%>"> <%= k.getM_title() %>
			</a></td>
			<td> <%= k.getM_write() %> </td>
			<td> <%= k.getRegdate() %> </td>
			<td> <%= k.getCnt() %> </td>
		</tr>

		<%
			}
			//모두 사용됨: boardList
			//세션 변수의 값을 제거: 서버의 메모리에서 세션 변수 boardList에 저장한 값을 제거
			session.removeAttribute("boardList");
			
		} catch (Exception e) {
			response.sendRedirect("getBoardList.do");
		}
		%>
	</table>
	
	<br> <a href = "http://localhost:8181/JSP_MY_PROJ"> 홈으로 </a>
	<p/> <a href = "insertBoard.jsp"> 새 글 쓰기 </a>
		
	</center>
</body>
</html>