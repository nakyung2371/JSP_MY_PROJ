package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import m_board.BoardDAO;
import m_board.BoardDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//http://localhost:8181/JSP_MY_PROJ/*.do
@WebServlet ("*.do")
public class Board_Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Board_Controller() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		//client에서 보내는 get, post 요청 모두 처리
		//한글이 깨지지 않도록 처리
		request.setCharacterEncoding("UTF-8");
		
		System.out.println("do 요청을 처리하는 controller입니다.");
		
		//URL: http://localhost:8181/JSP_MY_PROJ/my.do
		//URI: /JSP_MY_PROJ/my.do
		
		String uri = request.getRequestURI();
		System.out.println(uri);
		
		String path = uri.substring(uri.lastIndexOf("/"));
		System.out.println(path);
		System.out.println("============================");
		
		if (path.equals("/insertBoard.do")) {
			System.out.println("/insertBoard.do 요청");
			//로직 처리
			
			//1. 클라이언트의 넘어오는 변수가 잘 들어오는 지 확인(클라이언트 요청)
			String title = request.getParameter("m_title");
			String write = request.getParameter("m_write");
			String content = request.getParameter("m_cont");
			
			//2. 클라이언트에서 넘어오는 변수의 값을 DTO에 setter 주입
			BoardDTO dto = new BoardDTO();
			dto.setM_title(title);
			dto.setM_write(write);
			dto.setM_cont(content);
						
			//3. DAO에 insertBoard(dto)
			BoardDAO dao = new BoardDAO();
			dao.insertBoard(dto);
			
			//비즈니스 로직 완료: DTO, DAO
			
			//4. 뷰 페이지 전송
			response.sendRedirect("getBoardList.do");
			
		} else if (path.equals("/getBoardList.do")) {
			System.out.println("/getBoardList.do 요청");
			
			//로직 처리
			
			//1. BoardDTO 객체 생성
			BoardDTO dto = new BoardDTO();
			
			//2. BoardDAO 객체에 getBoardList(dto)
			BoardDAO dao = new BoardDAO();
			
			//리턴받을 변수 선언
			List<BoardDTO> boardList = new ArrayList<>();
			
			//boardList: DB의 m_board 테이블의 레코드를 dto로 저장 후 ArrayList 내의 각 방에 저장된 상태
			boardList = dao.getBoardList(dto);
			
			//boardList 클라이언트 view 페이지로 전송: Session 변수에 담아서 client 뷰 페이지로 전송
			//client의 session 정보를 가져와서 session 변수에 할당
			HttpSession session = request.getSession();
			
			//세션에 boardList를 추가
			session.setAttribute("boardList", boardList);
			
			//클라이언트 뷰 페이지
			response.sendRedirect("getBoardList.jsp");
	
			
		} else if (path.equals("/getBoard.do")) {
			System.out.println("/getBoard.do 요청");
			//로직 처리
			
			//1. client에서 넘어오는 파라미터 id 변수의 값을 읽어서 dto에 저장 후 dao.getBoard(dto)
			//http://localhost:8181/JSP_MY_PROJ/getBoard.do?id=5
			
			int id = Integer.parseInt(request.getParameter("id"));
			
			
			//2. dto에 id 값을 setter 주입
			BoardDTO dto = new BoardDTO();
			dto.setId(id);
			
			//3. DAO의 getBoard(dto) 호출 수 리턴 값을 받아서 저장
			BoardDAO dao = new BoardDAO();
			
			//리턴 값을 받을 DTO 선언
			BoardDTO board = new BoardDTO();
			board = dao.getBoard(dto);
			
			//4. 세션 변수에 저장 후 뷰 페이지로 전송
			HttpSession session = request.getSession();
			
			session.setAttribute("board", board);
			
			//5. 뷰 페이지
			response.sendRedirect("getBoard.jsp");
			
						
		} else if (path.equals("/updateBoard.do")) {
			System.out.println("/updateBoard.do 요청");
			//로직 처리 
			
			//1. 클라이언트의 파라미터의 변수를 받아서 새로운 변수에 저장
			String title = request.getParameter("m_title");
			String write = request.getParameter("m_write");
			String content = request.getParameter("m_cont");
			int id = Integer.parseInt(request.getParameter("id"));
			
			//2. 변수를 BoardDTO에 setter 주입
			BoardDTO dto = new BoardDTO();
			dto.setM_title(title);
			dto.setM_write(write);
			dto.setM_cont(content);
			dto.setId(id);
			
			//3. BoardDAO에 updateBoard(dto)
			BoardDAO dao = new BoardDAO();
			dao.updateBoard(dto);
			
			//4. 뷰 페이지로 이동(업데이트 후 리스트 페이지로 이동)
			response.sendRedirect("getBoardList.do");
			
		} else if (path.equals("/deleteBoard.do")) {
			System.out.println("/deleteBoard.do 요청");
			//로직 처리 
			
			//1. 클라이언트의 파라미터 변수의 값 할당: id - 파라미터로 넘어오는 id는 String이기 때문에 int 타입으로 변환
			String s_id = request.getParameter("id");
			int id = Integer.parseInt(s_id);
			
			//2. 변수의 값을 BoardDTO에 주입
			BoardDTO dto = new BoardDTO();
			dto.setId(id);
			
			//3. BoardDAO에 메소드 호출: deleteBoard(dto)
			BoardDAO dao = new BoardDAO();
			dao.deleteBoard(dto);
			
			//4. 뷰 페이지로 이동
			response.sendRedirect("getBoardList.do");
			
			
		}
			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
