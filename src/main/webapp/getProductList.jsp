<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import = "java.util.*" %>
<%@ page import = "product.ProductDTO" %>
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
		<p/> <br> <a href = "http://localhost:8181/JSP_MY_PROJ"> 홈으로 </a>
<%
	return;
	}
%>

<%
//Session 변수에 저장된 ArrayList를 가지고 옴
	List<ProductDTO> productList = new ArrayList<>();

//세션에서 가져온 값은 Object 타입이어서 List 타입으로 변환
	try {
		productList = (List<ProductDTO>) session.getAttribute("productList");
		

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 상품 목록 </title>
</head>
<body>
	<center>
	<h1> 상품 목록 </h1>
	<hr>
	
	<table border = "1" width = "700">
		<tr>
			<th bgcolor="#E4F7BA" width="100px"> 상품번호 </th>
			<th bgcolor="#E4F7BA" width="250px"> 상품명 </th>
			<th bgcolor="#E4F7BA" width="150px"> 상품 가격 </th>
			<th bgcolor="#E4F7BA" width="150px"> 등록일 </th>
		</tr>
		
		<!-- ArrayList의 ProductDTO를 꺼내서 출력: loop 돌리면서 출력 -->
		
		<%
			for (ProductDTO k : productList) {
		%>
		
		<tr>
			<td align = "center"> <%= k.getId() %></td>
			<td> <a href = "getProduct.act?id=<%= k.getId()%>"> <%= k.getName() %>
			</a></td>
			<td> <%= k.getPrice() %>원 </td>
			<td> <%= k.getRegdate() %> </td>
		</tr>

		<%
			}
			//모두 사용됨: productList
			//세션 변수의 값을 제거: 서버의 메모리에서 세션 변수 productList에 저장한 값을 제거
			session.removeAttribute("productList");
			
		} catch (Exception e) {
			response.sendRedirect("getProductList.act");
		}
		%>
	</table>
	
	<br> <a href = "http://localhost:8181/JSP_MY_PROJ"> 홈으로 </a>
	<p/> <a href = "insertProduct.jsp"> 새 글 쓰기 </a>
		
	</center>
</body>
</html>