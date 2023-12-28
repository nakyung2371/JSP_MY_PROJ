package m_board;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import common.JDBCUtil;

public class BoardDAO {
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	//sql 쿼리를 상수로 지정
	//insert 쿼리
	private final String BOARD_INSERT =
			"insert into m_board (id, m_title, m_write, m_cont)"
			+ "values ((select nvl(max(id),0) + 1 from m_board), ?, ?, ?)";
	
	//DB의 m_board 테이블의 모든 레코드를 출력하는 쿼리: 레코드가 여러 개: dto -> AraayList 리턴
	private final String BOARD_LIST = "select * from m_board order by id desc";
	
	//DB의 m_board 테이블의 글 상세 조회: 레코드 1개 <- dto
	private final String BOARD_GET = "select * from m_board where id = ?";
	
	//DB의 m_board 테이블의 업데이트 퀴리
	private final String BOARD_UPDATE = "update m_board set m_title = ?, m_write = ?, m_cont = ? where id = ?";

	//DB의 m_board 테이블의 레코드 삭제
	private final String BOARD_DELETE = "delete m_board where id = ?";
	
	//글 조회수를 늘리는 쿼리
	private final String ADD_CNT = "update m_board set cnt = cnt + 1 where id = ?";
	
	//insertBoard(BoardDTO dto) 메소드:
	public void insertBoard (BoardDTO dto) {
		System.out.println("insertBoard 기능 처리");
		
		try {
			
			//conn, pstmt 객체 활성화
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(BOARD_INSERT);
			
			//pstmt 객체의 ? 변수의 값 할당
			pstmt.setString(1, dto.getM_title());
			pstmt.setString(2, dto.getM_write());
			pstmt.setString(3, dto.getM_cont());
			/*
			System.out.println(dto.getM_title());
			System.out.println(dto.getM_write());
			System.out.println(dto.getM_cont());
			*/
			//pstmt를 실행해서 DB에 저장
			pstmt.executeUpdate();
			
			System.out.println("m_board 테이블에 값이 insert 되었습니다.");
			
		} catch (Exception e) {
			System.out.println("m_board 테이블에 값이 insert 되지 않았습니다.");
			e.printStackTrace();
			
		} finally {
			//사용한 객체 제거
			JDBCUtil.close(pstmt, conn);			
		}
	}
	
	//m_board 테이블의 전체 레코드를 출력하는 메소드
	//DB의 레코드 하나를 BoardDTO에 담는다. 각각의 BoardDTO를 ArrayList에 담아서 리턴
	//rs, pstmt, conn
	public List<BoardDTO> getBoardList(BoardDTO dto) {
		//중요: ArrayList는 While 블락 밖에서 선언
		//	   ArrayList에 저장되는 BoardDTO 선언은 While 블락 안에서 선언
		
		List<BoardDTO> boardList = new ArrayList<>();
		
		try {
			conn = JDBCUtil.getConnection();	//conn 객체 활성화: Oracle, XE, HR12, 1234
			pstmt = conn.prepareStatement(BOARD_LIST);
			
			//pstmt를 실행 후 rs에 레코드를 저장
			rs = pstmt.executeQuery();
			
			System.out.println("DB Select 성공");
			
			//rs의 각 레코드를 BoardDTO에 저장 -> 각각의 DTO를 ArrayList에 저장
				//if, do ~ while <-> while
				//rs.next(): 다음 레코드가 존재하면 true, 커서가 다음 레코드로 이동
			
			//BoardDTO board = new BoardDTO(); 		//밖에서 BoardDTO를 선언할 시 1번째 레코드만 반복 출력이 됨
			
			while (rs.next()) { //한번 호출이 되면
				//BoardDTO 객체 생성
				
				BoardDTO board = new BoardDTO();	//루프 블락 내에 선언 | while문 안에 선언해야 함
				
				board.setId(rs.getInt("id"));
				board.setM_title(rs.getString("m_title"));
				board.setM_write(rs.getString("m_write"));
				board.setRegdate(rs.getDate("regdate"));
				board.setCnt(rs.getInt("cnt"));
				
				//boardList: ArrayList의 add 메소드를 사용해서 boardDTO를 저장함
				boardList.add(board);
			}
			
		} catch (Exception e) {
			System.out.println("DB Select 실패");
			e.printStackTrace();	//실패할 경우 콘솔에 오류 정보 출력
			
		} finally {
			//사용한 객체 반납: rs, pstmt, conn
			JDBCUtil.close(rs, pstmt, conn);		
		}
				
		return boardList;
	}
	
