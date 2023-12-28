package member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import common.JDBCUtil;
import m_board.BoardDTO;

public class MemberDAO {
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	private final String MEMBER_INSERT =
			"insert into member (id, password, phone, email, addr)"
			+ "values (?, ?, ?, ?, ?)";
	
	//DB의 member 테이블의 모든 레코드를 출력하는 쿼리: 레코드가 여러 개: dto -> AraayList 리턴
	private final String MEMBER_LIST = "select * from member order by role asc";
	
	private final String MEMBER_LOGIN = "select * from member where id = ? and password = ?";
	
	//로그인 메소드
	public MemberDTO login(MemberDTO dto) {
		System.out.println("login 메소드 호출");
		
		MemberDTO member = null;
		
		try {
			conn = JDBCUtil.getConnection();
			//MEMBER_LOGIN = "select * from member where id = ? and password = ?"
			pstmt = conn.prepareStatement(MEMBER_LOGIN);
			
			//? 변수의 값 할당
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPassword());
			
			//pstmt 실행 후 rs로 쿼리한 레코드 저장
			rs = pstmt.executeQuery();
			
			//rs의 값이 존재할 때 인증 성공: 레코드 1개가 출력
			while (rs.next()) {
				
				//리턴으로 돌려줄 dto 선언
				member = new MemberDTO();
				
				member.setId(rs.getString("ID"));
				member.setPassword(rs.getString("PASSWORD"));
				member.setPhone(rs.getString("PHONE"));
				member.setEmail(rs.getString("EMAIL"));
				member.setRegdate(rs.getDate("REGDATE"));
				member.setAddr(rs.getString("ADDR"));
				member.setRole(rs.getString("ROLE"));
				
				System.out.println(" - 인증 성공: 해당 ID와 Password가 DB에 존재합니다.");
			}
			
		} catch (Exception e) {
			System.out.println("인증 오류가 발생하였습니다.");
			e.printStackTrace();
			
		} finally {
			JDBCUtil.close(rs, pstmt, conn);
			
		}
		
		return member;
	}

	//insertMember(MemberDTO dto) 메소드:
	public void insertMember (MemberDTO dto) {
		System.out.println("insertMember 기능 처리");
		
		try {
			
			//conn, pstmt 객체 활성화
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(MEMBER_INSERT);
			
			//pstmt 객체의 ? 변수의 값 할당
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPassword());
			pstmt.setString(3, dto.getPhone());
			pstmt.setString(4, dto.getEmail());
			pstmt.setString(5, dto.getAddr());

			//pstmt를 실행해서 DB에 저장
			pstmt.executeUpdate();
			
			System.out.println("member 테이블에 값이 insert 되었습니다.");
			
		} catch (Exception e) {
			System.out.println("member 테이블에 값이 insert 되지 않았습니다.");
			e.printStackTrace();
			
		} finally {
			//사용한 객체 제거
			JDBCUtil.close(pstmt, conn);			
		}
	}
	
	//member 테이블의 전체 레코드를 출력하는 메소드
	//DB의 레코드 하나를 MemberDTO에 담는다. 각각의 MemberDTO를 ArrayList에 담아서 리턴
	//rs, pstmt, conn
	public List<MemberDTO> getMemberList(MemberDTO dto) {
		//중요: ArrayList는 While 블락 밖에서 선언
		//	   ArrayList에 저장되는 MemberDTO 선언은 While 블락 안에서 선언
		
		List<MemberDTO> memberList = new ArrayList<>();
		
		try {
			conn = JDBCUtil.getConnection();	//conn 객체 활성화: Oracle, XE, HR12, 1234
			pstmt = conn.prepareStatement(MEMBER_LIST);
			
			//pstmt를 실행 후 rs에 레코드를 저장
			rs = pstmt.executeQuery();
			
			System.out.println("DB Select 성공");
			
			//rs의 각 레코드를 MemberDTO에 저장 -> 각각의 DTO를 ArrayList에 저장
				//if, do ~ while <-> while
				//rs.next(): 다음 레코드가 존재하면 true, 커서가 다음 레코드로 이동
			
			//MemberDTO member = new MemberDTO(); 		//밖에서 MemberDTO를 선언할 시 1번째 레코드만 반복 출력이 됨
			
			while (rs.next()) { //한번 호출이 되면
				//MemberDTO 객체 생성
				
				MemberDTO member = new MemberDTO();	//루프 블락 내에 선언 | while문 안에 선언해야 함
				
				member.setId(rs.getString("id"));
				member.setPassword(rs.getString("password"));
				member.setPhone(rs.getString("phone"));
				member.setEmail(rs.getString("email"));
				member.setRegdate(rs.getDate("regdate"));
				member.setAddr(rs.getString("addr"));
				member.setRole(rs.getString("role"));
				
				//MemberList: ArrayList의 add 메소드를 사용해서 boardDTO를 저장함
				memberList.add(member);
			}
			
		} catch (Exception e) {
			System.out.println("DB Select 실패");
			e.printStackTrace();	//실패할 경우 콘솔에 오류 정보 출력
			
		} finally {
			//사용한 객체 반납: rs, pstmt, conn
			JDBCUtil.close(rs, pstmt, conn);		
		}
				
		return memberList;
	}
}
