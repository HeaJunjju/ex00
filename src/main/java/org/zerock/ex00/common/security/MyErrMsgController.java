package org.zerock.ex00.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j;

//접근금지(403) 오류 발생 시, 스프링 시큐리티의 요청을 처리할 컨트롤러
@Controller
@Log4j
public class MyErrMsgController {
	//접근 금지(403) 오류에 대한 메시지 페이지 전달
	@GetMapping("/accessFobiddenError")
	public String callAccessForbiddenPage(Authentication authentication, Model model) {
		System.out.println("Forbidden 오류에 대한 메시지 전송 메소드"); 
		log.info("엑세스 거부 시 전달된 Authentication 객체: " + authentication);
		model.addAttribute("msg", "접근이 금지됨.");
		
		return "common/err_msg/myAccessForbiddenMsg";
	}
}
