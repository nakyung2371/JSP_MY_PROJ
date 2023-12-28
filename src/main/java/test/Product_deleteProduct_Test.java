package test;

import product.ProductDAO;
import product.ProductDTO;

public class Product_deleteProduct_Test {

	public static void main(String[] args) {
		//1. ProductDTO에 id의 값을 할당
		ProductDTO dto = new ProductDTO();
		dto.setId(5);
		
		//2. ProductDAO의 deleteProduct(dto)
		ProductDAO dao = new ProductDAO();
		dao.deleteProduct(dto);
					
	}

}
