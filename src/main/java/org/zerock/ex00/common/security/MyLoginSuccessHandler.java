package org.zerock.ex00.common.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.log4j.Log4j;

//로그인이 성공된 후에 처리할 내용이 구현된 클래스
//AuthenticationSuccessHandler 인터페이스의 구현객체로 생성.
@Log4j
public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		log.info("로그인 성공 후: MyLoginSuccessHandler.onAuthenticationSuccess() 시작......");  
		log.info("전달된 Authentication 객체 정보: " + authentication); 
		
		List<String> roleNames = new ArrayList<>();//롤 목록을 저장
		//권한이름 추출
		authentication.getAuthorities()//인증객체에 저장되는 권한들(authorities)을 가져옴
					.forEach(
						authority -> {
							roleNames.add(authority.getAuthority());
						}				
					);
		log.info("ROLE NAMES: " + roleNames); //권한(롤)이름 리스트를 콘솔 표시
		
		if(roleNames.contains("ROLE_ADMIN")) {//권한(롤)목록에 ROLE_ADMIN이 포함되어 있으면
			//응답객체를 통해, 게시물 목록 페이지로 리다이렉트 시킴
			response.sendRedirect("/ex00/myboard/detail?bno=1"); 
		}else {
			//권한 목록에 ROLE_ADMIN 이 없는 경우, home.jsp(/) 로 리다이렉트 시킴.
			response.sendRedirect("/ex00/");
		}

		
	}

}
