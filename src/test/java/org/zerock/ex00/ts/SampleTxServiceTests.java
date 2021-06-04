package org.zerock.ex00.ts;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.zerock.ex00.service.SampleTxService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@WebAppConfiguration    //테스트 시, DispatcherServlet의 servlet-context.xml 설정 구성파일(들)을 사용하기 위한 어노테이션
@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/mybatis-context.xml",
					   "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class SampleTxServiceTests {
	@Setter(onMethod_ = { @Autowired })
	private SampleTxService service;
	
	@Test
	public void testLong() {
		String str = "Starry\r\n"+
						//"Starry night\r\n"+
						//"Paint Your palette blue and grey\r\n"+
						"Look out on a summer's day";
		
		log.info(str.getBytes().length);
		
		service.addData(str);
	}
}