	//글 상세 조회: getBoard(dto) 
	public BoardDTO getBoard(BoardDTO dto) {
		System.out.println("getBoard 메소드 호출 성공");
		
		//조회수 증가 메소드 호출
		addCNT(dto);
		
		BoardDTO board = new BoardDTO();
		
		try {
			conn = JDBCUtil.getConnection();
			//BOARD_GET = "select * from m_board where id = ?"
			pstmt = conn.prepareStatement(BOARD_GET);
			pstmt.setInt(1, dto.getId());
			
			//rs: 레코드 1개
			rs = pstmt.executeQuery();
			
			//rs의 값이 존재할 때
			while (rs.next()) {
				
				board.setId(rs.getInt("id"));
				board.setM_title(rs.getString("m_title"));
				board.setM_write(rs.getString("m_write"));
				board.setM_cont(rs.getString("m_cont"));
				board.setRegdate(rs.getDate("regdate"));
				board.setCnt(rs.getInt("cnt"));
				
				System.out.println("RS의 레코드를 dto 저장 성공");
			}
			
		} catch (Exception e) {
			System.out.println("글 상세 조회 실패");
			e.printStackTrace();			
		} finally {
			JDBCUtil.close(rs, pstmt, conn);
		}
		
		return board;
	}

	//글 수정 메소드: updateBoard(dto)
	public void updateBoard(BoardDTO dto) {
		System.out.println("updateBoard 메소드 호출됨");
		
		try {
			conn = JDBCUtil.getConnection();
			//BOARD_UPDATE = "update m_board set m_title = ?, m_write = ?, m_cont = ? where id = ?"
			pstmt = conn.prepareStatement(BOARD_UPDATE);
			
			//? 변수에 값을 할당
			pstmt.setString(1, dto.getM_title());
			pstmt.setString(2, dto.getM_write());
			pstmt.setString(3, dto.getM_cont());
			pstmt.setInt(4, dto.getId());
			
			//쿼리를 실행
			pstmt.executeUpdate();
			
			System.out.println("DB 업데이트 성공");
			
		} catch (Exception e) {
			System.out.println("DB 업데이트 실패");
			e.printStackTrace();
			
		} finally {
			JDBCUtil.close(pstmt, conn);
		}
	}

	//글 삭제 메소드: deleteBoard(dto)
	public void deleteBoard (BoardDTO dto) {
		
		try {
			conn = JDBCUtil.getConnection();
			//BOARD_DELETE = "delete board where id = ?"
			pstmt = conn.prepareStatement(BOARD_DELETE);
			
			//? 변수값 할당
			pstmt.setInt(1, dto.getId());
			
			//쿼리 실행
			pstmt.executeUpdate();		//insert, update, delete 구문일 때 실행
			
			System.out.println("DB 레코드 삭제 성공");
						
		} catch (Exception e) {
			System.out.println("DB 레코드 삭제 실패");
			e.printStackTrace();
			
		} finally {
			JDBCUtil.close(pstmt, conn);
		}
	}

	//글 조회수 늘리는 메소드
	public void addCNT(BoardDTO dto) {
		
		try {
			conn = JDBCUtil.getConnection();
			//ADD_CNT = "update board set cnt = cnt + 1 where id = ?"
			pstmt = conn.prepareStatement(ADD_CNT);
			
			//? 변수값 할당
			pstmt.setInt(1, dto.getId());
			
			//쿼리 실행
			pstmt.executeUpdate();
			
			System.out.println("조회수 입력 성공");
			
		} catch (Exception e) {
			System.out.println("조회수 입력 실패");
			e.printStackTrace();
			
		} finally {
			JDBCUtil.close(pstmt, conn);
			
		}
		
	}
	
}















