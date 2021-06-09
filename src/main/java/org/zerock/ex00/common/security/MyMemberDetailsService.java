package org.zerock.ex00.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.zerock.ex00.domain.MyMemberVO;
import org.zerock.ex00.mapper.MyMemberMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class MyMemberDetailsService implements UserDetailsService{
	//setter 방식 주입이 구성되어야 security-context.xml에 정상적인 설정이 가능
	@Setter(onMethod_ = {@Autowired})
	private MyMemberMapper myMemberMapper;

	//생성자 주입방식은 security-context.xml에 설정시에 오류발생
	//private MyMemberMapper myMemberMapper;
	//public MyMemberDetailsService(MyMemberMapper myMemberMapper) {
	// this.myMemberMapper = myMemberMapper;
	//}
	 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//스프링 시큐리티가 사용하는 username(사실userid)을 매개변수로 사용합니다.
		log.info("Load User By UserName: " + username); 
		 
		//스프링 시큐리티가 사용하는 username을 매개변수로 사용합니다.
		MyMemberVO myMember = myMemberMapper.selectMyMember(username); 
		 
		log.warn("MyMemberMapper에 의해서 반환된 MyMemberVO: " + myMember); 
		 
		//MyMemberUser 객체 생성 -> UserDetails 유형의 User 객체로 변환되어 반환됨
		return myMember == null ? null : new MyMemberUser(myMember); 
	}
	

}
