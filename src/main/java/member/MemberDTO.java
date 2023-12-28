package member;

import java.sql.Date;

import lombok.Data;

@Data
public class MemberDTO {
	
	private String id;
	private String password;
	private String phone;
	private String email;
	private Date regdate;
	private String addr;
	private String role;
	

}

/*
create table Member(
    id varchar2(255) not null primary key,
    password varchar2(255) not null,
    phone varchar2 (255) not null,
    email varchar2(255) null,
    regdate date default sysdate,
    addr varchar2(255),
    role varchar2(255) default 'user'
);
*/