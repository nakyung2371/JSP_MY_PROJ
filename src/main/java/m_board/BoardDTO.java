package m_board;

import java.sql.Date;

import lombok.Data;

@Data
public class BoardDTO {
	
	private int id;
	private String m_title;
	private String m_write;
	private String m_cont;
	private Date regdate;
	private int cnt;
	

}

/*
create table m_board (
id number(5) not null primary key,
m_title varchar2 (255) not null ,
m_write varchar2(255) not null,
m_cont varchar2 (255) not null,
regdate date default sysdate,
cnt number(5) default 0
);
*/