package test;

import m_board.BoardDAO;
import m_board.BoardDTO;

public class Board_getBoard_Test {

	public static void main(String[] args) {
		//1. dto에 조회할 id 값만 할당 후 dao.getBoard(dto)
		BoardDTO dto = new BoardDTO();
		dto.setId(1);
		
		//2. dao 메소드 호출 getBoard(dto)
		BoardDAO dao = new BoardDAO();
		
		//리턴으로 돌려받는 변수 선언
		BoardDTO board = new BoardDTO();
		
		board = dao.getBoard(dto);
		
		System.out.println(board);
		
	}

}

