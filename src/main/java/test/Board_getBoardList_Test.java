package test;

import java.util.ArrayList;
import java.util.List;

import m_board.BoardDAO;
import m_board.BoardDTO;

public class Board_getBoardList_Test {

	public static void main(String[] args) {
		//BoardDAO의 getBoardList(dto) 메소드 테스트
		
		//1. BoardDTO 객체 생성
		BoardDTO dto = new BoardDTO();
		
		//2. BoardDAO 객체 생성 후 메소드 호출
		BoardDAO dao = new BoardDAO();
		
		//ArrayList 선언: <BoardDTO> 객체가 각 방에 들어있음.
		List<BoardDTO> boardList = new ArrayList<>();
		
		//boardList DB의 각 레코드를 DTO에 담아서 저장
		boardList = dao.getBoardList(dto);
		
		//ArrayList: boardList의 각 방의 BoardDTO 객체를 순환하면서 객체를 출력
		//For문을 사용해서 출력
		System.out.println("================= for문으로 출력 =================");
		for (int i = 0; i < boardList.size(); i++) {
			System.out.println(boardList.get(i));
		}
		
		System.out.println("============ Enhanced for문으로 출력 ============");
		for (BoardDTO b : boardList) {		//boardList: ArrayList의 각 방의 저장된 것을 b 변수로 꺼내서 출력
			System.out.println(b);			
		}

	}

}