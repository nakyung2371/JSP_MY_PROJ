<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import = "java.util.*" %>
<%@ page import = "member.MemberDTO" %>

<%
//Session 변수에 저장된 ArrayList를 가지고 옴
	List<MemberDTO> memberList = new ArrayList<>();

//세션에서 가져온 값은 Object 타입이어서 List 타입으로 변환
	try {
		memberList = (List<MemberDTO>) session.getAttribute("memberList");
		

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 사용자 목록 </title>
</head>
<body>
	<center>
	<h1> 사용자 목록 </h1>
	<hr>
	
	<table border = "1" width = "840">
		<tr>
			<th bgcolor="#FFBE98" width="100px"> 아이디 </th>
			<th bgcolor="#FFBE98" width="100px"> 비밀번호 </th>
			<th bgcolor="#FFBE98" width="180px"> 휴대폰 번호 </th>
			<th bgcolor="#FFBE98" width="200px"> 이메일 </th>
			<th bgcolor="#FFBE98" width="100px"> 가입 날짜 </th>
			<th bgcolor="#FFBE98" width="80px"> 주소 </th>
			<th bgcolor="#FFBE98" width="80px"> 권한 </th>
		</tr>
		
		<!-- ArrayList의 MemberDTO를 꺼내서 출력: loop 돌리면서 출력 -->
		
		<%
			for (MemberDTO k : memberList) {
		%>
		
		<tr>
			<td align = "center"> <%= k.getId() %></td>
			<td align = "center">  <%= k.getPassword() %></td>
			<td align = "center"> <%= k.getPhone() %> </td>
			<td align = "center"> <%= k.getEmail() %> </td>
			<td align = "center"> <%= k.getRegdate() %> </td>
			<td align = "center"> <%= k.getAddr() %> </td>
			<td align = "center"> <%= k.getRole() %> </td>
		</tr>

		<%
			}
			//모두 사용됨: memberList
			//세션 변수의 값을 제거: 서버의 메모리에서 세션 변수 memberList에 저장한 값을 제거
			session.removeAttribute("memberList");
			
		} catch (Exception e) {
			response.sendRedirect("getMemberList.us");
		}
		%>
	</table>
	
	<br> <a href = "http://localhost:8181/JSP_MY_PROJ"> 홈으로 </a>
		
	</center>
</body>
</html>