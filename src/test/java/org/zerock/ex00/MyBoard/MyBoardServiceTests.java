package org.zerock.ex00.MyBoard;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.zerock.ex00.domain.MyBoardVO;
import org.zerock.ex00.service.MyBoardService;

//import com.spring5212.mypro00.common.paging.MyBoardPagingDTO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@WebAppConfiguration    //테스트 시, DispatcherServlet의 servlet-context.xml 설정 구성파일(들)을 사용하기 위한 어노테이션
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/mybatis-context.xml",
					   "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Log4j
public class MyBoardServiceTests {

	@Setter(onMethod_ = { @Autowired })
	private MyBoardService myBoardService;
		
////생성자 주입 방식: 테스트 시 오류(JUnit 특징)
////	private MyBoardService myBoardService;
////	public BoardServiceTests(MyBoardService myBoardService) {
////		this.myBoardService = myBoardService;
////	}
//// 테스트 시 오류:	java.lang.Exception: Test class should have exactly 
////				one public zero-argument constructor
////		
////	setter를 이용한 자동 주입
////	private MyBoardService myBoardService;
////
	
////	@Setter(onMethod_ = { @Autowired }) <- 이 한줄은 아래와 같은 코드를 한줄로 줄인 것
////	@Autowired
////	public void setMyBoardService(MyBoardService myBoardService) {
////		this.myBoardService = myBoardService;
////	}
//	
//	//MyBoardService 빈 생성 유무 확인 테스트
	@Test
	public void testMyBoardServiceExist() {

		log.info("MyBoardService 빈 생성 확인: "+myBoardService);
		assertNotNull(myBoardService);
	}

	//게시물 목록 조회 서비스 테스트
//	@Test
//	public void testGetBoardList() {
//		//페이징 고려 안함
//		myBoardService.getBoardList().forEach(myBoard -> log.info(myBoard));
//		
//		//페이징 고려
//		//myBoardService.getBoardList(new MyBoardPagingDTO(2, 10)).forEach(myBoard -> log.info(myBoard));
//	}
//
//	
//	//게시물 등록: selectKey 이용 테스트
//	@Test
//	public void testRegisterBoard() {
//
//		MyBoardVO myBoard = new MyBoardVO();
//		myBoard.setBtitle("서비스 새글 입력  테스트 제목");
//		myBoard.setBcontent("서비스 새글 입력 테스트  내용");
//		myBoard.setBwriter("test");
//
//		myBoardService.registerBoard(myBoard);
//		log.info("등록된 게시물의 Bno: " + myBoard.getBno());
//	}
////
//	//게시물 수정 테스트
//	@Test
//	public void testModifyBoard() {
//
//		MyBoardVO myBoard = myBoardService.getBoard(1L);
//
//		if (myBoard == null) {
//			return;
//		}
//
//		myBoard.setBtitle("제목 수정:서비스 테스트");
//		log.info("수정된 게시물 조회 결과(boolean): " + myBoardService.modifyBoard(myBoard));
//	}
//	
//	//게시물 조회  테스트: by bno + 조회수 증가 고려
//	@Test
//	public void testGetBoard() {
//		log.info(myBoardService.getBoard(1L));
//	}
////
////	
//	//게시물 삭제  테스트- By 사용자: bdelFlag 컬럼만 1로 수정
//	@Test
//	public void testSetBoardDeleted() {
//		// 게시물 번호의 존재 여부를 확인하고 테스트할 것
//		log.info("수행결과: " + myBoardService.setBoardDeleted(7L));
//		log.info("수행결과: " + myBoardService.setBoardDeleted(8L));
//		log.info(myBoardService.getBoard(7L));
//		log.info(myBoardService.getBoard(8L));
//	}
//	
//	//게시물 삭제  테스트- By 관리자: 실제 삭제 발생
//	@Test
//	public void testRemoveBoard() {
//		// 게시물 번호의 존재 여부를 확인하고 테스트할 것
//		log.info("서비스: 특정 게시물 삭제 테스트: " + myBoardService.removeBoard(6L));
//	}
//
//	//게시물 삭제 테스트(관리자) – 사용자 삭제 요청된 게시물 전체 삭제: bdelFlag = 1
	@Test
	public void testRemoveAllDeletedBoard() {
		log.info("서비스: 삭제 설정된 모든 게시물 삭제로 삭제된 행 수: " + myBoardService.removeAllDeletedBoard());
	}


}
