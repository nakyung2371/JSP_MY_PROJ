package product;

import java.sql.Date;

import lombok.Data;

@Data
public class ProductDTO {
	
	private int id;
	private String name;
	private double price;
	private String content;
	private Date regdate;

}

/*
create table product (
    id number(5)  not null primary key,  
    name varchar2(255) not null,              
    price number(8,2) not null,                    
    content varchar2(2000),                        
    regdate date default sysdate              
);
*/