package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import m_board.BoardDAO;
import m_board.BoardDTO;
import member.MemberDAO;
import member.MemberDTO;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

//http://localhost:8181/JSP_MY_PROJ/*.us
@WebServlet("*.us")
public class Member_Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Member_Controller() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		//한글이 깨지지 않도록 처리
		request.setCharacterEncoding("UTF-8");
		
		String uri = request.getRequestURI();
		String path = uri.substring(uri.lastIndexOf("/"));
		
		if (path.equals("/login.us")) {
			System.out.println("login.us 요청 처리");
			
			//1. client에서 넘긴 파라미터 변수 값을 받아서 변수에 저장
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			
			//2. 넘겨 받은 값을 MemberDTO에 저장
			MemberDTO dto = new MemberDTO();
			dto.setId(id);
			dto.setPassword(password);
			
			//3. MemberDAO의 login(dto)
			MemberDAO dao = new MemberDAO();
			
			//리턴받을 MemberDTO 선언
			MemberDTO member = new MemberDTO();
			
			member = dao.login(dto);
			
			//member가 null일 경우: 인증 실패, 그렇지 않을 경우: 인증 성공
			if (member == null) {	//인증 실패
				System.out.println("인증 실패했습니다.");
				response.sendRedirect("LoginForm.jsp");
			
			} else {				//인증 성공
				//세션의 변수의 값을 할당하고 view 페이지로 전송
				System.out.println("인증 성공했습니다.");
				
				HttpSession session = request.getSession();
				session.setAttribute("id", member.getId());
				session.setAttribute("role", member.getRole());
				
				response.sendRedirect("LoginForm.jsp");
			}
			
		} else if (path.equals("/insertMember.us")) {
			System.out.println("insertMember.us 요청 처리");
			
			//로직 처리
			
			//1. 클라이언트의 넘어오는 변수가 잘 들어오는 지 확인(클라이언트 요청)
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			String addr = request.getParameter("addr");
			
			//2. 클라이언트에서 넘어오는 변수의 값을 DTO에 setter 주입
			MemberDTO dto = new MemberDTO();
			dto.setId(id);
			dto.setPassword(password);
			dto.setPhone(phone);
			dto.setEmail(email);
			dto.setAddr(addr);
			
			//3. DAO에 insertMember(dto)
			MemberDAO dao = new MemberDAO();
			//리턴받을 MemberDTO 선언
			MemberDTO member = new MemberDTO();
			
			member = dao.login(dto);
			
			//member가 null일 경우: 인증 실패, 그렇지 않을 경우: 인증 성공
			if (member == null) {	//인증 실패
				System.out.println("인증 실패했습니다.");
				response.sendRedirect("insertMember.jsp");
			
			} else {				//인증 성공
				//세션의 변수의 값을 할당하고 view 페이지로 전송
				System.out.println("인증 성공했습니다.");
				
				HttpSession session = request.getSession();
				session.setAttribute("id", member.getId());
				session.setAttribute("role", member.getRole());
				
				response.sendRedirect("insertMember.jsp");
			}
			
			
		} else if (path.equals("/logout.us")) {
			System.out.println("logout.us 요청 처리");
			
			//1. 사용자 세션 변수의 모든 값을 삭제함
			HttpSession session = request.getSession();
			
			//세션 변수에 담긴 모든 변수의 값을 삭제
			session.invalidate();
			
			//뷰페이지로 이동
			response.sendRedirect("http://localhost:8181/JSP_MY_PROJ");
		
		} else if (path.equals("/getMemberList.us")) {
			System.out.println("/getMemberList.us 요청");
			
			//로직 처리
			
			//1. MemberDTO 객체 생성
			MemberDTO dto = new MemberDTO();
			
			//2. MemberDAO 객체에 getMemberList(dto)
			MemberDAO dao = new MemberDAO();
			
			//리턴받을 변수 선언
			List<MemberDTO> memberList = new ArrayList<>();
			
			//memberList: DB의 member 테이블의 레코드를 dto로 저장 후 ArrayList 내의 각 방에 저장된 상태
			memberList = dao.getMemberList(dto);
			
			//memberList 클라이언트 view 페이지로 전송: Session 변수에 담아서 client 뷰 페이지로 전송
			//client의 session 정보를 가져와서 session 변수에 할당
			HttpSession session = request.getSession();
			
			//세션에 memberList를 추가
			session.setAttribute("memberList", memberList);
			
			//클라이언트 뷰 페이지
			response.sendRedirect("getMemberList.jsp");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
