package org.zerock.ex00.mapper;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.ex00.common.paging.MyBoardPagingDTO;
import org.zerock.ex00.domain.MyBoardVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/mybatis-context.xml")
// Java Config
// @ContextConfiguration(classes = {com.spring5212.config.MybatisConfig.class} )
@Log4j
public class MyBoardMapperTests {

	@Setter(onMethod_ = @Autowired)
	private MyBoardMapper myBoardMapper;
	
//	@Autowired
//	private MyBoardMapper myBoardMapper;
//	
//	
//public void setMyBoardMapper(MyBoardMapper myBoardMapper) {
//		this.myBoardMapper = myBoardMapper;
//	}


	//게시물 목록 조회 테스트1 <--테스트 후, 메서드 주석처리
//	@Test
//	public void testSelectBoardList() {
//
//		myBoardMapper.selectMyBoardList().forEach(myBoard -> log.info(myBoard));
//		myBoardMapper.selectMyBoardList().forEach(myBoard -> System.out.println(myBoard));
//
//	}
	
//게시물 목록 조회 테스트2 - 페이징 고려 
//	@Test
//	public void testSelectBoardListWithPaging() {
//		
//		MyBoardPagingDTO myBoardPagingDTO = new MyBoardPagingDTO(); //기본 생성자 이용(1, 10)
//
//		//myBoardPagingDTO.setPageNum(1);
//		//myBoardPagingDTO.setRowAmountPerPage(10);
//		myBoardMapper.selectMyBoardList(myBoardPagingDTO).forEach(myBoard -> System.out.println(myBoard));
//		
//		myBoardPagingDTO = new MyBoardPagingDTO(2, 10); //2개의 매개변수 생성자
//		myBoardMapper.selectMyBoardList(myBoardPagingDTO).forEach(myBoard -> System.out.println(myBoard));
//
//	}
	
	//게시물 목록 검색 조회 테스트2 - 페이징 고려 
	@Test
	public void testSearchBoardWithPaging() {
	
		MyBoardPagingDTO myBoardPagingDTO = new MyBoardPagingDTO(); //기본 생성자 이용(1, 10)
		//myBoardPagingDTO.setKeyword("5");
		myBoardPagingDTO.setKeyword("용5");
		//myBoardPagingDTO.setKeyword("5");
	
		//myBoardPagingDTO.setScope("T");
		//myBoardPagingDTO.setScope("C");
		//myBoardPagingDTO.setScope("W");
		//myBoardPagingDTO.setScope("TC");
		myBoardPagingDTO.setScope("TCW");
		//myBoardPagingDTO.setScope("TW");
		//myBoardPagingDTO.setScope("CW");
	
		log.info("행 총 개수: " + myBoardMapper.selectRowAmountTotal(myBoardPagingDTO));
	
		List<MyBoardVO> list = myBoardMapper.selectMyBoardList(myBoardPagingDTO);
		list.forEach(board -> log.info(board));
		
	}

//게시물 등록 테스트 - selectKey 사용않음
//	@Test
//	public void testInsertMyBoard() {
//
//		MyBoardVO myBoard = new MyBoardVO();
//		myBoard.setBtitle("메퍼 테스트-입력제목");
//		myBoard.setBcontent("매퍼 테스트-입력내용");
//		myBoard.setBwriter("test");
//
//
//		myBoardMapper.insertMyBoard(myBoard);
//
//		log.info(myBoard);
//	}

//게시물 등록 테스트 - selectKey 사용
//	@Test
//	public void testInsertMyBoardSelectKey() {
//
//		MyBoardVO myBoard = new MyBoardVO();
//		myBoard.setBtitle("메퍼 테스트-select key");
//		myBoard.setBcontent("매퍼 테스트-select key");
//		myBoard.setBwriter("test01");
//
//
//		myBoardMapper.insertMyBoardSelectKey(myBoard);
//		
//		log.info(myBoard);
//	}
	
//게시물 조회 테스트(by bno)
//	@Test
//	public void testSelectMyBoard() {
//		// 존재하는 게시물 번호로 테스트
//		log.info(myBoardMapper.selectMyBoard(1L));
//	}

//게시물 삭제 테스트 - 사용자 요청: 게시물의 bdelFlag 컬럼값이 0 -> 1 로 수정만 됨
//	@Test
//	public void testUpdateBdelFlag() {
//		myBoardMapper.updateBdelFlag(10L); 
//		log.info(myBoardMapper.selectMyBoard(10L));
//	}


////게시물 삭제 테스트- 관리자 요청: 실제 특정 게시물이 삭제됨
//	@Test
//	public void testDeleteMyBoard() {
//		log.info("DELETE COUNT: " + myBoardMapper.deleteMyBoard(9L));
//		log.info(myBoardMapper.selectMyBoard(9L));
//	}
	

//게시물 삭제 테스트 - 삭제 요청된 게시물들 전체 삭제 (관리자)
//	@Test
//	public void testDeleteAllBoard() {
//		log.info("DELETE COUNT: " + myBoardMapper.deleteAllBoardSetDeleted());
//		log.info(myBoardMapper.selectMyBoard(10L));
//	}
	
//게시물 수정 테스트
//	@Test
//	public void testUpdateMyBoard() {
//	
//		MyBoardVO myBoard = new MyBoardVO();
//		myBoard.setBno(1L);  //실행 전 존재하는 번호인지 확인할 것
//		myBoard.setBtitle("수정된 제목");
//		myBoard.setBcontent("수정된 내용");
//	
//		log.info("UPDATE COUNT: " + myBoardMapper.updateMyBoard(myBoard));
//		myBoard = myBoardMapper.selectMyBoard(1L);
//		System.out.println("수정된 행 값: " + myBoard.toString());
//	
//	}
//
//게시물 조회수 증가 테스트
//	@Test
//	public void testUpdateBviewsCnt() {
//
//		myBoardMapper.updateBviewsCnt(1L);
//		System.out.println("수정된 행 값: " + myBoardMapper.selectMyBoard(1L).toString());
//	}
//
//	

}
