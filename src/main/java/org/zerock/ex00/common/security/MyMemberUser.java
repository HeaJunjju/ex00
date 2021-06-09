package org.zerock.ex00.common.security;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.zerock.ex00.domain.MyMemberVO;

import lombok.Getter;

@Getter
public class MyMemberUser extends User{
	private static final long serialVersionUID=1L;
	
	private MyMemberVO myMember;
	
	public MyMemberUser(MyMemberVO myMember) {
		super(myMember.getUserid(),
				myMember.getUserpw(),
				myMember.getAuthorityList()
						.stream()
						.map(auth -> new SimpleGrantedAuthority(auth.getAuthority()))
						.collect(Collectors.toList())
		);
		
		System.out.println("MyUser 생성자에 전달된 MyMemberVO 정보:" + myMember.toString()); 
		System.out.println("MyUser 객체 생성을 통해 MyUser 의 부모객체(User 객체) 생성됨"); 
		System.out.println("====================================================="); 
		
		this.myMember = myMember;

	}
}
