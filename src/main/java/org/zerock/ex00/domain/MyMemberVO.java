package org.zerock.ex00.domain;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class MyMemberVO {
	//book_ex.tbl_mymember 테이블의 컬럼명과 동일하게 필드이름을 설정.
	private String userid;
	private String userpw;
	private String userName;
	private Timestamp mregDate;
	private Timestamp mmodDate;	//암호 등 계정 정보 수정 날짜시간
	private String mdropFlg;	//'0'(false) - 유지, '1'(true) - 탈퇴요청
	private boolean enabled;	// 0 (false) - 비활성화 상태, 1 (true) - 활성화 : 휴면계정, 정지상태로 활용//컬럼 데이터유형 CHAR(1)
	
	private List<MyAuthorityVO> authorityList;
}
