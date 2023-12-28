<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "product.ProductDTO" %>

<%
	//session 변수에 담긴 값을 불러옴: 서버의 RAM
	ProductDTO product = new ProductDTO();

	//session 변수의 값을 가지고 올 때 다운 캐스팅이 필요하다.
	product = (ProductDTO) session.getAttribute("product");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 상품 상세 페이지 </title>
</head>
<body>
	<center>
	<h1> 상품 상세 페이지 </h1>
	<hr>
	<br> <br>
	<form method = "post" action = "updateProduct.act">
		
		<!-- 글 수정 시 조건을 처리할 컬럼 -->
		<input type = "hidden" name = "id" value = "<%= product.getId() %>">
			
		
		<table border = "1" width = "700px" cellpadding = "5px">
		<tr>
			<td bgcolor = "#E4F7BA" align = "center"> 상품명 </td>
			<td> <input type = "text" name = "name" value = "<%= product.getName() %>"> </td>
		</tr>
		<tr>
			<td bgcolor = "#E4F7BA" align = "center"> 가격 </td>
			<td> <input type = "text" name = "price" value = "<%= product.getPrice() %>"> </td>
		</tr>
		<tr>
			<td bgcolor = "#E4F7BA" align = "center"> 상세 설명 </td>
			<td> <textarea name = "content" rows="10" cols="70"> <%= product.getContent() %></textarea> </td>
		</tr>
		<tr>
			<td bgcolor = "#E4F7BA" align = "center"> 등록일 </td>
			<td> <%= product.getRegdate() %> </td>
		</tr>
		<tr>
			<td colspan = "2" align = "center"> <input type = "submit" value = "글 수정하기"> </td>
		</tr>
		
		</table>
	</form>
	
	<br> <br>
	<a href = "deleteProduct.act?id=<%= product.getId() %>">
		글 삭제
	</a>
	
	<p/> <a href = "http://localhost:8181/JSP_MY_PROJ"> 홈으로 </a>
	<p/> <a href = "getProductList.act"> 글 목록 </a>
	<p/> <a href = "insertProduct.jsp"> 새 글 쓰기</a>
	
	
	</center>
	
	

</body>
</html>