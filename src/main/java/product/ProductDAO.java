package product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import common.JDBCUtil;
import m_board.BoardDTO;

public class ProductDAO {
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	//sql 쿼리를 상수로 지정
	//insert 쿼리
	private final String PRODUCT_INSERT =
			"insert into product (id, name, price, content)"
			+ "values ((select nvl(max(id),0) + 1 from product), ?, ?, ?)";
	
	//DB의 product 테이블의 모든 레코드를 출력하는 쿼리: 레코드가 여러 개: dto -> AraayList 리턴
	private final String PRODUCT_LIST = "select * from product order by id asc";
	
	//DB의 product 테이블의 글 상세 조회: 레코드 1개 <- dto
	private final String PRODUCT_GET = "select * from product where id = ?";
	
	//DB의 product 테이블의 업데이트 퀴리
	private final String PRODUCT_UPDATE = "update product set name = ?, price = ?, content = ? where id = ?";

	//DB의 product 테이블의 레코드 삭제
	private final String PRODUCT_DELETE = "delete product where id = ?";
	
	
	//insertProduct(ProductDTO dto) 메소드:
	public void insertProduct(ProductDTO dto) {
		System.out.println("insertProduct 기능 처리");
		
		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(PRODUCT_INSERT);
			
			pstmt.setString(1, dto.getName());
			pstmt.setDouble(2, dto.getPrice());
			pstmt.setString(3, dto.getContent());
			
			pstmt.executeUpdate();
			
			System.out.println("product 테이블에 값이 insert 되었습니다.");
			
		} catch (Exception e) {
			System.out.println("product 테이블에 값이 insert 되지 않았습니다.");
			e.printStackTrace();
			
		} finally {
			JDBCUtil.close(pstmt, conn);
		}
	}


//product 테이블의 전체 레코드를 출력하는 메소드
	//DB의 레코드 하나를 ProductDTO에 담는다. 각각의 ProductDTO를 ArrayList에 담아서 리턴
	//rs, pstmt, conn
	public List<ProductDTO> getProductList(ProductDTO dto) {
		//중요: ArrayList는 While 블락 밖에서 선언
		//	   ArrayList에 저장되는 ProductDTO 선언은 While 블락 안에서 선언
		
		List<ProductDTO> productList = new ArrayList<>();
		
		try {
			conn = JDBCUtil.getConnection();	//conn 객체 활성화: Oracle, XE, HR12, 1234
			pstmt = conn.prepareStatement(PRODUCT_LIST);
			
			//pstmt를 실행 후 rs에 레코드를 저장
			rs = pstmt.executeQuery();
			
			System.out.println("DB Select 성공");
			
			//rs의 각 레코드를 ProductDTO에 저장 -> 각각의 DTO를 ArrayList에 저장
				//if, do ~ while <-> while
				//rs.next(): 다음 레코드가 존재하면 true, 커서가 다음 레코드로 이동
			
			//ProductDTO board = new ProductDTO(); 		//밖에서 ProductDTO를 선언할 시 1번째 레코드만 반복 출력이 됨
			
			while (rs.next()) { //한번 호출이 되면
				//ProductDTO 객체 생성
				
				ProductDTO product = new ProductDTO();	//루프 블락 내에 선언 | while문 안에 선언해야 함
				
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getDouble("price"));
				product.setContent(rs.getString("content"));
				product.setRegdate(rs.getDate("regdate"));
				
				//productList: ArrayList의 add 메소드를 사용해서 ProductDTO를 저장함
				productList.add(product);
			}
			
		} catch (Exception e) {
			System.out.println("DB Select 실패");
			e.printStackTrace();	//실패할 경우 콘솔에 오류 정보 출력
			
		} finally {
			//사용한 객체 반납: rs, pstmt, conn
			JDBCUtil.close(rs, pstmt, conn);		
		}
				
		return productList;
	}
	
	//상품 상세 조회: getProduct(dto) 
	public ProductDTO getProduct(ProductDTO dto) {
		System.out.println("getProduct 메소드 호출 성공");
		
		//조회수 증가 메소드 호출
		//addCNT(dto);
		
		ProductDTO product = new ProductDTO();
		
		try {
			conn = JDBCUtil.getConnection();
			//PRODUCT_GET = "select * from product where id = ?"
			pstmt = conn.prepareStatement(PRODUCT_GET);
			pstmt.setInt(1, dto.getId());
			
			//rs: 레코드 1개
			rs = pstmt.executeQuery();
			
			//rs의 값이 존재할 때
			while (rs.next()) {
				
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getDouble("price"));
				product.setContent(rs.getString("content"));
				product.setRegdate(rs.getDate("regdate"));

				
				System.out.println("RS의 레코드를 dto 저장 성공");
			}
			
		} catch (Exception e) {
			System.out.println("글 상세 조회 실패");
			e.printStackTrace();			
		} finally {
			JDBCUtil.close(rs, pstmt, conn);
		}
		
		return product;
	}

	//상품 수정 메소드: updateProduct(dto)
	public void updateProduct(ProductDTO dto) {
		System.out.println("updateProduct 메소드 호출됨");
		
		try {
			conn = JDBCUtil.getConnection();
			//PRODUCT_UPDATE = "update product set name = ?, price = ?, content = ? where id = ?";
			pstmt = conn.prepareStatement(PRODUCT_UPDATE);
			
			//? 변수에 값을 할당
			pstmt.setString(1, dto.getName());
			pstmt.setDouble(2, dto.getPrice());
			pstmt.setString(3, dto.getContent());
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

	//상품 삭제 메소드: deleteProduct(dto)
	public void deleteProduct (ProductDTO dto) {
		
		try {
			conn = JDBCUtil.getConnection();
			//PRODUCT_DELETE = "delete product where id = ?";
			pstmt = conn.prepareStatement(PRODUCT_DELETE);
			
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

	
}

