package org.zerock.ex00.MyReply;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

//코드에서, 오류를 마우스 우클릭 --> 메시지에서 static 임포트를 수행
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;

//import com.google.gson.Gson;


//테스트 메소드를 실행하는 어노테이션
@RunWith(SpringJUnit4ClassRunner.class)
//DispatcherServlet의 구성설정파일을 사용하여 컨트롤러를 테스트 환경에 필요한 설정을 가져옴
@WebAppConfiguration 
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/mybatis-context.xml",
						"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
@Log4j
public class MyReplyControllerTests {

	//private static final Logger logger = LoggerFactory.getLogger(MyReplyControllerTests.class);
	
//테스트 환경 구성
	//컨트롤러 테스트를 위해서는 WebApplicationContext를 객체(웹 컨텍스트 객체)로 주입받아야 합니다.
	@Setter(onMethod_ = { @Autowired })
	private WebApplicationContext ctx;

	//테스트 객체를 담을 필드
	private MockMvc mockMvc;

	//테스트 전에 테스트 객체를 생성하여 테스트 환경 설정
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
//테스트 환경 구성-끝
	
	
	//게시물에 대한 댓글 목록 조회 테스트: GET /replies/pages/{bno}/{page}
//	@Test
//	public void testShowReplyList() throws Exception {
//	
//		int resultStatus = 
//			mockMvc.perform(get("/replies/pages/229376/1")
//							//서버가 보낸 것이 JSON 일 때만 처리(컨트롤러의 produces 에 대응)
//							.accept("application/json; charset=UTF-8")
//							//서버로 보내는 데이터에 대한 인코딩을 UTF-8로 지정
//							.characterEncoding("utf-8")
//							)
//			 	.andDo(print())// 콘솔 출력
//			 	.andReturn()
//				.getResponse()
//				.getStatus();
//	
//		log.info("웹브라우저에 전달되는 ResponseEntiry 객체의 처리 상태 코드(resultStatus): " + resultStatus );
//	
//	}
	
//	@Test
//	public void testShowReply() throws Exception{
//		
//		int resultStatus =
//			mockMvc.perform(get("/replies/229376/1")
//							//서버가 보낸 것이 JSON 일 때만 처리(컨트롤러의 produces 에 대응)
//							.accept("application/json; charset=UTF-8")
//							//서버로 보내는 데이터에 대한 인코딩을 UTF-8로 지정
//							.characterEncoding("utf-8")
//							)
//			 	.andDo(print())// 콘솔 출력
//			 	.andReturn()
//				.getResponse()
//				.getStatus();
//		
//		log.info("웹브라우저에 전달되는 ResponseEntiry 객체의 처리 상태 코드(resultStatus): " + resultStatus );
//	}
//
//	
//	//게시물에 대한 댓글 등록(rno 반환):POST /replies/{bno}/new
//	//JSON 형식 입력 테스트
//	@Test
//	public void testRegisterReplyForBoard() throws Exception {
//
//		//replyVO 객체 생성
//		MyReplyVO myReply = new MyReplyVO();
//			
//		//replyVO에 입력값 지정
//		myReply.setBno(229376L);
//		myReply.setRcontent("컨트롤러-게시물에 대한 댓글 등록 테스트: JSON입력테스트");
//		myReply.setRwriter("testJson");
//
//		//myReply를  JSON 스트링으로 변환
//		String myReplyJsonStr = new Gson().toJson(myReply);
//
//		//JsonString 객체 콘솔출력
//		log.info("컨트롤러-게시물에 대한 댓글 등록 테스트: JSON-String 객체(myReplyJsonStr):"+myReplyJsonStr);
//
//		//ResponseEntiry가 반환하는 상태값을 표시
//		 int resultStatus 
//		 		= mockMvc.perform(post("/replies/229376/new")
//		 						  //서버가 보낸 것이 String 일 때만 처리(컨트롤러의 @*Mapping의 produces 속성에 대응)
//		 						  .accept("text/plain; charset=UTF-8")
//		 						  //서버에게 보낼 것이 JSON 임을 header에 명시(컨트롤러의 @*Mapping의 consumes 속성에 대응)
//		 						  .contentType("application/json; charset=UTF-8")
//		 						  //보내는 데이터에 대한 인코딩 설정입니다.
//		 						  .characterEncoding("utf-8")
//		 						  //테스트에 처리되는 데이터
//		 						  .content(myReplyJsonStr)
//		 						 ) 
//						 .andDo(print())//print()를 직접 static으로 import, 콘솔에 출력
//						 .andReturn()
//						 .getResponse()
//						 .getStatus();
//		 log.info("웹브라우저에 전달되는 ResponseEntiry 객체의 처리 상태 코드(resultStatus): " + resultStatus);
//	}
//	
//	//게시물에 대한 댓글의 댓글 등록: (rno 반환): POST /replies/{bno}/{prno}/new
//	@Test
//	public void testRegisterReplyForReply() throws Exception {
//	
//		//replyVO 객체 생성
//		MyReplyVO myReply = new MyReplyVO();
//			
//		//replyVO에 입력값 지정
//		myReply.setBno(229376L);
//		myReply.setRcontent("컨트롤러-댓글에 대한 댓글 등록 테스트");
//		myReply.setRwriter("test00");
//		myReply.setPrno(1L);  //부모댓글의 rno를 받아와야 함
//	
//		//myReply를  JSON 스트링으로 변환
//		String myReplyJsonStr = new Gson().toJson(myReply);
//	
//		//JsonString 객체 콘솔출력
//		log.info("컨트롤러-게시물에 대한 댓글 등록 테스트: JSON-String 객체(myReplyJsonStr):"+myReplyJsonStr);
//	
//		//ResponseEntiry가 반환하는 상태값을 표시
//		 int resultStatus 
//		 		= mockMvc.perform(post("/replies/229377/51/new")
//		 						  //서버가 보낸 것이 String 일 때만 처리(컨트롤러의 @*Mapping의 produces 속성에 대응)
//		 						  .accept("text/plain; charset=UTF-8")
//		 						  //서버에게 보낼 것이 JSON 임을 header에 명시(컨트롤러의 @*Mapping의 consumes 속성에 대응)
//		 						  .contentType("application/json; charset=UTF-8")
//		 						  //보내는 데이터에 대한 인코딩 설정입니다.
//		 						  .characterEncoding("utf-8")
//		 						  //테스트에 처리되는 데이터
//		 						  .content(myReplyJsonStr)
//		 						 ) 
//						 .andDo(print())//print()를 직접 static으로 import, 콘솔에 출력
//						 .andReturn()
//						 .getResponse()
//						 .getStatus();
//		 log.info("웹브라우저에 전달되는 ResponseEntiry 객체의 처리 상태 코드(resultStatus): " + resultStatus);
//	}
//	
//	
//	//게시물에 대한 특정 댓글 조회 테스트: GET /replies/{bno}/{rno}
//	@Test
//	public void testGetReply() throws Exception{
//		
//		int resultStatus =
//			mockMvc.perform(get("/replies/229376/1")
//							//서버가 보낸 것이 JSON 일 때만 처리(컨트롤러의 produces 에 대응)
//							.accept("application/json; charset=UTF-8")
//							//서버로 보내는 데이터에 대한 인코딩을 UTF-8로 지정
//							.characterEncoding("utf-8")
//							)
//			 	.andDo(print())// 콘솔 출력
//			 	.andReturn()
//				.getResponse()
//				.getStatus();
//		
//		log.info("웹브라우저에 전달되는 ResponseEntiry 객체의 처리 상태 코드(resultStatus): " + resultStatus );
//	}
//	
//	
//	//게시물에 대한 특정 댓글 수정 테스트 : put /replies/{bno}/{rno}, patch /replies/{bno}/{rno}
//	@Test
//	public void testModifyReply() throws Exception{
//	
//		MyReplyVO myReply = new MyReplyVO() ;
//	
//		myReply.setRno(1L);
//		
//		myReply.setRcontent("댓글-컨트롤러-수정테스트 - 댓글 수정(put)");
//		
//		//replyVO를  JSON 스트링으로 변환
//		String myReplyJsonStr = new Gson().toJson(myReply);
//		
//		System.out.println("===========JSON-String 객체로 변환:" + myReplyJsonStr);		
//	
//		int resultStatus =
//				mockMvc.perform(put("/replies/229376/1")
//				//mockMvc.perform(patch("/replies/229376/1")
//								//서버가 보낸 것(컨트롤러의 @*Mapping의 produces 속성에 대응)
//			 		 			.accept("text/plain; charset=UTF-8")
//			 		 			//서버에게 보낼 것(컨트롤러의 @*Mapping의 consumes 속성에 대응)
//			 		 			.contentType("application/json; charset=UTF-8")
//			 		 			.content(myReplyJsonStr)
//							)
//			 			.andDo(print())
//			 			.andReturn()
//			 			.getResponse()
//			 			.getStatus();
//		
//		log.info("웹브라우저에 전달되는 ResponseEntiry 객체의 처리 상태 코드(resultStatus): " + resultStatus);
//	}
//	
//	//게시물에 대한 특정 댓글 삭제: DELETE:  /replies/{bno}/{rno}
//	@Test
//	public void testRemoveReply1() throws Exception{
//		
//		int resultStatus =
//			mockMvc.perform(delete("/replies/229376/1").accept("text/plain; charset=UTF-8"))
//					.andDo(print())
//					.andReturn()
//					.getResponse()
//					.getStatus();
//		
//		log.info("웹브라우저에 전달되는 ResponseEntiry 객체의 처리 상태 코드(resultStatus): " + resultStatus );
//	}


}
