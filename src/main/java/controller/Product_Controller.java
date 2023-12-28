package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import product.ProductDAO;
import product.ProductDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//http://localhost:8181/JSP_MY_PROJ/*.act
@WebServlet("*.act")
public class Product_Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Product_Controller() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		request.setCharacterEncoding("UTF-8");
		
		System.out.println("act 요청을 처리하는 controller입니다.");
		
		//URL: http://localhost:8181/JSP_MY_PROJ/my.act
		//URI: /JSP_MY_PROJ/my.act
		
		String uri = request.getRequestURI();
		System.out.println(uri);
		
		String path = uri.substring(uri.lastIndexOf("/"));
		System.out.println(path);
		System.out.println("============================");
		
		if (path.equals("/insertProduct.act")) {
			System.out.println("/insertProduct.act 요청");
			//로직 처리
			
			//1. 클라이언트의 넘어오는 변수가 잘 들어오는지 확인(클라이언트 요청)
			String name = request.getParameter("name");
			Double price = Double.parseDouble(request.getParameter("price"));
			String content = request.getParameter("content");
			
			//2. 클라이언트에서 넘어오는 변수의 값을 DIO에 setter 주입
			ProductDTO dto = new ProductDTO();
			dto.setName(name);
			dto.setPrice(price);
			dto.setContent(content);
			
			//3. ProductDAO의 insertProduct(dto)
			ProductDAO dao = new ProductDAO();
			dao.insertProduct(dto);
			
			//비즈니스 로직 완료: DTO, DAO
			
			//4. 뷰 페이지 전송
			response.sendRedirect("getProductList.act");
			
		} else if (path.equals("/getProductList.act")) {
			System.out.println("/getProductList.act 요청");
			
			//로직 처리
			
			//1. ProductDTO 객체 생성
			ProductDTO dto = new ProductDTO();
			
			//2. ProductDAO 객체에 getProductList(dto)
			ProductDAO dao = new ProductDAO();
			
			//리턴받을 변수 선언
			List<ProductDTO> productList = new ArrayList<>();
			
			//productList: DB의 product 테이블의 레코드를 dto로 저장 후 ArrayList 내의 각 방에 저장된 상태
			productList = dao.getProductList(dto);
			
			//productList 클라이언트 view 페이지로 전송: Session 변수에 담아서 client 뷰 페이지로 전송
			//client의 session 정보를 가져와서 session 변수에 할당
			HttpSession session = request.getSession();
			
			//세션에 productList를 추가
			session.setAttribute("productList", productList);
			
			//클라이언트 뷰 페이지
			response.sendRedirect("getProductList.jsp");
	
			
		} else if (path.equals("/getProduct.act")) {
			System.out.println("/getProduct.act 요청");
			//로직 처리
			
			//1. client에서 넘어오는 파라미터 id 변수의 값을 읽어서 dto에 저장 후 dao.getProduct(dto)
			//http://localhost:8181/JSP_MY_PROJ/getProduct.act?id=5
			
			int id = Integer.parseInt(request.getParameter("id"));
			
			
			//2. dto에 id 값을 setter 주입
			ProductDTO dto = new ProductDTO();
			dto.setId(id);
			
			//3. DAO의 getProduct(dto) 호출 수 리턴 값을 받아서 저장
			ProductDAO dao = new ProductDAO();
			
			//리턴 값을 받을 DTO 선언
			ProductDTO product = new ProductDTO();
			product = dao.getProduct(dto);
			
			//4. 세션 변수에 저장 후 뷰 페이지로 전송
			HttpSession session = request.getSession();
			
			session.setAttribute("product", product);
			
			//5. 뷰 페이지
			response.sendRedirect("getProduct.jsp");
			
						
		} else if (path.equals("/updateProduct.act")) {
			System.out.println("/updateProduct.act 요청");
			//로직 처리 
			
			//1. 클라이언트의 파라미터의 변수를 받아서 새로운 변수에 저장
			
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			Double price = Double.parseDouble(request.getParameter("price"));
			String content = request.getParameter("content");
			
			//2. 변수를 ProductDTO에 setter 주입
			ProductDTO dto = new ProductDTO();
			dto.setId(id);
			dto.setName(name);
			dto.setPrice(price);
			dto.setContent(content);
			
			//3. ProductDAO에 updateProduct(dto)
			ProductDAO dao = new ProductDAO();
			dao.updateProduct(dto);
			
			//4. 뷰 페이지로 이동(업데이트 후 리스트 페이지로 이동)
			response.sendRedirect("getProductList.act");
			
		} else if (path.equals("/deleteProduct.act")) {
			System.out.println("/deleteProduct.act 요청");
			//로직 처리 
			
			//1. 클라이언트의 파라미터 변수의 값 할당: id - 파라미터로 넘어오는 id는 String이기 때문에 int 타입으로 변환
			String s_id = request.getParameter("id");
			int id = Integer.parseInt(s_id);
			
			//2. 변수의 값을 ProductDTO에 주입
			ProductDTO dto = new ProductDTO();
			dto.setId(id);
			
			//3. ProductDAO에 메소드 호출: deleteProduct(dto)
			ProductDAO dao = new ProductDAO();
			dao.deleteProduct(dto);
			
			//4. 뷰 페이지로 이동
			response.sendRedirect("getProductList.act");
			
			
		}
			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}