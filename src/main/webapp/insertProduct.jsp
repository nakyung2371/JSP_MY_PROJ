<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import = "member.MemberDTO" %>
<%

MemberDTO member = new MemberDTO();
String id = (String) session.getAttribute("id");
String role = (String) session.getAttribute("role");

	if (id == null) {
%>
		로그인이 필요합니다.
<%
	return;
	}
%>

<%
	if (!role.equals("Admin")){
%>
		권한이 없습니다.
		<p/><br> <a href = "http://localhost:8181/JSP_MY_PROJ"> 홈으로 </a>
<%
	return;
	}
%>

    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 새 상품 등록 </title>
</head>
<body>

	<center>
	<h1> 새 상품 등록 - Admin만 등록 가능 </h1>
	<hr>
	
	<form method = "post" action = "insertProduct.act" >
		<table border = "1" cellpadding = "10" cellspacing = "0">
			<tr>
				<td bgcolor = "#E4F7BA"> 상품 이름 </td>
				<td> <input type = "text" name = "name"> </td>
			</tr>
			<tr>
				<td bgcolor = "#E4F7BA"> 가격 </td>
				<td> <input type = "text" name = "price" size = "10"> </td>
			</tr>
			<tr>
				<td bgcolor = "#E4F7BA"> 상품 설명</td>
				<td> <textarea name = "content" cols = "40" rows = "10"> </textarea></td>
			</tr>
			<tr>
				<td colspan = "2" align = "center"> 
					 <input type = "submit" value = "새 글 등록">
				</td>
			</tr>
		</table>
	</form>
		
		<br> <a href = "http://localhost:8181/JSP_MY_PROJ"> 홈으로 </a>
		<p/> <a href = "getProductList.act"> 상품 목록 </a>
	
	</center>
</body>
</html>