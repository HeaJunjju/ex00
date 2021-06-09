package org.zerock.ex00.domain;

import lombok.Data;

@Data
public class MyAuthorityVO {
	//book_ex.tbl_mymember_authority 테이블의 컬럼명과 동일하게 필드이름을 설정.
	private String userid;
	private String authority;
}
